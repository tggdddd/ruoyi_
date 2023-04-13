package cn.iocoder.yudao.framework.quartz.core.handler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @ClassName JobHandlerInterface
 * @Description
 * @Author 15014
 * @Time 2023/3/3 12:55
 * @Version 1.0
 */
public interface JobHandlerInterface {

    String execute(JobExecutionContext executionContext);
}
