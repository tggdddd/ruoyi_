package cn.iocoder.yudao.module.bpm.framework.flowable.core.event;

import lombok.Setter;
import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.joda.time.DateTime;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @ClassName SendNotify
 * @Description
 * @Author 15014
 * @Time 2023/2/27 9:58
 * @Version 1.0
 */
public class SendNotify  implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) {
        System.out.println(execution.getEventName()+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }
}
