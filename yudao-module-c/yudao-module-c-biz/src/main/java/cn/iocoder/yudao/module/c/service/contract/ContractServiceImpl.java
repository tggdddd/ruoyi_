package cn.iocoder.yudao.module.c.service.contract;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.c.controller.admin.contract.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.c.convert.contract.ContractConvert;
import cn.iocoder.yudao.module.c.dal.mysql.contract.ContractMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.c.enums.ErrorCodeConstants.*;

/**
 * 合同表单 Service 实现类
 *
 * @author jack
 */
@Service
@Validated
public class ContractServiceImpl implements ContractService {

    @Resource
    private ContractMapper ontractMapper;

    @Override
    public Long createontract(ContractCreateReqVO createReqVO) {
        // 插入
        ContractDO ontract = ContractConvert.INSTANCE.convert(createReqVO);
        ontractMapper.insert(ontract);
        // 返回
        return ontract.getId();
    }

    @Override
    public void updateontract(ContractUpdateReqVO updateReqVO) {
        // 校验存在
        validateontractExists(updateReqVO.getId());
        // 更新
        ContractDO updateObj = ContractConvert.INSTANCE.convert(updateReqVO);
        ontractMapper.updateById(updateObj);
    }

    @Override
    public void deleteontract(Long id) {
        // 校验存在
        validateontractExists(id);
        // 删除
        ontractMapper.deleteById(id);
    }

    private void validateontractExists(Long id) {
        if (ontractMapper.selectById(id) == null) {
            throw exception(CONTRACT_NOT_EXISTS);
        }
    }

    @Override
    public ContractDO getontract(Long id) {
        return ontractMapper.selectById(id);
    }

    @Override
    public List<ContractDO> getontractList(Collection<Long> ids) {
        return ontractMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ContractDO> getontractPage(ContractPageReqVO pageReqVO) {
        return ontractMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ContractDO> getontractList(ContractExportReqVO exportReqVO) {
        return ontractMapper.selectList(exportReqVO);
    }

}
