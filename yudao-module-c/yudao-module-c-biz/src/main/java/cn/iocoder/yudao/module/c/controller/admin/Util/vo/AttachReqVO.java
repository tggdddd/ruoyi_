package cn.iocoder.yudao.module.c.controller.admin.Util.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * @ClassName AttachReqVO
 * @Description
 * @Author 15014
 * @Time 2023/2/18 23:05
 * @Version 1.0
 */

/**
 * 合同表单附件请求 VO
 */
@Data
public class AttachReqVO {

    @Schema(description = "system_user表用户ID", required = true, example = "22709")
    private Long userId;

    @Schema(description = "用户的真实姓名", required = true, example = "芋艿")
    private String name;

    @Schema(description = "用户的身份证号", required = true)
    private String identityCard;

    @Schema(description = "薪资", required = true)
    private BigDecimal salary;
    @Schema(description = "岗位", required = true, example = "开发人员")
    private String post;
    @Schema(description = "附件")
    @NotNull
    private String attach;

    @Schema(description = "业绩要求")
    private String performanceRequirements;

    @Schema(description = "违约条款")
    private String defaultClause;

    @Schema(description = "合同状态 0未签订 1签订 2到期 3终止", required = true, example = "0")
    private Byte status;

    @Schema(description = "甲方")
    private String firstParty;

    @Schema(description = "签约时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime signedTime;

    @Schema(description = "合同开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime startTime;

    @Schema(description = "合同结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime endTime;

}
