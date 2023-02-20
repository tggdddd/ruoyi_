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

import com.alibaba.excel.annotation.ExcelProperty;

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

    @ExcelProperty("业绩提交表单ids")
    private String formIds;

    @ExcelProperty("业绩表单提交开始开始时间")
    private LocalDateTime startTime;

    @ExcelProperty("业绩表单提交终止时间")
    private LocalDateTime endTime;

    @ExcelProperty("业绩表单提交通知时间")
    private LocalDateTime notifyTime;

    @ExcelProperty("业绩表单提交通知频率")
    private LocalDateTime notifyDuration;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
