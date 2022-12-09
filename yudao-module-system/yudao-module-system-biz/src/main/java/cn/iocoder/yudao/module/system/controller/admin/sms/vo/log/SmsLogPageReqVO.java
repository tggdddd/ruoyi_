package cn.iocoder.yudao.module.system.controller.admin.sms.vo.log;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(title = "管理后台 - 短信日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SmsLogPageReqVO extends PageParam {

    @Schema(title = "短信渠道编号", example = "10")
    private Long channelId;

    @Schema(title = "模板编号", example = "20")
    private Long templateId;

    @Schema(title = "手机号", example = "15601691300")
    private String mobile;

    @Schema(title = "发送状态", example = "1",  description = "参见 SmsSendStatusEnum 枚举类")
    private Integer sendStatus;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(title = "发送时间")
    private LocalDateTime[] sendTime;

    @Schema(title = "接收状态", example = "0", description = "参见 SmsReceiveStatusEnum 枚举类")
    private Integer receiveStatus;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(title = "接收时间")
    private LocalDateTime[] receiveTime;

}
