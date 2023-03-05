package cn.iocoder.yudao.module.c.dal.mysql.PerformanceReportRequest;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.PerformanceReportRequestExportReqVO;
import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.PerformanceReportRequestPageReqVO;
import cn.iocoder.yudao.module.c.dal.dataobject.PerformanceReportRequest.PerformanceReportRequestDO;
import org.apache.ibatis.annotations.Mapper;

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
                .eqIfPresent(PerformanceReportRequestDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(PerformanceReportRequestDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(PerformanceReportRequestDO::getNotifyTime, reqVO.getNotifyTime())
                .betweenIfPresent(PerformanceReportRequestDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(PerformanceReportRequestDO::getProcessDefitionId, reqVO.getProcessDefitionId())
                .eqIfPresent(PerformanceReportRequestDO::getUrgeTime, reqVO.getUrgeTime())
                .eqIfPresent(PerformanceReportRequestDO::getUserId, reqVO.getUserId())
                .eqIfPresent(PerformanceReportRequestDO::getPostId,reqVO.getPostId())
                .orderByDesc(PerformanceReportRequestDO::getId));
    }

    default List<PerformanceReportRequestDO> selectList(PerformanceReportRequestExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PerformanceReportRequestDO>()
                .eqIfPresent(PerformanceReportRequestDO::getContractId, reqVO.getContractId())
                .eqIfPresent(PerformanceReportRequestDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(PerformanceReportRequestDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(PerformanceReportRequestDO::getNotifyTime, reqVO.getNotifyTime())
                .betweenIfPresent(PerformanceReportRequestDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(PerformanceReportRequestDO::getProcessDefitionId, reqVO.getProcessDefitionId())
                .eqIfPresent(PerformanceReportRequestDO::getUrgeTime, reqVO.getUrgeTime())
                .eqIfPresent(PerformanceReportRequestDO::getPostId,reqVO.getPostId())
                .eqIfPresent(PerformanceReportRequestDO::getUserId, reqVO.getUserId())
                .orderByDesc(PerformanceReportRequestDO::getId));
    }

}
