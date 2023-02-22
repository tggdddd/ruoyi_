package cn.iocoder.yudao.module.c.service.contracttemplate;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.iocoder.yudao.framework.security.core.LoginUser;
import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.c.controller.admin.contracttemplate.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.contracttemplate.ContractTemplateDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.c.convert.contracttemplate.ContractTemplateConvert;
import cn.iocoder.yudao.module.c.dal.mysql.contracttemplate.ContractTemplateMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.bpm.enums.ErrorCodeConstants.OA_LEAVE_NOT_EXISTS;
import static cn.iocoder.yudao.module.c.enums.ErrorCodeConstants.*;

/**
 * 合同表单模板 Service 实现类
 *
 * @author jack
 */
@Service
@Validated
public class ContractTemplateServiceImpl implements ContractTemplateService {
    /**
     * 对应的流程定义 KEY
     */
    public static final String PROCESS_KEY = "oa_contract_template";

    @Resource
    private ContractTemplateMapper ontractTemplateMapper;
    @Resource
    private BpmProcessInstanceApi processInstanceApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createontractTemplate(ContractTemplateCreateReqVO createReqVO) {
        // 插入表单
        ContractTemplateDO ontractTemplate = ContractTemplateConvert.INSTANCE.convert(createReqVO)
                .setResult(BpmProcessInstanceResultEnum.PROCESS.getResult());;
        ontractTemplateMapper.insert(ontractTemplate);

        // 发起 BPM 流程
        String processInstanceId = processInstanceApi.createProcessInstance(getLoginUserId(),
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                        .setBusinessKey(String.valueOf(ontractTemplate.getId())));


        // 将工作流的编号，更新到 表单中
        ontractTemplateMapper.updateById(new ContractTemplateDO().setId(ontractTemplate.getId()).setProcessInstanceId(processInstanceId));
        // 返回
        return ontractTemplate.getId();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateontractTemplate(ContractTemplateUpdateReqVO updateReqVO) {
        // 校验存在
        validateontractTemplateExists(updateReqVO.getId());
        // 发起 BPM 流程
        String processInstanceId = processInstanceApi.createProcessInstance(getLoginUserId(),
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                        .setBusinessKey(String.valueOf(updateReqVO.getId())));
        

        // 更新标新数据
        // 更新最新工作流编号
        // 更新审核状态
        ContractTemplateDO updateObj = ContractTemplateConvert.INSTANCE.convert(updateReqVO)
                .setProcessInstanceId(processInstanceId)
                .setResult(BpmProcessInstanceResultEnum.PROCESS.getResult());
        ontractTemplateMapper.updateById(updateObj);
    }

    @Override
    public void updateontractResult(Long id, Integer result) {
        validateontractTemplateExists(id);
        ontractTemplateMapper.updateById(new ContractTemplateDO().setId(id).setResult(result));
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
