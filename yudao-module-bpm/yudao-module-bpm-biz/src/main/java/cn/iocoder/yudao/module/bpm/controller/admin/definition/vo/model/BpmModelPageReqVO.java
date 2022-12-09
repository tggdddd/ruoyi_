package cn.iocoder.yudao.module.bpm.controller.admin.definition.vo.model;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Schema(title = "管理后台 - 流程模型分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmModelPageReqVO extends PageParam {

    @Schema(title = "标识", example = "process1641042089407", description = "精准匹配")
    private String key;

    @Schema(title = "名字", example = "芋道", description = "模糊匹配")
    private String name;

    @Schema(title = "流程分类", description = "参见 bpm_model_category 数据字典", example = "1")
    private String category;

}
