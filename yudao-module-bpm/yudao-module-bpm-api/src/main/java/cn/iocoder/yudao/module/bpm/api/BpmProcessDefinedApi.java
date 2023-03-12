package cn.iocoder.yudao.module.bpm.api;

import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;

/**
 * @ClassName BpmProcessDefinedApi
 * @Description 流程定义接口
 * @Author 肖润杰
 * @Time 2023/3/10 10:00
 * @Version 1.0
 */
public interface BpmProcessDefinedApi {
    /**
     * 根据key获得流程定义
     *
     * @param processDefinedKey 流程定义key
     * @return 流程定义
     */
    ProcessDefinition getActiveProcessDefined(String processDefinedKey);
    ProcessDefinition getProcessDefined(String processDefinedKey);
}
