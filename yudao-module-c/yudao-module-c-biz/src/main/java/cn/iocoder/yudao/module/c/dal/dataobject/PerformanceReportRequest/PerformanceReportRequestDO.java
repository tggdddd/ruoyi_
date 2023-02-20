package cn.iocoder.yudao.module.c.dal.dataobject.PerformanceReportRequest;

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
     * 业绩提交表单ids
     */
    private String formIds;
    /**
     * 业绩表单提交开始开始时间
     */
    private LocalDateTime startTime;
    /**
     * 业绩表单提交终止时间
     */
    private LocalDateTime endTime;
    /**
     * 业绩表单提交通知时间
     */
    private LocalDateTime notifyTime;
    /**
     * 业绩表单提交通知频率
     */
    private LocalDateTime notifyDuration;

}
