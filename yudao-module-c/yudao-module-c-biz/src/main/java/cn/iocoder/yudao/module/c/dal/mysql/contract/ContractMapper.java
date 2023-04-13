package cn.iocoder.yudao.module.c.dal.mysql.contract;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.c.Util.UserDeptUtils;
import cn.iocoder.yudao.module.c.controller.admin.contract.vo.ContractExportReqVO;
import cn.iocoder.yudao.module.c.controller.admin.contract.vo.ContractPageReqVO;
import cn.iocoder.yudao.module.c.dal.dataobject.contract.ContractDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 合同表单 Mapper
 *
 * @author jack
 */
@Mapper
public interface ContractMapper extends BaseMapperX<ContractDO> {

    default PageResult<ContractDO> selectPage(ContractPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ContractDO>()
                .eqIfPresent(ContractDO::getUserId, reqVO.getUserId())
                .eqIfPresent(ContractDO::getResult, reqVO.getResult())
                .likeIfPresent(ContractDO::getName, reqVO.getName())
                .eqIfPresent(ContractDO::getIdentityCard, reqVO.getIdentityCard())
                .eqIfPresent(ContractDO::getSalary, reqVO.getSalary())
                .eqIfPresent(ContractDO::getPerformanceRequirements, reqVO.getPerformanceRequirements())
                .eqIfPresent(ContractDO::getDefaultClause, reqVO.getDefaultClause())
                .eqIfPresent(ContractDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ContractDO::getPostId,reqVO.getPostId())
                .eqIfPresent(ContractDO::getFirstParty, reqVO.getFirstParty())
                .betweenIfPresent(ContractDO::getSignedTime, reqVO.getSignedTime())
                .betweenIfPresent(ContractDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(ContractDO::getEndTime, reqVO.getEndTime())
                .betweenIfPresent(ContractDO::getCreateTime, reqVO.getCreateTime())
                .inIfPresent(ContractDO::getUserId, UserDeptUtils.getUserByDeptIds(UserDeptUtils.getSubDeptIdsAnd(reqVO.getDeptId())))
                .orderByDesc(ContractDO::getId));
    }

    default List<ContractDO> selectList(ContractExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ContractDO>()
                .eqIfPresent(ContractDO::getUserId, reqVO.getUserId())
                .eqIfPresent(ContractDO::getResult, reqVO.getResult())
                .likeIfPresent(ContractDO::getName, reqVO.getName())
                .eqIfPresent(ContractDO::getIdentityCard, reqVO.getIdentityCard())
                .eqIfPresent(ContractDO::getSalary, reqVO.getSalary())
                .eqIfPresent(ContractDO::getPerformanceRequirements, reqVO.getPerformanceRequirements())
                .eqIfPresent(ContractDO::getDefaultClause, reqVO.getDefaultClause())
                .eqIfPresent(ContractDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ContractDO::getPostId,reqVO.getPostId())
                .eqIfPresent(ContractDO::getFirstParty, reqVO.getFirstParty())
                .betweenIfPresent(ContractDO::getSignedTime, reqVO.getSignedTime())
                .betweenIfPresent(ContractDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(ContractDO::getEndTime, reqVO.getEndTime())
                .betweenIfPresent(ContractDO::getCreateTime, reqVO.getCreateTime())
                .inIfPresent(ContractDO::getUserId, UserDeptUtils.getUserByDeptIds(UserDeptUtils.getSubDeptIdsAnd(reqVO.getDeptId())))
                .orderByDesc(ContractDO::getId));
    }

}
