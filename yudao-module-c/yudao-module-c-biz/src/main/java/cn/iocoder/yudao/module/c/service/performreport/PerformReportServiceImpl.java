package cn.iocoder.yudao.module.c.service.performreport;

import cn.hutool.core.convert.Convert;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.quartz.core.util.CronUtils;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.instance.BpmProcessInstanceCreateReqVO;
import cn.iocoder.yudao.module.bpm.service.task.BpmProcessInstanceService;
import cn.iocoder.yudao.module.c.Util.StringUtil;
import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.PerformanceReportRequestExportReqVO;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.PerformReportCreateReqVO;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.PerformReportExportReqVO;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.PerformReportPageReqVO;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.PerformReportUpdateReqVO;
import cn.iocoder.yudao.module.c.convert.performreport.PerformReportConvert;
import cn.iocoder.yudao.module.c.dal.dataobject.PerformanceReportRequest.PerformanceReportRequestDO;
import cn.iocoder.yudao.module.c.dal.dataobject.performreport.PerformReportDO;
import cn.iocoder.yudao.module.c.dal.mysql.PerformanceReportRequest.PerformanceReportRequestMapper;
import cn.iocoder.yudao.module.c.dal.mysql.performreport.PerformReportMapper;
import cn.iocoder.yudao.module.c.enums.BussinessFormEnum;
import cn.iocoder.yudao.module.c.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.c.enums.dal.ReportEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.c.enums.ErrorCodeConstants.PERFORM_REPORT_NOT_EXISTS;

/**
 * 业绩信息 Service 实现类
 *
 * @author jack
 */
@Service
@Validated
public class PerformReportServiceImpl implements PerformReportService {

    @Resource
    private PerformReportMapper performReportMapper;
    @Resource
    private BpmProcessInstanceService bpmProcessInstanceService;
    @Resource
    private PerformanceReportRequestMapper performanceReportRequestMapper;

    @Override
    public Long createPerformReport(PerformReportCreateReqVO createReqVO) {
        // 插入
        PerformReportDO performReport = PerformReportConvert.INSTANCE.convert(createReqVO);
        performReportMapper.insert(performReport);
        // 返回
        return performReport.getId();
    }

    @Override
    public void updatePerformReport(PerformReportUpdateReqVO updateReqVO) {
        // 校验存在
        validatePerformReportExists(updateReqVO.getId());
        // 更新
        PerformReportDO updateObj = PerformReportConvert.INSTANCE.convert(updateReqVO);
        performReportMapper.updateById(updateObj);
    }

    @Override
    public void deletePerformReport(Long id) {
        // 校验存在
        validatePerformReportExists(id);
        // 删除
        performReportMapper.deleteById(id);
    }

    private void validatePerformReportExists(Long id) {
        if (performReportMapper.selectById(id) == null) {
            throw exception(PERFORM_REPORT_NOT_EXISTS);
        }
    }

    @Override
    public PerformReportDO getPerformReport(Long id) {
        return performReportMapper.selectById(id);
    }

    @Override
    public List<PerformReportDO> getPerformReportList(Collection<Long> ids) {
        return performReportMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PerformReportDO> getPerformReportPage(PerformReportPageReqVO pageReqVO) {
        return performReportMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PerformReportDO> getPerformReportList(PerformReportExportReqVO exportReqVO) {
        return performReportMapper.selectList(exportReqVO);
    }

    @Override
    public List<PerformReportDO> getUnSubmit() {
        return performReportMapper.getUnSubmit();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createProcessInstance(Long userId, @Valid BpmProcessInstanceCreateReqVO createReqVO, String reportId) {
        // 检验是否已提交
        if(StringUtil.notEmpty(reportId)){
            if (performReportMapper.selectCount(new LambdaQueryWrapper<PerformReportDO>()
                    .eq(PerformReportDO::getId, reportId)
                    .isNotNull(PerformReportDO::getProcessInstanceId)
            ) != 0) {
                throw exception(ErrorCodeConstants.PERFORM_REPORT_REPEAT_SUBMIT);
            }
        }
        String processInstanceId = bpmProcessInstanceService.createProcessInstance(userId, createReqVO);
        // 完成业绩要求
        if (StringUtil.notEmpty(reportId)) {
            performReportMapper.updateById(new PerformReportDO()
                    .setBpmProcessDefinitionId(createReqVO.getProcessDefinitionId())
                    .setUserId(userId)
                    .setProcessInstanceId(processInstanceId)
                    .setId(Long.valueOf(reportId))
                    .setStatus(ReportEnum.SUBMIT.getType()));
        }else {
        //  额外提交
            PerformanceReportRequestDO performanceReportRequestDO = performanceReportRequestMapper.selectList(
                    new PerformanceReportRequestExportReqVO()
                            .setProcessDefitionId(createReqVO.getProcessDefinitionId())
                            .setUserId(userId))
                            .get(0);
            PerformReportDO performReportDO = new PerformReportDO()
                    .setBpmProcessDefinitionId(createReqVO.getProcessDefinitionId())
                    .setUserId(userId)
                    .setPostId(performanceReportRequestDO.getPostId())
                    .setProcessInstanceId(processInstanceId)
                    .setContractId(performanceReportRequestDO.getContractId())
                    .setStatus(ReportEnum.SUBMIT.getType());
            performReportMapper.insert(performReportDO);
        }
        return processInstanceId;
    }

}
