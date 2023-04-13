package cn.iocoder.yudao.module.system.controller.admin.home.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 首页 用户信息 Response VO")
@Data
public class UserInfoRespVO {
    /** 名字 */
    @Schema(description = "名字")
    private String userName;
    /** 距离用户注册的时间 */
    @Schema(description = "距离用户注册的时间")
    private String dateTime;
    @Schema(description = "排名")
    /** 排名 */

    private Integer rank;

}
