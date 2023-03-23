package cn.iocoder.yudao.module.c.controller.admin.performreport.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import cn.iocoder.yudao.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnore;
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

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("用户ID")
    private Long userId;

    @ExcelProperty("表单id")
    private Long contractId;

    @ExcelProperty("岗位")
    private String postName;
    @ExcelIgnore
    private Long postId;

    @ExcelProperty("提交流程id")
    private String bpmProcessInstanceExtId;

    @ExcelProperty("流程定义id")
    private String bpmProcessDefinitionId;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("流程实例id")
    private String processInstanceId;
    @ExcelProperty("过期时间")
    private LocalDateTime expireTime;
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.BPM_PROCESS_INSTANCE_RESULT)
    private Integer status;
    @ExcelProperty("部门")
    private String deptName;
    @ExcelIgnore
    private Long deptId;
}
