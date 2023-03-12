package cn.iocoder.yudao.module.c.controller.admin.performreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 业绩信息 Excel VO
 *
 * @author jack
 */
@Data
public class PerformReportExcelVO {

    @ExcelProperty("主键(自增策略)")
    private Long id;

    @ExcelProperty("system_user表用户ID")
    private Long userId;

    @ExcelProperty("合同表单id")
    private Long contractId;

    @ExcelProperty("用户的岗位id")
    private Long postId;

    @ExcelProperty("提交的流程id")
    private String bpmProcessInstanceExtId;

    @ExcelProperty("流程定义id")
    private String bpmProcessDefinitionId;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("流程实例的编号")
    private String processInstanceId;
    @ExcelProperty("过期时间")
    private LocalDateTime expireTime;
    @ExcelProperty("状态")
    private Integer status;
}
