package cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 业绩定义 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PerformanceReportRequestRespVO extends PerformanceReportRequestBaseVO {

    @Schema(description = "主键(自增策略)", required = true, example = "13759")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}
