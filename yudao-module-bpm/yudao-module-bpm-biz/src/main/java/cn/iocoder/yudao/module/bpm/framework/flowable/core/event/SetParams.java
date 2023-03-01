package cn.iocoder.yudao.module.bpm.framework.flowable.core.event;

import lombok.Setter;
import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;


public class SetParams implements ExecutionListener {
    @Setter
    private Expression remindTime;
    @Setter
    private Expression cancelTime;
    @Override
    public void notify(DelegateExecution execution) {
        execution.setVariable("remindTime",remindTime.getValue(execution));
        execution.setVariable("cancelTime",cancelTime.getValue(execution));
    }
}
