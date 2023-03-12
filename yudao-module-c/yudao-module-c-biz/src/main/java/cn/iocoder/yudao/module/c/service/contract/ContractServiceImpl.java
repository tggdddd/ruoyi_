package cn.iocoder.yudao.module.c.service.contract;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.iocoder.yudao.framework.common.util.collection.MapUtils;
import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import cn.iocoder.yudao.module.c.convert.contracttemplate.ContractTemplateConvert;
import cn.iocoder.yudao.module.c.dal.dataobject.contracttemplate.ContractTemplateDO;
import cn.iocoder.yudao.module.c.enums.ContractStatusConstant;
import cn.iocoder.yudao.module.c.enums.dal.ContractStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import liquibase.pro.packaged.E;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import cn.iocoder.yudao.module.c.controller.admin.contract.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.c.convert.contract.ContractConvert;
import cn.iocoder.yudao.module.c.dal.mysql.contract.ContractMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUser;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.c.enums.ErrorCodeConstants.*;

/**
 * 合同表单 Service 实现类
 *
 * @author jack
 */
@Service
@Validated
public class ContractServiceImpl implements ContractService {
    /**
     * 对应的流程定义 KEY
     */
    public static final String PROCESS_KEY = "oa_contract";
    public static final String PROCESS_DEL_KEY = "oa_contract_del";
    @Resource
    private ContractMapper ontractMapper;
    @Resource
    private BpmProcessInstanceApi processInstanceApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createontract(ContractCreateReqVO createReqVO) {
        // 插入表单
        ContractDO ontract = ContractConvert.INSTANCE.convert(createReqVO)
                .setResult(BpmProcessInstanceResultEnum.PROCESS.getResult());
        ontractMapper.insert(ontract);
        // 发起 BPM 流程
        BpmProcessInstanceCreateReqDTO bpmProcessInstanceCreateReqDTO = new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                .setVariables(new HashMap<String,Object>(){{
                                  put("userId",createReqVO.getUserId());
                                  put("contractStartTime",parseToZoneDateTimeString(createReqVO.getStartTime()));
                                  put("contractEndTime",parseToZoneDateTimeString(createReqVO.getEndTime()));
                                  put("contractSignedTime",parseToZoneDateTimeString(createReqVO.getSignedTime()));
                              }}
                )
                .setBusinessKey(String.valueOf(ontract.getId()));
        String processInstanceId = processInstanceApi.createProcessInstance(getLoginUserId(),bpmProcessInstanceCreateReqDTO);

        // 将工作流的编号，更新到 表单中
        ontractMapper.updateById(new ContractDO().setId(ontract.getId()).setProcessInstanceId(processInstanceId));

        // 返回
        return ontract.getId();
    }
    private String parseToZoneDateTimeString(LocalDateTime time){
        if(time == null){
            return null;
        }
       return time.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateontract(ContractUpdateReqVO updateReqVO) {
        // 校验表单存在
        validateontractExists(updateReqVO.getId());
        // 获得实例
        String processInstanceId = ontractMapper.selectById(updateReqVO.getId()).getProcessInstanceId();
        // 删除还在审核的流程
        try {
            processInstanceApi.cancelProcessInstance(processInstanceId,"已被修改");
        }catch (Exception ignored){

        }
        // 发起 BPM 流程
        processInstanceId = processInstanceApi.createProcessInstance(getLoginUserId(),
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                        .setVariables(new HashMap(){{
                            put("userId",updateReqVO.getUserId());
                            put("contractStartTime",parseToZoneDateTimeString(updateReqVO.getStartTime()));
                            put("contractEndTime",parseToZoneDateTimeString(updateReqVO.getEndTime()));
                            put("contractSignedTime",parseToZoneDateTimeString(updateReqVO.getSignedTime()));
                                      }}
                        )
                        .setBusinessKey(String.valueOf(updateReqVO.getId())));
        // 更新
        // 更新标新数据
        // 更新最新工作流编号
        // 更新审核状态
        ContractDO updateObj = ContractConvert.INSTANCE.convert(updateReqVO)
                .setProcessInstanceId(processInstanceId)
                .setResult(BpmProcessInstanceResultEnum.PROCESS.getResult());
        ontractMapper.updateById(updateObj);
    }

    @Override
    public void updateContractStatus(Long id, Integer status) {
        validateontractExists(id);
        ontractMapper.updateById(new ContractDO().setId(id).setStatus(status));
    }

    @Override
    public void updateontractResult(Long id, Integer result) {
        validateontractExists(id);
        ontractMapper.updateById(new ContractDO().setId(id).setResult(result));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteontract(Long id) {
        // 校验存在
        validateontractExists(id);
        // 获得实例
        ContractDO contractDo = ontractMapper.selectById(id);
        // 对于可删除的流程
        if(Objects.equals(ContractStatusConstant.UNSIGNED, contractDo.getStatus())
        || Objects.equals(ContractStatusConstant.EXPIRE, contractDo.getStatus())
        || Objects.equals(ContractStatusConstant.END, contractDo.getStatus())){
            // 删除还在审核的流程
            try {
                processInstanceApi.cancelProcessInstance(contractDo.getProcessInstanceId(),"已被删除");
            }catch (Exception ignored){
            }
            // 删除表单
            ontractMapper.deleteById(id);
            return true;
        }
        // 对于已经实施的流程，使用启动删除流程
        // 发起 BPM 流程
        processInstanceApi.createProcessInstance(getLoginUserId(),
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_DEL_KEY)
                        .setBusinessKey(String.valueOf(contractDo.getId())));
        return "删除的流程已经提交！";
    }

    @Override
    public void deleteContractAbs(Long id) {
        // 校验存在
        validateontractExists(id);
        // 删除表单
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

    @Override
    public boolean checkIsStart(Long id) {
        return ontractMapper.selectOne(new LambdaQueryWrapper<ContractDO>()
                .eq(ContractDO::getId, id)
                .eq(ContractDO::getStatus, ContractStatusConstant.SIGNED)
                .eq(ContractDO::getResult, BpmProcessInstanceResultEnum.APPROVE.getResult())
                .lt(true, ContractDO::getStartTime, LocalDateTime.now())
        ) != null;
    }

    @Override
    public void sign(Long id) {
        ontractMapper.updateById(new ContractDO().setId(id).setSignedTime(LocalDateTime.now()).setStatus(ContractStatus.SIGNED.getCode()));
    }

}
