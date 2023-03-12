package cn.iocoder.yudao.module.bpm.api;

import cn.iocoder.yudao.module.bpm.service.definition.BpmProcessDefinitionService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName BpmProcessDefinedApiImpl
 * @Description
 * @Author 肖润杰
 * @Time 2023/3/10 10:02
 * @Version 1.0
 */
@Component
public class BpmProcessDefinedApiImpl implements BpmProcessDefinedApi{
    @Resource
    private BpmProcessDefinitionService bpmProcessDefinitionService;

    public ProcessDefinition getActiveProcessDefined(String processDefinedKey){
        return bpmProcessDefinitionService.getActiveProcessDefinition(processDefinedKey);
    }

    @Override
    public ProcessDefinition getProcessDefined(String processDefinedKey) {
        return bpmProcessDefinitionService.getProcessDefinition2(processDefinedKey);
    }
}
