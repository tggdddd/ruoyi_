package cn.iocoder.yudao.module.c.dal.mysql.performreport;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.PerformReportExportReqVO;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.PerformReportPageReqVO;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.StatisticsRespVO;
import cn.iocoder.yudao.module.c.dal.dataobject.performreport.PerformReportDO;
import cn.iocoder.yudao.module.c.enums.dal.ReportEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 业绩信息 Mapper
 *
 * @author jack
 */
@Mapper
public interface PerformReportMapper extends BaseMapperX<PerformReportDO> {

    default PageResult<PerformReportDO> selectPage(PerformReportPageReqVO reqVO) {
        return selectPage1(reqVO, null);
    }
    default PageResult<PerformReportDO> selectPage1(PerformReportPageReqVO reqVO,List userIds) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PerformReportDO>()
                .eqIfPresent(PerformReportDO::getUserId, reqVO.getUserId())
                .eqIfPresent(PerformReportDO::getContractId, reqVO.getContractId())
                .eqIfPresent(PerformReportDO::getPostId, reqVO.getPostId())
                .eqIfPresent(PerformReportDO::getBpmProcessInstanceExtId, reqVO.getBpmProcessInstanceExtId())
                .eqIfPresent(PerformReportDO::getBpmProcessDefinitionId, reqVO.getBpmProcessDefinitionId())
                .betweenIfPresent(PerformReportDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PerformReportDO::getExpireTime, reqVO.getExpireTime())
                .eqIfPresent(PerformReportDO::getProcessInstanceId, reqVO.getProcessInstanceId())
                .eqIfPresent(PerformReportDO::getStatus, reqVO.getStatus())
                .in(userIds!=null,PerformReportDO::getUserId,
                        userIds)
                .orderByDesc(PerformReportDO::getId));
    }


    default List<PerformReportDO> selectList(PerformReportExportReqVO reqVO) {
        return selectList(reqVO, null);
    }
    default List<PerformReportDO> selectList(PerformReportExportReqVO reqVO, List userIds) {
        return selectList(new LambdaQueryWrapperX<PerformReportDO>()
                .eqIfPresent(PerformReportDO::getUserId, reqVO.getUserId())
                .eqIfPresent(PerformReportDO::getContractId, reqVO.getContractId())
                .eqIfPresent(PerformReportDO::getPostId, reqVO.getPostId())
                .eqIfPresent(PerformReportDO::getBpmProcessInstanceExtId, reqVO.getBpmProcessInstanceExtId())
                .eqIfPresent(PerformReportDO::getBpmProcessDefinitionId, reqVO.getBpmProcessDefinitionId())
                .betweenIfPresent(PerformReportDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PerformReportDO::getExpireTime, reqVO.getExpireTime())
                .eqIfPresent(PerformReportDO::getProcessInstanceId, reqVO.getProcessInstanceId())
                .eqIfPresent(PerformReportDO::getStatus, reqVO.getStatus())
                .in(userIds!=null,PerformReportDO::getUserId,
                        userIds)
                .orderByDesc(PerformReportDO::getId));
    }

    List<PerformReportDO> getUnSubmit();

    default List<StatisticsRespVO> getStatistics(Long userId) {
        List<StatisticsRespVO> result = new ArrayList<>(4);
        result.add(
                new StatisticsRespVO()
                        .setName("未提交")
                        .setValue(
                                selectCount(new LambdaQueryWrapper<PerformReportDO>()
                                        .eq(userId != null, PerformReportDO::getUserId, userId)
                                        .eq(PerformReportDO::getStatus, ReportEnum.Create.getType())
                                )));
        result.add( new StatisticsRespVO()
                .setName("已提交")
                .setValue(
                        selectCount(new LambdaQueryWrapper<PerformReportDO>()
                                .eq(userId != null, PerformReportDO::getUserId, userId)
                                .eq(PerformReportDO::getStatus , ReportEnum.SUBMIT.getType())
                        )));
        result.add( new StatisticsRespVO()
                .setName("不通过")
                .setValue(
                        selectCount(new LambdaQueryWrapper<PerformReportDO>()
                                .eq(userId != null, PerformReportDO::getUserId, userId)
                                .eq(PerformReportDO::getStatus , ReportEnum.REJECT.getType())
                        )));
        result.add( new StatisticsRespVO()
                .setName("已完成")
                .setValue(
                        selectCount(new LambdaQueryWrapper<PerformReportDO>()
                                .eq(userId != null, PerformReportDO::getUserId, userId)
                                .eq(PerformReportDO::getStatus , ReportEnum.COMPLETION.getType())
                        )));
        result.add( new StatisticsRespVO()
                .setName("已超时")
                .setValue(
                        selectCount(new LambdaQueryWrapper<PerformReportDO>()
                                .eq(userId != null, PerformReportDO::getUserId, userId)
                                .eq(PerformReportDO::getStatus , ReportEnum.EXPIRE.getType())
                        )));
        return result;
    }
}
