package cn.iocoder.yudao.module.c.controller.admin.contract.vo;

import cn.iocoder.yudao.module.c.enums.ContractStatusConstant;
import cn.iocoder.yudao.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import org.jeecgframework.poi.excel.annotation.Excel;


/**
 * 合同表单 Excel VO
 *
 * @author jack
 */
@Data
public class ContractExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("用户ID")
    private Long userId;

    @ExcelProperty("用户的真实姓名")
    private String name;

    @ExcelProperty("用户的身份证号")
    private String identityCard;
    @ExcelProperty("岗位")
    private String postName;
    @ExcelIgnore
    private Long postId;
    @ExcelProperty("薪资")
    private BigDecimal salary;

    @ExcelProperty("附件")
    private String attach;

    @ExcelProperty("业绩要求")
    private String performanceRequirements;

    @ExcelProperty("违约条款")
    private String defaultClause;

    @ExcelProperty(value = "合同状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.CONTRACT_STATUS)
    private Integer status;

    @ExcelProperty("甲方")
    private String firstParty;

    @ExcelProperty("签约时间")
    private LocalDateTime signedTime;

    @ExcelProperty("合同开始时间")
    private LocalDateTime startTime;

    @ExcelProperty("合同结束时间")
    private LocalDateTime endTime;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;
    @ExcelProperty(value ="审核结果", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.BPM_PROCESS_INSTANCE_RESULT)
    private Integer result;


    @ExcelProperty("部门")
    private String deptName;
    @ExcelIgnore
    private Long deptId;
}
