package cn.iocoder.yudao.module.c.controller.admin.performreport.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 业绩信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PerformReportPageReqVO extends PageParam {

    @Schema(description = "system_user表用户ID", example = "13575")
    private Long userId;

    @Schema(description = "合同表单id", example = "14031")
    private Long contractId;

    @Schema(description = "用户的岗位id", example = "17467")
    private Long postId;

    @Schema(description = "提交的流程id", example = "1907")
    private String bpmProcessInstanceExtId;

    @Schema(description = "流程定义id", example = "3869")
    private String bpmProcessDefinitionId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "流程实例的编号", example = "27165")
    private String processInstanceId;
    @Schema(description = "过期时间")
    private LocalDateTime[] expireTime;
    @Schema(description = "状态")
    private Integer status;
}
