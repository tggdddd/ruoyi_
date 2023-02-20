package cn.iocoder.yudao.module.c.controller.admin.Util.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;


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
@Schema(description = "合同附件工具 -  AttachReq VO")
@Data
@ToString(callSuper = true)
public class AttachReqVO {

    @Schema(description = "system_user表用户ID", example = "22709")
    private Long userId;

    @Schema(description = "用户的真实姓名", example = "芋艿")
    private String name;

    @Schema(description = "用户的身份证号")
    private String identityCard;

    @Schema(description = "薪资")
    private BigDecimal salary;
    @Schema(description = "岗位", example = "1")
    private Long postId;
    @Schema(description = "附件", example = "开发人员{post}")
    @NotNull
    private String attach;

    @Schema(description = "业绩要求")
    private String performanceRequirements;

    @Schema(description = "违约条款")
    private String defaultClause;

    @Schema(description = "合同状态 0未签订 1签订 2到期 3终止", example = "0")
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
