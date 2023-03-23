package cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
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

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("合同ID")
    private Long contractId;

    @ExcelProperty("开始时间")
    private String startTime;

    @ExcelProperty("终止时间")
    private String endTime;

    @ExcelProperty("通知时间")
    private String notifyTime;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("提交流程id")
    private String processDefitionId;

    @ExcelProperty("未交通知时间")
    private String urgeTime;

    @ExcelProperty("用户id")
    private Long userId;
    @ExcelProperty("岗位")
    private String postName;
    @ExcelIgnore
    private Long postId;
    @ExcelProperty("部门")
    private String deptName;
    @ExcelIgnore
    private Long deptId;
}
