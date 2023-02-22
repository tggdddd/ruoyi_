package cn.iocoder.yudao.module.c.service.contracttemplate;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.c.controller.admin.contracttemplate.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.contracttemplate.ContractTemplateDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 合同表单模板 Service 接口
 *
 * @author jack
 */
public interface ContractTemplateService {

    /**
     * 创建合同表单模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createontractTemplate(@Valid ContractTemplateCreateReqVO createReqVO);

    /**
     * 更新合同表单模板
     *
     * @param updateReqVO 更新信息
     */
    void updateontractTemplate(@Valid ContractTemplateUpdateReqVO updateReqVO);

    /**
     * 更新审核的状态
     *
     * @param id 编号
     * @param result 结果
     */
    void updateontractResult(Long id, Integer result);
    /**
     * 删除合同表单模板
     *
     * @param id 编号
     */
    void deleteontractTemplate(Long id);

    /**
     * 获得合同表单模板
     *
     * @param id 编号
     * @return 合同表单模板
     */
    ContractTemplateDO getontractTemplate(Long id);

    /**
     * 获得合同表单模板列表
     *
     * @param ids 编号
     * @return 合同表单模板列表
     */
    List<ContractTemplateDO> getontractTemplateList(Collection<Long> ids);

    /**
     * 获得合同表单模板分页
     *
     * @param pageReqVO 分页查询
     * @return 合同表单模板分页
     */
    PageResult<ContractTemplateDO> getontractTemplatePage(ContractTemplatePageReqVO pageReqVO);

    /**
     * 获得合同表单模板列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 合同表单模板列表
     */
    List<ContractTemplateDO> getontractTemplateList(ContractTemplateExportReqVO exportReqVO);

}
