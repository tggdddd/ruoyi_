package cn.iocoder.yudao.module.bpm.framework.flowable.core.event;

import lombok.Setter;
import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
/**
 * @ClassName CancelContract
 * @Description
 * @Author 15014
 * @Time 2023/2/27 9:58
 * @Version 1.0
 */
public class CancelProcess  implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        if(execution.getVariable("isCompleted") != null){
            return;
        }
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        if (runtimeService.createActivityInstanceQuery().processInstanceId(execution.getRootProcessInstanceId()).activityName("用户签约").finished().singleResult() == null) {
            runtimeService.deleteProcessInstance(execution.getRootProcessInstanceId(), "用户未在规定时间内签约");
        }
        // TODO: 2023/3/13 发送合同取消的通知
    }
}
