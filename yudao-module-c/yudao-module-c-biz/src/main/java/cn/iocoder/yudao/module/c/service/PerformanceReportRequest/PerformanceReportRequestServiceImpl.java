package cn.iocoder.yudao.module.c.service.PerformanceReportRequest;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.PerformanceReportRequest.PerformanceReportRequestDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.c.convert.PerformanceReportRequest.PerformanceReportRequestConvert;
import cn.iocoder.yudao.module.c.dal.mysql.PerformanceReportRequest.PerformanceReportRequestMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.c.enums.ErrorCodeConstants.*;

/**
 * 业绩定义 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class PerformanceReportRequestServiceImpl implements PerformanceReportRequestService {

    @Resource
    private PerformanceReportRequestMapper performanceReportRequestMapper;

    @Override
    public Long createPerformanceReportRequest(PerformanceReportRequestCreateReqVO createReqVO) {
        // 插入
        PerformanceReportRequestDO performanceReportRequest = PerformanceReportRequestConvert.INSTANCE.convert(createReqVO);
        performanceReportRequestMapper.insert(performanceReportRequest);
        // 返回
        return performanceReportRequest.getId();
    }

    @Override
    public void updatePerformanceReportRequest(PerformanceReportRequestUpdateReqVO updateReqVO) {
        // 校验存在
        validatePerformanceReportRequestExists(updateReqVO.getId());
        // 更新
        PerformanceReportRequestDO updateObj = PerformanceReportRequestConvert.INSTANCE.convert(updateReqVO);
        performanceReportRequestMapper.updateById(updateObj);
    }

    @Override
    public void deletePerformanceReportRequest(Long id) {
        // 校验存在
        validatePerformanceReportRequestExists(id);
        // 删除
        performanceReportRequestMapper.deleteById(id);
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
    public List<PerformanceReportRequestDO> getPerformanceReportRequestList(PerformanceReportRequestExportReqVO exportReqVO) {
        return performanceReportRequestMapper.selectList(exportReqVO);
    }

}
