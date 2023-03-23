package cn.iocoder.yudao.module.c.dal.dataobject.performreport;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 业绩信息 DO
 *
 * @author jack
 */
@TableName("c_perform_report")
@KeySequence("c_perform_report_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformReportDO extends BaseDO {

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
     * 合同表单id
     */
    private Long contractId;
    /**
     * 用户的岗位id
     */
    private Long postId;
    /**
     * 提交的流程id
     */
    private Long bpmProcessInstanceExtId;
    /**
     * 流程定义id
     */
    private String bpmProcessDefinitionId;
    /**
     * 流程实例的编号
     */
    private String processInstanceId;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
    private Integer status;
    /** 部门id  */
    private Long deptId;
}
