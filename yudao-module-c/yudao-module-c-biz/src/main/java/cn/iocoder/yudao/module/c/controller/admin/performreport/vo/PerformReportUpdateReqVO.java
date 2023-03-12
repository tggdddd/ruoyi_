package cn.iocoder.yudao.module.c.controller.admin.performreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 业绩信息更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PerformReportUpdateReqVO extends PerformReportBaseVO {

    @Schema(description = "主键(自增策略)", required = true, example = "32619")
    @NotNull(message = "主键(自增策略)不能为空")
    private Long id;

}
