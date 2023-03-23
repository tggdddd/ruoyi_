package cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* 业绩定义 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class PerformanceReportRequestBaseVO {

    @Schema(description = "关联的合同表ID", required = true, example = "32477")
    @NotNull(message = "关联的合同表ID不能为空")
    private Long contractId;

    @Schema(description = "业绩表单提交开始开始时间", required = true)
    @NotNull(message = "业绩表单提交开始开始时间不能为空")
    private String startTime;

    @Schema(description = "业绩表单提交终止时间")
    private String endTime;

    @Schema(description = "业绩表单提交通知时间")
    private String notifyTime;

    @Schema(description = "业绩提交流程id", required = true, example = "20491")
    @NotNull(message = "业绩提交流程id不能为空")
    private String processDefitionId;

    @Schema(description = "业绩表单未交通知时间")
    private String urgeTime;

    @Schema(description = "用户id", required = true, example = "9748")
    private Long userId;

    @Schema(description = "岗位id")
    private Long postId;

    @Schema(description = "部门Id")
    private Long deptId;
}
