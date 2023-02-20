package cn.iocoder.yudao.module.c.convert.contract;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.c.controller.admin.Util.vo.AttachReqVO;
import cn.iocoder.yudao.module.c.dal.dataobject.contracttemplate.ContractTemplateDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.c.controller.admin.contract.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.contract.ContractDO;

/**
 * 合同表单 Convert
 *
 * @author jack
 */
@Mapper
public interface ContractConvert {

    ContractConvert INSTANCE = Mappers.getMapper(ContractConvert.class);

    ContractDO convert(ContractCreateReqVO bean);

    ContractDO convert(ContractUpdateReqVO bean);

    ContractRespVO convert(ContractDO bean);

    List<ContractRespVO> convertList(List<ContractDO> list);

    PageResult<ContractRespVO> convertPage(PageResult<ContractDO> page);

    List<ContractExcelVO> convertList02(List<ContractDO> list);

    AttachReqVO convert(ContractTemplateDO contractTemplateDO);
    AttachReqVO convert0(ContractDO contractDO);
}
