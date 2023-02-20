package cn.iocoder.yudao.module.c.convert.contracttemplate;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.c.controller.admin.contracttemplate.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.contracttemplate.ContractTemplateDO;

/**
 * 合同表单模板 Convert
 *
 * @author jack
 */
@Mapper
public interface ContractTemplateConvert {

    ContractTemplateConvert INSTANCE = Mappers.getMapper(ContractTemplateConvert.class);

    ContractTemplateDO convert(ContractTemplateCreateReqVO bean);

    ContractTemplateDO convert(ContractTemplateUpdateReqVO bean);

    ContractTemplateRespVO convert(ContractTemplateDO bean);

    List<ContractTemplateRespVO> convertList(List<ContractTemplateDO> list);

    PageResult<ContractTemplateRespVO> convertPage(PageResult<ContractTemplateDO> page);

    List<ContractTemplateExcelVO> convertList02(List<ContractTemplateDO> list);

}
