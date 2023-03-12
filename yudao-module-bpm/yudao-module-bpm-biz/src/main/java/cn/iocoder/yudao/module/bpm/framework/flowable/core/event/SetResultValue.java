package cn.iocoder.yudao.module.bpm.framework.flowable.core.event;

import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * @ClassName SetResultValue
 * @Description
 * @Author 15014
 * @Time 2023/3/1 11:36
 * @Version 1.0
 */
public class SetResultValue implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setVariable("isCompleted",true);
    }
}
