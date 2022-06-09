package com.protools.flowablePOC.deployment;

import org.flowable.app.api.AppRepositoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.test.PluggableFlowableTestCase;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.test.ConfigurationResource;
import org.flowable.engine.test.Deployment;
import org.flowable.engine.test.FlowableTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.InputStream;
import java.util.List;
@FlowableTest
public class BPMNDeploymentTest extends PluggableFlowableTestCase {
    private RuntimeService runtimeService;
    private TaskService taskService;
    @BeforeEach
    void setUp(ProcessEngine processEngine) {
        // Setup de l'env de test
        this.runtimeService = processEngine.getRuntimeService();
        this.taskService = processEngine.getTaskService();
    }
    @Test
    @Deployment(resources = {"processes/casUsageTest.bpmn20.xml"})
    public void testDesTests(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("CasUtilisationPOC", "businessKey123");
        String businessKey = (String) runtimeService.getVariable(processInstance.getId(), "businessKeyInExecution");
        assertThat(businessKey).isEqualTo("businessKey123");

        org.flowable.task.api.Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        assertThat(task).isNotNull();
    }
}
