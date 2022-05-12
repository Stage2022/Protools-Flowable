package com.protools.flowablePOC.controllers;

import com.protools.flowablePOC.beans.TaskRepresentation;
import com.protools.flowablePOC.services.WorkflowService;
import io.swagger.v3.oas.annotations.Operation;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ProcessController {
    private Logger logger = LoggerFactory.getLogger(ProcessController.class);
    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @PostMapping(value= "/start-process/{processKey}")
    public String startProcessInstance(@PathVariable String processKey){
        logger.info("> POST request to start the process: "+ processKey);
        return (workflowService.startProcess(processKey));
    }
    @Operation(summary = "Get all task by assignee")
    @GetMapping(value = "/tasks/{assignee}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getTasks(@PathVariable String assignee) {
        List<Task> tasks = workflowService.getTasks(assignee);
        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
        for (Task task : tasks) {
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        return dtos;
    }

    @Operation(summary = "Claim all task by processID")
    @PostMapping("/get-tasks/{assignee}/{processID}")
    public void getTasks(@PathVariable String processID, @PathVariable String assignee) {
        logger.info(">>> Claim assigned tasks <<<");
        workflowService.claimTasks(processID,assignee);

    }

    @Operation(summary = "Complete claimed task by processKey, add variables to process")
    @GetMapping("/complete-task/{assignee}/{processID}")
    public void completeTaskA(@PathVariable String processID, @RequestBody HashMap<String,Object> variables, @PathVariable String assignee) {
        logger.info(">>> Complete assigned task for assignee + "+ assignee +" <<<");
        workflowService.completeTask(processID,variables,assignee);
    }


}
