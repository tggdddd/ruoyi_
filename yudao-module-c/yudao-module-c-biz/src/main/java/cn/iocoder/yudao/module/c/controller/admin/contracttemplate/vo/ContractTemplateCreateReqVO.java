package cn.iocoder.yudao.module.c.controller.admin.contracttemplate.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 合同表单模板创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractTemplateCreateReqVO extends ContractTemplateBaseVO {

}
