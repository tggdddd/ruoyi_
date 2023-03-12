package cn.iocoder.yudao.module.c.Job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.web.config.WebProperties;
import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import cn.iocoder.yudao.module.c.Util.StringUtil;
import cn.iocoder.yudao.module.c.controller.admin.contract.vo.ContractExportReqVO;
import cn.iocoder.yudao.module.c.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.module.c.enums.ContractJobMessageEnum;
import cn.iocoder.yudao.module.c.enums.ContractStatusConstant;
import cn.iocoder.yudao.module.c.service.contract.ContractService;
import cn.iocoder.yudao.module.system.api.sms.SmsSendApi;
import cn.iocoder.yudao.module.system.api.sms.dto.send.SmsSendSingleToUserReqDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ContractIntervalDetectJob
 * @Description 合同定期检测
 * 提醒快到期，及时续约
 * 将到期的设置为已终止
 * @Author 肖润杰
 * @Time 2023/3/12 11:04
 * @Version 1.0
 */
@Component("checkContract")
public class ContractIntervalDetectJob implements JobHandler {
    /**
     * 最长调用时间，当提醒时间距离执行超过这个时间，则认为已经触发
     */
    private static final Duration LAST_DELAY_TRIGGER_DURATION = Duration.parse("PT6H");
    @Resource
    ContractService contractService;
    @Resource
    private SmsSendApi smsSendApi;

    @Resource
    private WebProperties webProperties;

    @Override
    public String execute(String param) throws Exception {
        List<Duration> list = new LinkedList<>();
        // 获得时间数组
        if (StringUtil.notEmpty(param)) {
            String[] split = param.split(",");
            for (String s : split) {
                if (StringUtil.notEmpty(s)) {
                    Duration parse = Duration.parse(s);
                    if (parse != null) {
                        list.add(parse);
                    }
                }
            }
            list.sort(Comparator.reverseOrder());
        }
        // 获得正在执行中的合同
        List<ContractDO> contractDOS = contractService.getontractList(new ContractExportReqVO()
                .setStatus(ContractStatusConstant.SIGNED)
                .setResult(BpmProcessInstanceResultEnum.APPROVE.getResult())
        );
        // 遍历合同
        for (ContractDO contractDO : contractDOS) {
            // 超过过期时间则设置为已过期
            if (contractDO.getEndTime().isBefore(LocalDateTime.now())) {
                toExpired(contractDO);
                continue;
            }
            // 获得距离到期时间
            LocalDateTime now = LocalDateTime.now();
            for (Duration time : list) {
                // 当到期时间与设置时间间隔不超过  最大延迟时 触发提醒
                if (now.plus(time).isAfter(contractDO.getEndTime()) && now.plus(time).isBefore(contractDO.getEndTime().plus(LAST_DELAY_TRIGGER_DURATION))) {
                    remindToUpdate(contractDO);
                    break;
                }
            }
        }
        return "执行合同的定时检测任务";
    }

    /**
     * 设置合同状态为到期
     */
    private void toExpired(ContractDO contractDO) {
        contractService.updateContractStatus(contractDO.getId(), ContractStatusConstant.EXPIRE);
        // 发送通知
        Map<String, Object> templateParams = new HashMap<>();
        templateParams.put("detailUrl", getUrl());
        templateParams.put("startTime", contractDO.getSignedTime().toString());
        templateParams.put("endTime", contractDO.getEndTime().toString());
        SmsSendSingleToUserReqDTO smsSendSingleToUserReqDTO = new SmsSendSingleToUserReqDTO()
                .setUserId(contractDO.getUserId())
                .setTemplateCode(ContractJobMessageEnum.EXPIRE.getSmsTemplateCode())
                .setTemplateParams(templateParams);
        sendMessage(smsSendSingleToUserReqDTO);
    }

    /**
     * 提醒续约
     */
    private void remindToUpdate(ContractDO contractDO) {
        // 发送通知
        Map<String, Object> templateParams = new HashMap<>();
        templateParams.put("detailUrl", getUrl());
        templateParams.put("startTime", contractDO.getSignedTime().toString());
        templateParams.put("endTime", contractDO.getEndTime().toString());
        SmsSendSingleToUserReqDTO smsSendSingleToUserReqDTO = new SmsSendSingleToUserReqDTO()
                .setUserId(contractDO.getUserId())
                .setTemplateCode(ContractJobMessageEnum.NOTIFY.getSmsTemplateCode())
                .setTemplateParams(templateParams);
        sendMessage(smsSendSingleToUserReqDTO);
    }

    private String getUrl() {
        return webProperties.getAdminUi().getUrl() +
                "/contract/contract";
    }

    /**
     * 发送消息
     */
    public void sendMessage(SmsSendSingleToUserReqDTO reqDTO) {
        smsSendApi.sendSingleSmsToAdmin(reqDTO);
    }

}
