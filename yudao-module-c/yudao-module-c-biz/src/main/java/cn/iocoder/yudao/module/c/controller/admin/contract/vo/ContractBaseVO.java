package cn.iocoder.yudao.module.c.controller.admin.contract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* 合同表单 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class ContractBaseVO {

    @Schema(description = "system_user表用户ID", required = true, example = "22709")
    @NotNull(message = "system_user表用户ID不能为空")
    private Long userId;

    @Schema(description = "用户的真实姓名", required = true, example = "芋艿")
    @NotNull(message = "用户的真实姓名不能为空")
    private String name;
    @Schema(description = "岗位", required = true, example = "开发人员")
    private String post;
    @Schema(description = "用户的身份证号", required = true)
    @NotNull(message = "用户的身份证号不能为空")
    private String identityCard;

    @Schema(description = "薪资", required = true)
    @NotNull(message = "薪资不能为空")
    private BigDecimal salary;

    @Schema(description = "附件")
    private String attach;

    @Schema(description = "业绩要求")
    private String performanceRequirements;

    @Schema(description = "违约条款")
    private String defaultClause;

    @Schema(description = "合同状态 0未签订 1签订 2到期 3终止", required = true, example = "0")
    @NotNull(message = "合同状态 0未签订 1签订 2到期 3终止不能为空")
    private Integer status;

    @Schema(description = "甲方")
    private String firstParty;

    @Schema(description = "签约时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime signedTime;

    @Schema(description = "合同开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

    @Schema(description = "合同结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

}
