package cn.iocoder.yudao.module.c.dal.dataobject.contract;

import com.sun.xml.internal.bind.v2.TODO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 合同表单 DO
 *
 * @author jack
 */
@TableName("c_contract")
@KeySequence("c_contract_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractDO extends BaseDO {

    /**
     * 主键(自增策略)
     */
    @TableId
    private Long id;
    /**
     * system_user表用户ID
     */
    private Long userId;
    /**
     * 用户的真实姓名
     */
    private String name;
    /**
     * 用户的身份证号
     */
    private String identityCard;
    /**
     * 薪资
     */
    private BigDecimal salary;
      /**岗位  */
      private String post;
    /**
     * 附件
     */
    private String attach;
    /**
     * 业绩要求
     */
    private String performanceRequirements;
    /**
     * 违约条款
     */
    private String defaultClause;
    /**
     * 合同状态 0未签订 1签订 2到期 3终止
     *
     * 枚举 {@link TODO contract_status 对应的类}
     */
    private Byte status;
    /**
     * 甲方
     */
    private String firstParty;
    /**
     * 签约时间
     */
    private LocalDateTime signedTime;
    /**
     * 合同开始时间
     */
    private LocalDateTime startTime;
    /**
     * 合同结束时间
     */
    private LocalDateTime endTime;

}
