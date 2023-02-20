package cn.iocoder.yudao.module.c.controller.admin.contracttemplate.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 合同表单模板分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractTemplatePageReqVO extends PageParam {

    @Schema(description = "用户的真实姓名", example = "王五")
    private String name;

    @Schema(description = "用户的身份证号")
    private String identityCard;

    @Schema(description = "薪资")
    private BigDecimal salary;
    @Schema(description = "岗位", required = true, example = "开发人员")
    private String post;
    @Schema(description = "业绩要求")
    private String performanceRequirements;

    @Schema(description = "违约条款")
    private String defaultClause;

    @Schema(description = "甲方")
    private String firstParty;

    @Schema(description = "签约时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] signedTime;

    @Schema(description = "合同开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

    @Schema(description = "合同结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
