package cn.iocoder.yudao.module.c.controller.admin.contract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 合同表单 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractRespVO extends ContractBaseVO {

    @Schema(description = "主键(自增策略)", required = true, example = "28516")
    private Long id;

    @Schema(description = "附件")
    private String attach;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

    @Schema(description = "审核结果",example = "1")
    private Integer result;

    @Schema(description = "合同状态 0未签订 1签订 2到期 3终止", required = false, example = "0")
    private Integer status;

    @Schema(description = "部门Id")
    private Long deptId;
}
