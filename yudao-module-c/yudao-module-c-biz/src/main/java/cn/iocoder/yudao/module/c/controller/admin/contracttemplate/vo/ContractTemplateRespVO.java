package cn.iocoder.yudao.module.c.controller.admin.contracttemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 合同表单模板 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractTemplateRespVO extends ContractTemplateBaseVO {

    @Schema(description = "主键(自增策略)", required = true, example = "28516")
    private Long id;

    @Schema(description = "附件")
    private String attach;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}
