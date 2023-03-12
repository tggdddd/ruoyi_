package cn.iocoder.yudao.module.c.service.performreport;

import cn.iocoder.yudao.module.bpm.dal.dataobject.task.BpmTaskExtDO;
import cn.iocoder.yudao.module.bpm.enums.definition.BpmModelCategoryEnum;
import cn.iocoder.yudao.module.bpm.framework.flowable.core.BpmFormReduce;
import cn.iocoder.yudao.module.bpm.service.task.BpmProcessInstanceService;
import cn.iocoder.yudao.module.c.dal.dataobject.performreport.PerformReportDO;
import cn.iocoder.yudao.module.c.dal.mysql.performreport.PerformReportMapper;
import cn.iocoder.yudao.module.c.enums.dal.ReportEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName ReportBpmInstanceReduceImpl
 * @Description
 * @Author 肖润杰
 * @Time 2023/3/11 22:07
 * @Version 1.0
 */
@Component("2")
public class ReportBpmInstanceReduceImpl implements BpmFormReduce {
    @Resource
    private PerformReportMapper performReportMapper;

    @Override
    public void create(String processInstanceId) {
    //     已在service完成
    }

    @Override
    public void cancel(String processInstanceId) {
        performReportMapper.update(new PerformReportDO().setStatus(ReportEnum.REJECT.getType()),new LambdaQueryWrapper<PerformReportDO>().eq(PerformReportDO::getProcessInstanceId,processInstanceId));
    }

    @Override
    public void completion(String processInstanceId) {
        performReportMapper.update(new PerformReportDO().setStatus(ReportEnum.COMPLETION.getType()),new LambdaQueryWrapper<PerformReportDO>().eq(PerformReportDO::getProcessInstanceId,processInstanceId));
    }
}
