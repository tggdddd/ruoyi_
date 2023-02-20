package cn.iocoder.yudao.module.c.dal.mysql.PerformanceReportRequest;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.c.dal.dataobject.PerformanceReportRequest.PerformanceReportRequestDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.*;

/**
 * 业绩定义 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PerformanceReportRequestMapper extends BaseMapperX<PerformanceReportRequestDO> {

    default PageResult<PerformanceReportRequestDO> selectPage(PerformanceReportRequestPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PerformanceReportRequestDO>()
                .eqIfPresent(PerformanceReportRequestDO::getContractId, reqVO.getContractId())
                .eqIfPresent(PerformanceReportRequestDO::getFormIds, reqVO.getFormIds())
                .betweenIfPresent(PerformanceReportRequestDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(PerformanceReportRequestDO::getEndTime, reqVO.getEndTime())
                .betweenIfPresent(PerformanceReportRequestDO::getNotifyTime, reqVO.getNotifyTime())
                .eqIfPresent(PerformanceReportRequestDO::getNotifyDuration, reqVO.getNotifyDuration())
                .betweenIfPresent(PerformanceReportRequestDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PerformanceReportRequestDO::getId));
    }

    default List<PerformanceReportRequestDO> selectList(PerformanceReportRequestExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PerformanceReportRequestDO>()
                .eqIfPresent(PerformanceReportRequestDO::getContractId, reqVO.getContractId())
                .eqIfPresent(PerformanceReportRequestDO::getFormIds, reqVO.getFormIds())
                .betweenIfPresent(PerformanceReportRequestDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(PerformanceReportRequestDO::getEndTime, reqVO.getEndTime())
                .betweenIfPresent(PerformanceReportRequestDO::getNotifyTime, reqVO.getNotifyTime())
                .eqIfPresent(PerformanceReportRequestDO::getNotifyDuration, reqVO.getNotifyDuration())
                .betweenIfPresent(PerformanceReportRequestDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PerformanceReportRequestDO::getId));
    }

}
