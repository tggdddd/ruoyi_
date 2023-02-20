package cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 业绩定义 Excel 导出 Request VO，参数和 PerformanceReportRequestPageReqVO 是一致的")
@Data
public class PerformanceReportRequestExportReqVO {

    @Schema(description = "关联的合同表ID", example = "32477")
    private Long contractId;

    @Schema(description = "业绩提交表单ids")
    private String formIds;

    @Schema(description = "业绩表单提交开始开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

    @Schema(description = "业绩表单提交终止时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endTime;

    @Schema(description = "业绩表单提交通知时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] notifyTime;

    @Schema(description = "业绩表单提交通知频率")
    private LocalDateTime notifyDuration;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
