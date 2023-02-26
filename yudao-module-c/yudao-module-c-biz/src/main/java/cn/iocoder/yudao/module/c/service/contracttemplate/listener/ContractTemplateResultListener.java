package cn.iocoder.yudao.module.c.service.contracttemplate.listener;

import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEventListener;
import cn.iocoder.yudao.module.bpm.service.oa.BpmOALeaveServiceImpl;
import cn.iocoder.yudao.module.c.service.contracttemplate.ContractTemplateService;
import cn.iocoder.yudao.module.c.service.contracttemplate.ContractTemplateServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * OA 请假单的结果的监听器实现类
 *
 * @author 芋道源码
 */
@Component
public class ContractTemplateResultListener extends BpmProcessInstanceResultEventListener {

    @Resource
    private ContractTemplateService contractTemplateService;

    @Override
    protected String getProcessDefinitionKey() {
        return ContractTemplateServiceImpl.PROCESS_KEY;
    }

    @Override
    protected void onEvent(BpmProcessInstanceResultEvent event) {
        contractTemplateService.updateontractResult(Long.parseLong(event.getBusinessKey()), event.getResult());
    }

}
