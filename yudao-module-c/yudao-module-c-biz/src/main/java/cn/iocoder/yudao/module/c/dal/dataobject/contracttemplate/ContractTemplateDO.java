package cn.iocoder.yudao.module.c.dal.dataobject.contracttemplate;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import cn.iocoder.yudao.module.c.enums.dal.ContractTemplateEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 合同表单模板 DO
 *
 * @author jack
 */
@TableName("c_contract_template")
@KeySequence("c_contract_template_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractTemplateDO extends BaseDO {

    /**
     * 主键(自增策略)
     */
    @TableId
    private Long id;
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
    private Integer postId;
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
     * 合同状态 0停用 1启动
     *
     * 枚举 {@link ContractTemplateEnum}
     */
    private Byte status;
    /**
     * 甲方
     */
    private String firstParty;
    /**
     * 合同开始时间
     */
    private LocalDateTime startTime;
    /**
     * 合同结束时间
     */
    private LocalDateTime endTime;
    /**
     * 审核的结果
     *
     * 枚举 {@link BpmProcessInstanceResultEnum}
     * 考虑到简单，所以直接复用了 BpmProcessInstanceResultEnum 枚举，也可以自己定义一个枚举哈
     */
    private Integer result;

    /**
     * 对应的流程编号
     *
     * 关联 ProcessInstance 的 id 属性
     */
    private String processInstanceId;
}
