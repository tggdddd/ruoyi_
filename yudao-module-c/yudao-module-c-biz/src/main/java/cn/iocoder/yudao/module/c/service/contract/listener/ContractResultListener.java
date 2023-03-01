package cn.iocoder.yudao.module.c.service.contract.listener;

import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEventListener;
import cn.iocoder.yudao.module.bpm.service.oa.BpmOALeaveService;
import cn.iocoder.yudao.module.bpm.service.oa.BpmOALeaveServiceImpl;
import cn.iocoder.yudao.module.c.controller.admin.contract.vo.ContractUpdateReqVO;
import cn.iocoder.yudao.module.c.service.contract.ContractService;
import cn.iocoder.yudao.module.c.service.contract.ContractServiceImpl;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * OA 请假单的结果的监听器实现类
 *
 * @author 芋道源码
 */
@Component
public class ContractResultListener extends BpmProcessInstanceResultEventListener {

    @Resource
    @Lazy
    private ContractService contractService;

    @Override
    protected String getProcessDefinitionKey() {
        return ContractServiceImpl.PROCESS_KEY;
    }

    @Override
    protected void onEvent(BpmProcessInstanceResultEvent event) {
        contractService.updateontractResult(Long.parseLong(event.getBusinessKey()), event.getResult());
        if(event.getResult().equals(BpmProcessInstanceResultEnum.APPROVE.getResult())){
            contractService.sign(Long.valueOf(event.getBusinessKey()));
        }
    }

}
