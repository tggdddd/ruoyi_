package cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 业绩定义 Excel 导出 Request VO，参数和 PerformanceReportRequestPageReqVO 是一致的")
@Data
public class PerformanceReportRequestExportReqVO {

    @Schema(description = "关联的合同表ID", example = "32477")
    private Long contractId;

    @Schema(description = "业绩表单提交开始开始时间")
    private String startTime;

    @Schema(description = "业绩表单提交终止时间")
    private String endTime;

    @Schema(description = "业绩表单提交通知时间")
    private String notifyTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "业绩提交流程id", example = "20491")
    private String processDefitionId;

    @Schema(description = "业绩表单未交通知时间")
    private String urgeTime;

    @Schema(description = "用户id", example = "9748")
    private Long userId;

    @Schema(description = "dept")
    private Long postId;

}
