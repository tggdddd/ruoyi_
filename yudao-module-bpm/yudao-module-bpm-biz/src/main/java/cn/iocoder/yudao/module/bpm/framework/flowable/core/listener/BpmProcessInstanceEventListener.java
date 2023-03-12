package cn.iocoder.yudao.module.bpm.framework.flowable.core.listener;

import cn.iocoder.yudao.module.bpm.dal.dataobject.task.BpmProcessInstanceExtDO;
import cn.iocoder.yudao.module.bpm.framework.flowable.core.BpmFormReduce;
import cn.iocoder.yudao.module.bpm.service.definition.BpmProcessDefinitionService;
import cn.iocoder.yudao.module.bpm.service.task.BpmProcessInstanceService;
import com.google.common.collect.ImmutableSet;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEntityEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.engine.delegate.event.AbstractFlowableEngineEventListener;
import org.flowable.engine.delegate.event.FlowableCancelledEvent;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 监听 {@link ProcessInstance} 的开始与完成，创建与更新对应的 {@link BpmProcessInstanceExtDO} 记录
 *
 * @author jason
 */
@Component
public class BpmProcessInstanceEventListener extends AbstractFlowableEngineEventListener {

    @Resource
    @Lazy
    private BpmProcessInstanceService processInstanceService;
    @Resource
    @Lazy
    private BpmProcessDefinitionService processDefinitionService;

    public static final Set<FlowableEngineEventType> PROCESS_INSTANCE_EVENTS = ImmutableSet.<FlowableEngineEventType>builder()
                     .add(FlowableEngineEventType.PROCESS_CREATED)
                     .add(FlowableEngineEventType.PROCESS_CANCELLED)
                     .add(FlowableEngineEventType.PROCESS_COMPLETED)
                     .build();

    public BpmProcessInstanceEventListener(){
        super(PROCESS_INSTANCE_EVENTS);
    }
    @Resource
    private ApplicationContext applicationContext;

    @Override
    protected void processCreated(FlowableEngineEntityEvent event) {
        Deployment deployment = processDefinitionService.getDeployment(
                processDefinitionService.getProcessDefinition(event.getProcessDefinitionId()).getDeploymentId()
        );
        // 进行额外操作
        try {
            BpmFormReduce bpmFormReduce = applicationContext.getBean(deployment.getCategory(), BpmFormReduce.class);
            bpmFormReduce.create(event.getProcessInstanceId());
        } catch (BeansException e) {
            // 非必须
        }
        processInstanceService.createProcessInstanceExt((ProcessInstance)event.getEntity());
    }

    @Override
    protected void processCancelled(FlowableCancelledEvent event) {
        Deployment deployment = processDefinitionService.getDeployment(
                processDefinitionService.getProcessDefinition(event.getProcessDefinitionId()).getDeploymentId()
        );// 进行额外操作
        try {
            BpmFormReduce bpmFormReduce = applicationContext.getBean(deployment.getCategory(), BpmFormReduce.class);
            bpmFormReduce.cancel(event.getProcessInstanceId());
        } catch (BeansException e) {
            // 非必须
        }
        processInstanceService.updateProcessInstanceExtCancel(event);
    }

    @Override
    protected void processCompleted(FlowableEngineEntityEvent event) {
        Deployment deployment = processDefinitionService.getDeployment(
                processDefinitionService.getProcessDefinition(event.getProcessDefinitionId()).getDeploymentId()
        );// 进行额外操作
        try {
            BpmFormReduce bpmFormReduce = applicationContext.getBean(deployment.getCategory(), BpmFormReduce.class);
            bpmFormReduce.completion(event.getProcessInstanceId());
        } catch (BeansException e) {
            // 非必须
        }
        processInstanceService.updateProcessInstanceExtComplete((ProcessInstance)event.getEntity());
    }
}
