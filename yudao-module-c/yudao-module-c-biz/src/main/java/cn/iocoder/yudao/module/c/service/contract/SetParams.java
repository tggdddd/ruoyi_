package cn.iocoder.yudao.module.c.service.contract;

import lombok.Setter;
import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName SetParams
 * @Description
 * @Author 15014
 * @Time 2023/2/26 18:18
 * @Version 1.0
 */
@Component
public class SetParams implements ExecutionListener {
    @Setter
    private Expression remindTime;
    @Setter
    private Expression cancelTime;
    @Resource
    RuntimeService runtimeService;

    @Override
    public void notify(DelegateExecution execution) {
        runtimeService.setVariable(execution.getSuperExecutionId(),"remindTime",remindTime.getValue(execution));
        runtimeService.setVariable(execution.getSuperExecutionId(),"cancelTime",cancelTime.getValue(execution));

    }
}
