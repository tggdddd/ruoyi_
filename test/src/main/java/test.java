
import liquibase.pro.packaged.F;
import liquibase.pro.packaged.S;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.ProcessEngineImpl;
import org.flowable.engine.impl.bpmn.parser.factory.ListenerFactory;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.junit.Before;
import org.junit.Test;

import javax.xml.stream.events.ProcessingInstruction;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @ClassName test
 * @Description
 * @Author 15014
 * @Time 2023/2/27 11:45
 * @Version 1.0
 */
public class test {
    RuntimeService runtimeService;
    RepositoryService repositoryService;
    TaskService taskService;
    HistoryService historyService;
    ProcessEngine processEngine;
    @Before
    public void init() {
        processEngine = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://127.0.0.1:3306/ruoyi-vue-pro?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true")
                .setJdbcUsername("root")
                .setJdbcPassword("root")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                .buildProcessEngine();
        runtimeService = processEngine.getRuntimeService();
        repositoryService = processEngine.getRepositoryService();
        taskService = processEngine.getTaskService();
        historyService = processEngine.getHistoryService();
    }
    @Test
    public void getTest() {
        // ProcessInstance processInstances = runtimeService.createProcessInstanceQuery().active().orderByStartTime().desc().list().get(0);
        // String processDefinitionId = processInstances.getProcessDefinitionId();
        // ProcessInstance processInstance = processInstances;
        for (Model model : repositoryService.createModelQuery().list()) {
            System.out.println(model.getCategory());
        }

    }
    @Test
    public void watchProcessInstanceProcess(){
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().active().orderByStartTime().desc().list().get(0);
        if (processInstance != null) {
            ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());
            String processDefinitionName = processDefinition.getName();
            String currentActivityId = processInstance.getActivityId();

            List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstance.getProcessInstanceId())
                    .orderByHistoricActivityInstanceStartTime().asc()
                    .list();
            System.out.println("实例名："+processDefinitionName);
            System.out.println("currentActivityId = " + currentActivityId);
            for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
                String activityName = historicActivityInstance.getActivityName();
                String deleteReason = historicActivityInstance.getDeleteReason();
                Date startTime = historicActivityInstance.getStartTime();
                Date endTime = historicActivityInstance.getEndTime();
                Long durationInMillis = historicActivityInstance.getDurationInMillis();
                System.out.println("------------");;
                System.out.println("activityName = " + activityName+" "+historicActivityInstance.getActivityId());
                System.out.println("startTime = " + startTime);
                System.out.println("endTime = " + endTime);
                System.out.println("durationInMillis = " + durationInMillis);
                System.out.println("deleteReason = " + deleteReason);
            }

            System.out.println("======================");

        }else {
            System.out.println("实例不存在");
        }

    }

}