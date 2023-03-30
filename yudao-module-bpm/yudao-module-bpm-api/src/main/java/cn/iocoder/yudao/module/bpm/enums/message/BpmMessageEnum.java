package cn.iocoder.yudao.module.bpm.enums.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Bpm 消息的枚举
 *
 * @author 芋道源码
 */
@AllArgsConstructor
@Getter
public enum BpmMessageEnum {

    PROCESS_INSTANCE_APPROVE("bpm_process_instance_approve"), // 流程任务被审批通过时，发送给申请人
    PROCESS_INSTANCE_REJECT("bpm_process_instance_reject"), // 流程任务被审批不通过时，发送给申请人
    TASK_ASSIGNED("bpm_task_assigned"), // 任务被分配时，发送给审批人
    CONTRACT_SIGNED("bpm_contract_signed"),
    CONTRACT_CANCEL("bpm_contract_cancel"),
    CONTRACT_SIGNED_ADMIN("bpm_contract_signed_admin"),
    CONTRACT_CANCEL_ADMIN("bpm_contract_cancel_admin"),
    CONTRACT_SIGN_NOTIFY("bpm_contract_sign_notify");
    /**
     * 短信模板的标识
     *
     * 关联 SmsTemplateDO 的 code 属性
     */
    private final String smsTemplateCode;

}
