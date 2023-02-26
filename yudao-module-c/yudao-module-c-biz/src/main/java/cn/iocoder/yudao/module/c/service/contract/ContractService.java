package cn.iocoder.yudao.module.c.service.contract;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.c.controller.admin.contract.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 合同表单 Service 接口
 *
 * @author jack
 */
public interface ContractService {

    /**
     * 创建合同表单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createontract(@Valid ContractCreateReqVO createReqVO);

    /**
     * 更新合同表单
     *
     * @param updateReqVO 更新信息
     */
    void updateontract(@Valid ContractUpdateReqVO updateReqVO);

    /**
     * 更新审核的状态
     *
     * @param id 编号
     * @param result 结果
     */
    void updateontractResult(Long id, Integer result);
    /**
     * 删除合同表单
     *
     * @param id 编号
     */
    void deleteontract(Long id);
    /**
     * 删除合同表单 必删除
     *
     * @param id 编号
     */
    void deleteContractAbs(Long id);
    /**
     * 获得合同表单
     *
     * @param id 编号
     * @return 合同表单
     */
    ContractDO getontract(Long id);

    /**
     * 获得合同表单列表
     *
     * @param ids 编号
     * @return 合同表单列表
     */
    List<ContractDO> getontractList(Collection<Long> ids);

    /**
     * 获得合同表单分页
     *
     * @param pageReqVO 分页查询
     * @return 合同表单分页
     */
    PageResult<ContractDO> getontractPage(ContractPageReqVO pageReqVO);

    /**
     * 获得合同表单列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 合同表单列表
     */
    List<ContractDO> getontractList(ContractExportReqVO exportReqVO);

}
