package cn.iocoder.yudao.module.bpm.framework.flowable.core.event;

import cn.iocoder.yudao.framework.web.config.WebProperties;
import cn.iocoder.yudao.module.bpm.enums.message.BpmMessageEnum;
import cn.iocoder.yudao.module.bpm.framework.ApplicationContextUtils;
import cn.iocoder.yudao.module.system.api.sms.SmsSendApi;
import cn.iocoder.yudao.module.system.api.sms.dto.send.SmsSendSingleToUserReqDTO;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.task.api.Task;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CancelContract
 * @Description
 * @Author 15014
 * @Time 2023/2/27 9:58
 * @Version 1.0
 */
public class CancelProcess  implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        if(execution.getVariable("isCompleted") != null){
            return;
        }
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        if (runtimeService.createActivityInstanceQuery().processInstanceId(execution.getRootProcessInstanceId()).activityName("用户签约").finished().singleResult() == null) {
            runtimeService.deleteProcessInstance(execution.getRootProcessInstanceId(), "用户未在规定时间内签约");
            Long userId = (Long) execution.getVariable("userId");
            Long createUserId = (Long) execution.getVariable("createUserId");
            // 您在{create_time}创建的合同审批流程，在{cancel_time}被取消了，详情请点击{detail_url}查看具体信息。
            // 您的待签合同在{cancel_time}被取消了，详情请点击{detail_url}查看具体信息。
            Map<String, Object> templateParams = new HashMap<>();
            templateParams.put("detail_url", getDetail(execution.getRootProcessInstanceId()));
            templateParams.put("create_time", runtimeService.createProcessInstanceQuery()
                    .processInstanceId(execution.getRootProcessInstanceId())
                    .singleResult().getStartTime().toString());
            templateParams.put("cancel_time", LocalDateTime.now().toString());

            // 发送通知给用户
            sendMessage(new SmsSendSingleToUserReqDTO()
                    .setUserId(userId)
                    .setTemplateCode(BpmMessageEnum.CONTRACT_CANCEL.getSmsTemplateCode())  //执行模板
                    .setTemplateParams(templateParams));//参数
            // 发送通知给创建者
            sendMessage(new SmsSendSingleToUserReqDTO()
                    .setUserId(createUserId)
                    .setTemplateCode(BpmMessageEnum.CONTRACT_CANCEL_ADMIN.getSmsTemplateCode())  //执行模板
                    .setTemplateParams(templateParams));//参数
        }else {
            Task task = processEngine.getTaskService().createTaskQuery()
                    .executionId(execution.getId())
                    .singleResult();
            processEngine.getTaskService().complete(task.getId());
            // 发送通知给用户
            sendMessage(new SmsSendSingleToUserReqDTO()
                    .setUserId(1L)
                    .setTemplateCode(BpmMessageEnum.CONTRACT_CANCEL.getSmsTemplateCode())  //执行模板
                    .setTemplateParams(new HashMap<>()));//参数
        }
    }
    private String getDetail(String processInstanceId) {
        return ApplicationContextUtils.getBean(WebProperties.class).getAdminUi().getUrl() +
                "/process-instance/detail?id=" +
                processInstanceId;

    }
    /**发送消息  */
    private void sendMessage(SmsSendSingleToUserReqDTO reqDTO) {
        ApplicationContextUtils.getBean(SmsSendApi.class).sendSingleSmsToAdmin(reqDTO);
    }

}
