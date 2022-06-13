package com.protools.flowablePOC.controllers;

import com.protools.flowablePOC.services.WorkflowInfoService;
import io.swagger.v3.oas.annotations.Operation;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstanceQuery;
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
    @Operation(summary = "Get all processInstance")
    @GetMapping(value = "/processInstances", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllProcessInstance() {
        JSONObject liste = workflowInfoService.getAllProcessInstance();

        return (String.valueOf(liste));
       }
    @CrossOrigin
    @Operation(summary = "Get all task by assignee")
    @GetMapping(value = "/tasks/{assignee}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTasks(@PathVariable String assignee) {
        JSONArray tasks = workflowInfoService.getTasks(assignee);

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
    @Operation(summary = "Get History")
    @GetMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public HistoricActivityInstanceQuery getHistory(){
        return(workflowInfoService.getHistory());
    }
}
