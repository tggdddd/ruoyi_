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
import org.flowable.engine.delegate.ExecutionListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SendNotify
 * @Description
 * @Author 15014
 * @Time 2023/2/27 9:58
 * @Version 1.0
 */
public class SendNotify  implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Long userId = (Long) execution.getVariable("userId");
        Long createUserId = (Long) execution.getVariable("createUserId");
        // 通知次数
        Integer notifyNo = (Integer) execution.getVariable("notify_no");
        if(notifyNo == null){
            notifyNo = 1;
        }
        //  您有一个新的合同等待您签约，请在{cancel_time}前查看，详情请点击{detail_url}查看具体信息--第{notify_no}次通知。
        Map<String, Object> templateParams = new HashMap<>();
        templateParams.put("notify_no", notifyNo);
        templateParams.put("detail_url", getDetail(execution.getRootProcessInstanceId()));
        templateParams.put("cancel_time", execution.getVariable("cancelTime"));
        // 发送通知给用户
        sendMessage(new SmsSendSingleToUserReqDTO()
                .setUserId(userId)
                .setTemplateCode(BpmMessageEnum.CONTRACT_SIGN_NOTIFY.getSmsTemplateCode())  //执行模板
                .setTemplateParams(templateParams));//参数
        //设置通知次数
        execution.setVariable("notify_no",notifyNo+1);
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
