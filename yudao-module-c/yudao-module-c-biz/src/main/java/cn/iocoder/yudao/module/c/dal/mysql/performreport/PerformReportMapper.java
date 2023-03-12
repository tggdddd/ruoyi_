package cn.iocoder.yudao.module.c.dal.mysql.performreport;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.c.dal.dataobject.performreport.PerformReportDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.*;

/**
 * 业绩信息 Mapper
 *
 * @author jack
 */
@Mapper
public interface PerformReportMapper extends BaseMapperX<PerformReportDO> {

    default PageResult<PerformReportDO> selectPage(PerformReportPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PerformReportDO>()
                .eqIfPresent(PerformReportDO::getUserId, reqVO.getUserId())
                .eqIfPresent(PerformReportDO::getContractId, reqVO.getContractId())
                .eqIfPresent(PerformReportDO::getPostId, reqVO.getPostId())
                .eqIfPresent(PerformReportDO::getBpmProcessInstanceExtId, reqVO.getBpmProcessInstanceExtId())
                .eqIfPresent(PerformReportDO::getBpmProcessDefinitionId, reqVO.getBpmProcessDefinitionId())
                .betweenIfPresent(PerformReportDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PerformReportDO::getExpireTime,reqVO.getExpireTime())
                .eqIfPresent(PerformReportDO::getProcessInstanceId, reqVO.getProcessInstanceId())
                .eqIfPresent(PerformReportDO::getStatus,reqVO.getStatus())
                .orderByDesc(PerformReportDO::getId));
    }

    default List<PerformReportDO> selectList(PerformReportExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PerformReportDO>()
                .eqIfPresent(PerformReportDO::getUserId, reqVO.getUserId())
                .eqIfPresent(PerformReportDO::getContractId, reqVO.getContractId())
                .eqIfPresent(PerformReportDO::getPostId, reqVO.getPostId())
                .eqIfPresent(PerformReportDO::getBpmProcessInstanceExtId, reqVO.getBpmProcessInstanceExtId())
                .eqIfPresent(PerformReportDO::getBpmProcessDefinitionId, reqVO.getBpmProcessDefinitionId())
                .betweenIfPresent(PerformReportDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PerformReportDO::getExpireTime,reqVO.getExpireTime())
                .eqIfPresent(PerformReportDO::getProcessInstanceId, reqVO.getProcessInstanceId())
                .eqIfPresent(PerformReportDO::getStatus,reqVO.getStatus())
                .orderByDesc(PerformReportDO::getId));
    }

    List<PerformReportDO> getUnSubmit();

}
