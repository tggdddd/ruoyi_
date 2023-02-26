package cn.iocoder.yudao.module.c.dal.mysql.contracttemplate;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.c.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.module.c.dal.dataobject.contracttemplate.ContractTemplateDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.c.controller.admin.contracttemplate.vo.*;

/**
 * 合同表单模板 Mapper
 *
 * @author jack
 */
@Mapper
public interface ContractTemplateMapper extends BaseMapperX<ContractTemplateDO> {

    default PageResult<ContractTemplateDO> selectPage(ContractTemplatePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ContractTemplateDO>()
                .likeIfPresent(ContractTemplateDO::getName, reqVO.getName())
                .eqIfPresent(ContractTemplateDO::getResult, reqVO.getResult())
                .eqIfPresent(ContractTemplateDO::getIdentityCard, reqVO.getIdentityCard())
                .eqIfPresent(ContractTemplateDO::getSalary, reqVO.getSalary())
                .eqIfPresent(ContractTemplateDO::getPerformanceRequirements, reqVO.getPerformanceRequirements())
                .eqIfPresent(ContractTemplateDO::getDefaultClause, reqVO.getDefaultClause())
                .eqIfPresent(ContractTemplateDO::getFirstParty, reqVO.getFirstParty())
                .eqIfPresent(ContractTemplateDO::getPostId,reqVO.getPostId())
                .betweenIfPresent(ContractTemplateDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(ContractTemplateDO::getEndTime, reqVO.getEndTime())
                .betweenIfPresent(ContractTemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ContractTemplateDO::getId));
    }

    default List<ContractTemplateDO> selectList(ContractTemplateExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ContractTemplateDO>()
                .likeIfPresent(ContractTemplateDO::getName, reqVO.getName())
                .eqIfPresent(ContractTemplateDO::getResult, reqVO.getResult())
                .eqIfPresent(ContractTemplateDO::getIdentityCard, reqVO.getIdentityCard())
                .eqIfPresent(ContractTemplateDO::getSalary, reqVO.getSalary())
                .eqIfPresent(ContractTemplateDO::getPerformanceRequirements, reqVO.getPerformanceRequirements())
                .eqIfPresent(ContractTemplateDO::getDefaultClause, reqVO.getDefaultClause())
                .eqIfPresent(ContractTemplateDO::getFirstParty, reqVO.getFirstParty())
                .eqIfPresent(ContractTemplateDO::getPostId,reqVO.getPostId())
                .betweenIfPresent(ContractTemplateDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(ContractTemplateDO::getEndTime, reqVO.getEndTime())
                .betweenIfPresent(ContractTemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ContractTemplateDO::getId));
    }

}
