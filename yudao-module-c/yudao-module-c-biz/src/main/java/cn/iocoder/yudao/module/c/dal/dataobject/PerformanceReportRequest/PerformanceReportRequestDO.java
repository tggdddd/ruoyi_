package cn.iocoder.yudao.module.c.dal.dataobject.PerformanceReportRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 业绩定义 DO
 *
 * @author 芋道源码
 */
@TableName("c_performance_report_request")
@KeySequence("c_performance_report_request_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceReportRequestDO extends BaseDO {

    /**
     * 主键(自增策略)
     */
    @TableId
    private Long id;
    /**
     * 关联的合同表ID
     */
    private Long contractId;
    /**
     * 业绩表单提交开始开始时间
     */
    private String startTime;
    /**
     * 业绩表单提交终止时间
     */
    private String endTime;
    /**
     * 业绩表单提交通知时间
     */
    private String notifyTime;
    /**
     * 业绩提交流程id
     */
    private String processDefitionId;
    /**
     * 业绩表单未交通知时间
     */
    private String urgeTime;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 岗位id
     */
    private Long postId;
    /** 部门id  */
    private Long deptId;
}
