package cn.iocoder.yudao.module.c.Job;

import cn.iocoder.yudao.module.c.dal.dataobject.PerformanceReportRequest.PerformanceReportRequestDO;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName VO
 * @Description
 * @Author 15014
 * @Time 2023/3/3 14:04
 * @Version 1.0
 */
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VO {

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
    private String jobKey;
    private String triggerKey;
    private Long reportId;
}
