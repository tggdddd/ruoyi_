package cn.iocoder.yudao.module.c.service.PerformanceReportRequest;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.PerformanceReportRequest.PerformanceReportRequestDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 业绩定义 Service 接口
 *
 * @author 芋道源码
 */
public interface PerformanceReportRequestService {

    /**
     * 创建业绩定义
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPerformanceReportRequest(@Valid PerformanceReportRequestCreateReqVO createReqVO);

    /**
     * 更新业绩定义
     *
     * @param updateReqVO 更新信息
     */
    void updatePerformanceReportRequest(@Valid PerformanceReportRequestUpdateReqVO updateReqVO);

    /**
     * 删除业绩定义
     *
     * @param id 编号
     */
    void deletePerformanceReportRequest(Long id);

    /**
     * 获得业绩定义
     *
     * @param id 编号
     * @return 业绩定义
     */
    PerformanceReportRequestDO getPerformanceReportRequest(Long id);

    /**
     * 获得业绩定义列表
     *
     * @param ids 编号
     * @return 业绩定义列表
     */
    List<PerformanceReportRequestDO> getPerformanceReportRequestList(Collection<Long> ids);

    /**
     * 获得业绩定义分页
     *
     * @param pageReqVO 分页查询
     * @return 业绩定义分页
     */
    PageResult<PerformanceReportRequestDO> getPerformanceReportRequestPage(PerformanceReportRequestPageReqVO pageReqVO);

    /**
     * 获得业绩定义列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 业绩定义列表
     */
    List<PerformanceReportRequestDO> getPerformanceReportRequestList(PerformanceReportRequestExportReqVO exportReqVO);

}
