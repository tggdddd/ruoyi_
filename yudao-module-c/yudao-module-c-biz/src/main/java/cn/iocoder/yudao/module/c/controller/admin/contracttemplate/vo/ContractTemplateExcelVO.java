package cn.iocoder.yudao.module.c.controller.admin.contracttemplate.vo;

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


/**
 * 合同表单模板 Excel VO
 *
 * @author jack
 */
@Data
public class ContractTemplateExcelVO {

    @ExcelProperty("主键(自增策略)")
    private Long id;

    @ExcelProperty("用户的真实姓名")
    private String name;

    @ExcelProperty("用户的身份证号")
    private String identityCard;
    @Schema(description = "岗位", required = true, example = "1")
    private Integer postId;
    @ExcelProperty("薪资")
    private BigDecimal salary;

    @ExcelProperty("附件")
    private String attach;

    @ExcelProperty("业绩要求")
    private String performanceRequirements;

    @ExcelProperty("违约条款")
    private String defaultClause;

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
    @Schema(description = "状态-参见 bpm_process_instance_result 枚举", required = true, example = "1")
    private Integer result;
}
