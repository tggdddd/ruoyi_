package cn.iocoder.yudao.module.c.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName ContractJobMessageEnum
 * @Description
 * @Author 肖润杰
 * @Time 2023/3/12 15:07
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum ContractJobMessageEnum {
    NOTIFY("contract_notify"), // 发送合同快到期通知
    EXPIRE("contract_expire"); // 发送合同到期通知

    /**
     * 短信模板的标识
     *
     * 关联 SmsTemplateDO 的 code 属性
     */
    private final String smsTemplateCode;
}
