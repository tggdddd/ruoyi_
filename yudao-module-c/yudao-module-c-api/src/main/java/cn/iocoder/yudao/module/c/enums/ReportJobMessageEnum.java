package cn.iocoder.yudao.module.c.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName ReportJobMessageEnum
 * @Description
 * @Author 15014
 * @Time 2023/3/3 15:09
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum ReportJobMessageEnum {
    NOTIFY("report_notify"), // 流程任务被审批通过时，发送给申请人
    URGE("report_urge"), // 流程任务被审批不通过时，发送给申请人
    EXPIRE("report_expire"); // 任务被分配时，发送给审批人

    /**
     * 短信模板的标识
     *
     * 关联 SmsTemplateDO 的 code 属性
     */
    private final String smsTemplateCode;
}
