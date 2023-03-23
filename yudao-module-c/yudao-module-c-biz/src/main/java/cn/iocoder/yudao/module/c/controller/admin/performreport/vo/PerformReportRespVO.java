package cn.iocoder.yudao.module.c.controller.admin.performreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 业绩信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PerformReportRespVO extends PerformReportBaseVO {

    @Schema(description = "主键(自增策略)", required = true, example = "32619")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

    @Schema(description = "流程定义名")
    private String processDefinedName;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "部门Id")
    private Long deptId;
}
