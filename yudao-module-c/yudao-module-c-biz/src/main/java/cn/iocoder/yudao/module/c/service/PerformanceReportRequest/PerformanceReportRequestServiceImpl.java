package cn.iocoder.yudao.module.c.service.PerformanceReportRequest;

import cn.iocoder.yudao.framework.common.util.collection.MapUtils;
import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.framework.quartz.core.scheduler.SchedulerManager;
import cn.iocoder.yudao.framework.quartz.core.util.CronUtils;
import cn.iocoder.yudao.module.c.Job.VO;
import cn.iocoder.yudao.module.c.convert.performreport.PerformReportConvert;
import cn.iocoder.yudao.module.c.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.module.c.dal.dataobject.performreport.PerformReportDO;
import cn.iocoder.yudao.module.c.dal.mysql.contract.ContractMapper;
import cn.iocoder.yudao.module.c.service.contract.ContractService;
import org.quartz.SchedulerException;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.*;
import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.PerformanceReportRequest.PerformanceReportRequestDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.c.convert.PerformanceReportRequest.PerformanceReportRequestConvert;
import cn.iocoder.yudao.module.c.dal.mysql.PerformanceReportRequest.PerformanceReportRequestMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.c.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.c.enums.JobNameConstant.REPORT_END;
import static cn.iocoder.yudao.module.c.enums.JobNameConstant.REPORT_JOB;
import static cn.iocoder.yudao.module.c.enums.JobNameConstant.REPORT_NOTIFY;
import static cn.iocoder.yudao.module.c.enums.JobNameConstant.REPORT_START;
import static cn.iocoder.yudao.module.c.enums.JobNameConstant.REPORT_URGE;

/**
 * 业绩定义 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class PerformanceReportRequestServiceImpl implements PerformanceReportRequestService {
    @Resource
    private SchedulerManager schedulerManager;

    @Resource
    private PerformanceReportRequestMapper performanceReportRequestMapper;
    @Resource
    private ContractMapper contractMapper;
    private static int retryCount = 3;
    private static int retryInterval = 10;

    @Override
    @Transactional
    public Long createPerformanceReportRequest(PerformanceReportRequestCreateReqVO createReqVO) {
        // 校验时间
        CronUtils.isValid(createReqVO.getStartTime());
        CronUtils.isValidAndNotFrequencyOrNull(createReqVO.getNotifyTime());
        CronUtils.isValidAndNotFrequencyOrNull(createReqVO.getUrgeTime());
        CronUtils.isValidAndNotFrequencyOrNull(createReqVO.getEndTime());
        // 查询合同
        ContractDO contractDO = contractMapper.selectById(createReqVO.getContractId());
        if(contractDO == null){
            throw exception(CONTRACT_NOT_EXISTS);
        }
        // 添加userId
        createReqVO.setUserId(contractDO.getUserId());
        // 添加postId
        createReqVO.setPostId(contractDO.getPostId());
        // 插入表单
        PerformanceReportRequestDO performanceReportRequest = PerformanceReportRequestConvert.INSTANCE.convert(createReqVO);
        performanceReportRequestMapper.insert(performanceReportRequest);
        // 创建开始定时器
        try {
            // 获得参数
            VO vo = PerformanceReportRequestConvert.INSTANCE.convertVO(performanceReportRequest);
            vo.setTriggerKey(REPORT_START+ performanceReportRequest.getId()).setJobKey(String.valueOf(performanceReportRequest.getId()));
            schedulerManager.addJob(performanceReportRequest.getId(),
                    REPORT_JOB,
                    JsonUtils.toJsonString(vo),
                    vo.getStartTime(),
                    3,
                    10,
                    String.valueOf(performanceReportRequest.getId()), REPORT_START+ performanceReportRequest.getId());
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        // 返回
        return performanceReportRequest.getId();
    }

    @Override
    @Transactional
    public void updatePerformanceReportRequest(PerformanceReportRequestUpdateReqVO updateReqVO) {
        // 校验存在
        validatePerformanceReportRequestExists(updateReqVO.getId());
        // 校验时间
        CronUtils.isValid(updateReqVO.getStartTime());
        CronUtils.isValidAndNotFrequencyOrNull(updateReqVO.getNotifyTime());
        CronUtils.isValidAndNotFrequencyOrNull(updateReqVO.getUrgeTime());
        CronUtils.isValidAndNotFrequencyOrNull(updateReqVO.getEndTime());

        // 查询合同
        ContractDO contractDO = contractMapper.selectById(updateReqVO.getContractId());
        if(contractDO == null){
            throw exception(CONTRACT_NOT_EXISTS);
        }
        // 添加userId
        updateReqVO.setUserId(contractDO.getUserId());
        // 添加postId
        updateReqVO.setPostId(contractDO.getPostId());
        // 更新
        PerformanceReportRequestDO updateObj = PerformanceReportRequestConvert.INSTANCE.convert(updateReqVO);
        performanceReportRequestMapper.updateById(updateObj);
    //     更新定时器
        // 修改开始定时器
        try {
            // 获得参数
            VO vo = PerformanceReportRequestConvert.INSTANCE.convertVO(updateReqVO);
            vo.setTriggerKey(REPORT_START+ updateReqVO.getId()).setJobKey(String.valueOf(updateReqVO.getId()));
            schedulerManager.updateOrInsertTrigger(String.valueOf(vo.getId()),
                    REPORT_START+vo.getId(),
                    JsonUtils.toJsonString(vo),
                    updateReqVO.getStartTime(),
                    retryCount,retryInterval);
            } catch (SchedulerException ex) {
            throw new RuntimeException(ex);
        }
    }

        @Override
        @Transactional
    public void deletePerformanceReportRequest(Long id) {
        // 校验存在
        validatePerformanceReportRequestExists(id);
        // 删除
        performanceReportRequestMapper.deleteById(id);
            try {
                schedulerManager.deleteTrigger(REPORT_START+id);
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
    }

    private void validatePerformanceReportRequestExists(Long id) {
        if (performanceReportRequestMapper.selectById(id) == null) {
            throw exception(PERFORMANCE_REPORT_REQUEST_NOT_EXISTS);
        }
    }

    @Override
    public PerformanceReportRequestDO getPerformanceReportRequest(Long id) {
        return performanceReportRequestMapper.selectById(id);
    }

    @Override
    public List<PerformanceReportRequestDO> getPerformanceReportRequestList(Collection<Long> ids) {

        return performanceReportRequestMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PerformanceReportRequestDO> getPerformanceReportRequestPage(PerformanceReportRequestPageReqVO pageReqVO) {
        return performanceReportRequestMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PerformanceReportRequestDO> getAll() {
        return performanceReportRequestMapper.selectList();
    }

    @Override
    public List<PerformanceReportRequestDO> getPerformanceReportRequestList(PerformanceReportRequestExportReqVO exportReqVO) {
        return performanceReportRequestMapper.selectList(exportReqVO);
    }

}
