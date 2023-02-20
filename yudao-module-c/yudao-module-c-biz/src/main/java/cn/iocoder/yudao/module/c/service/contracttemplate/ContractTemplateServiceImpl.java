package cn.iocoder.yudao.module.c.service.contracttemplate;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.c.controller.admin.contracttemplate.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.contracttemplate.ContractTemplateDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.c.convert.contracttemplate.ContractTemplateConvert;
import cn.iocoder.yudao.module.c.dal.mysql.contracttemplate.ContractTemplateMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.c.enums.ErrorCodeConstants.*;

/**
 * 合同表单模板 Service 实现类
 *
 * @author jack
 */
@Service
@Validated
public class ContractTemplateServiceImpl implements ContractTemplateService {

    @Resource
    private ContractTemplateMapper ontractTemplateMapper;

    @Override
    public Long createontractTemplate(ContractTemplateCreateReqVO createReqVO) {
        // 插入
        ContractTemplateDO ontractTemplate = ContractTemplateConvert.INSTANCE.convert(createReqVO);
        ontractTemplateMapper.insert(ontractTemplate);
        // 返回
        return ontractTemplate.getId();
    }

    @Override
    public void updateontractTemplate(ContractTemplateUpdateReqVO updateReqVO) {
        // 校验存在
        validateontractTemplateExists(updateReqVO.getId());
        // 更新
        ContractTemplateDO updateObj = ContractTemplateConvert.INSTANCE.convert(updateReqVO);
        ontractTemplateMapper.updateById(updateObj);
    }

    @Override
    public void deleteontractTemplate(Long id) {
        // 校验存在
        validateontractTemplateExists(id);
        // 删除
        ontractTemplateMapper.deleteById(id);
    }

    private void validateontractTemplateExists(Long id) {
        if (ontractTemplateMapper.selectById(id) == null) {
            throw exception(CONTRACT_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public ContractTemplateDO getontractTemplate(Long id) {
        return ontractTemplateMapper.selectById(id);
    }

    @Override
    public List<ContractTemplateDO> getontractTemplateList(Collection<Long> ids) {
        return ontractTemplateMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ContractTemplateDO> getontractTemplatePage(ContractTemplatePageReqVO pageReqVO) {
        return ontractTemplateMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ContractTemplateDO> getontractTemplateList(ContractTemplateExportReqVO exportReqVO) {
        return ontractTemplateMapper.selectList(exportReqVO);
    }

}
