package cn.iocoder.yudao.module.c.controller.admin.performreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
* 业绩信息 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class PerformReportBaseVO {

    @Schema(description = "system_user表用户ID", required = true, example = "13575")
    @NotNull(message = "system_user表用户ID不能为空")
    private Long userId;

    @Schema(description = "合同表单id", required = true, example = "14031")
    @NotNull(message = "合同表单id不能为空")
    private Long contractId;

    @Schema(description = "用户的岗位id", example = "17467")
    private Long postId;

    @Schema(description = "提交的流程id", example = "1907")
    private String bpmProcessInstanceExtId;

    @Schema(description = "流程定义id", example = "3869")
    private String bpmProcessDefinitionId;

    @Schema(description = "流程实例的编号", example = "27165")
    private String processInstanceId;

    @Schema(description = "过期时间")
    private LocalDateTime expireTime;
    @Schema(description = "部门Id")
    private Long deptId;
}
