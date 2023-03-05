package cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 业绩定义 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class PerformanceReportRequestExcelVO {

    @ExcelProperty("主键(自增策略)")
    private Long id;

    @ExcelProperty("关联的合同表ID")
    private Long contractId;

    @ExcelProperty("业绩表单提交开始开始时间")
    private String startTime;

    @ExcelProperty("业绩表单提交终止时间")
    private String endTime;

    @ExcelProperty("业绩表单提交通知时间")
    private String notifyTime;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("业绩提交流程id")
    private String processDefitionId;

    @ExcelProperty("业绩表单未交通知时间")
    private String urgeTime;

    @ExcelProperty("用户id")
    private Long userId;

    @Schema(description = "dept")
    private Long postId;

}
