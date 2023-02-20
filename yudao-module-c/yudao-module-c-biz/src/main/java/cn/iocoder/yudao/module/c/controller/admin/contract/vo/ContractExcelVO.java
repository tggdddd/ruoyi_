package cn.iocoder.yudao.module.c.controller.admin.contract.vo;

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
 * 合同表单 Excel VO
 *
 * @author jack
 */
@Data
public class ContractExcelVO {

    @ExcelProperty("主键(自增策略)")
    private Long id;

    @ExcelProperty("system_user表用户ID")
    private Long userId;

    @ExcelProperty("用户的真实姓名")
    private String name;

    @ExcelProperty("用户的身份证号")
    private String identityCard;
    @Schema(description = "岗位", required = true, example = "开发人员")
    private String post;
    @ExcelProperty("薪资")
    private BigDecimal salary;

    @ExcelProperty("附件")
    private String attach;

    @ExcelProperty("业绩要求")
    private String performanceRequirements;

    @ExcelProperty("违约条款")
    private String defaultClause;

    @ExcelProperty(value = "合同状态 0未签订 1签订 2到期 3终止", converter = DictConvert.class)
    @DictFormat("contract_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;

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

}
