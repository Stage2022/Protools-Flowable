package com.protools.flowablePOC.controllers;

import com.protools.flowablePOC.services.WorkflowInfoService;
import io.swagger.v3.oas.annotations.Operation;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ProcessInfoController {
    private Logger logger = LoggerFactory.getLogger(ProcessInfoController.class);
    @Autowired
    private WorkflowInfoService workflowInfoService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;
    @CrossOrigin
    @Operation(summary = "Get BPMN info by process")
    @GetMapping(value = "/bpmnInfo/{processID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, FlowElement> getBPMNInfo(@PathVariable String processID){
        return(workflowInfoService.getBPMNModel(processID));
    }

    @CrossOrigin
    @Operation(summary = "Get BPMN documentation by process")
    @GetMapping(value = "/documentation/{processID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBPMNModelDocumentation(@PathVariable String processID){
        return(workflowInfoService.getBPMNModelDocumentation(processID));
    }

    @CrossOrigin
    @Operation(summary = "Get ProcessDefinitionID (temporary endpoint)")
    @GetMapping(value = "/processDefinition/{processID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProcessDefinitionID(@PathVariable String processID){
        return(workflowInfoService.getProcessDefinitionID(processID));
    }
    @CrossOrigin
    @Operation(summary = "Get all processInstance")
    @GetMapping(value = "/processInstances", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllProcessInstance() {
        JSONObject liste = workflowInfoService.getAllProcessInstance();

        return (String.valueOf(liste));
    }
    @CrossOrigin
    @Operation(summary = "Get all task by assignee")
    @GetMapping(value = "/tasksAssignee/{assignee}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTasks(@PathVariable String assignee) {
        JSONArray tasks = workflowInfoService.getTasks(assignee);

        return String.valueOf(tasks);
    }

    @CrossOrigin
    @Operation(summary = "Get all task by Process ID")
    @GetMapping(value = "/tasksProcessID/{processID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTasksProcess(@PathVariable String processID) {
        JSONArray tasks = workflowInfoService.getTasksProcess(processID);

        return String.valueOf(tasks);
    }
    @CrossOrigin
    @Operation(summary = "Get all tasks")
    @GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllTasks() {
        JSONArray tasks = workflowInfoService.getAllTasks();

        return String.valueOf(tasks);
    }


    @CrossOrigin
    @Operation(summary = "Get error info by process Instance ID")
    @GetMapping(value = "/errors/{processInstanceId}")
    public String getErrors(@PathVariable String processInstanceId){
        JSONArray result = workflowInfoService.getJobs(processInstanceId);
                return String.valueOf(result);
    }

    @CrossOrigin
    @Operation(summary = "Get Process Variables by Process Instance ID")
    @GetMapping(value = "/variables/{processInstanceId}")
    public String getVariables(@PathVariable String processInstanceId){
        Map<String,Object> result = workflowInfoService.getProcessVariables(processInstanceId);
        JSONObject json = new JSONObject(result);
        return String.valueOf(json);
    }

    @CrossOrigin
    @Operation(summary = "Get activity ID (BPMN file) from process Instance ID")
    @GetMapping(value = "executionActivities/{processInstanceId}")
    public List<String> getActivityIdExecution(@PathVariable String processInstanceId){
        List<String> result = workflowInfoService.getActivityExecution(processInstanceId);
        return result;
    }
}
