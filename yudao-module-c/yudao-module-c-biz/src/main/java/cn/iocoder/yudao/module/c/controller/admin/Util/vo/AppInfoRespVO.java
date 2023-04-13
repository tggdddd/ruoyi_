package cn.iocoder.yudao.module.c.controller.admin.Util.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理APP - 首页信息 Response VO")
@Data
public class AppInfoRespVO {
    /** 登录IP */
    @Schema(description = "登录IP")
    private String ip;
    /** 已提交报告 */
    @Schema(description = "已提交报告")
    private Long commited;
    @Schema(description = "已签订合同")
    /** 已签订合同 */

    private Long signed;
    @Schema(description = "待完成任务")
    /** 待完成任务 */

    private Long todo;
}
