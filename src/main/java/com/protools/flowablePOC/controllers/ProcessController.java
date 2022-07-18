package com.protools.flowablePOC.controllers;

import com.google.gson.Gson;
import com.protools.flowablePOC.beans.TaskRepresentation;
import com.protools.flowablePOC.services.WorkflowInfoService;
import com.protools.flowablePOC.services.WorkflowService;
import io.swagger.v3.oas.annotations.Operation;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class ProcessController {
    private Logger logger = LoggerFactory.getLogger(ProcessController.class);
    @Autowired
    private WorkflowService workflowService;

    @CrossOrigin
    @Operation(summary = "Start process by ProcessKey")
    @PostMapping(value= "/start-process/{processKey}/{businessKey}")
    public String startProcessInstance(@PathVariable String processKey, @PathVariable String businessKey){
        logger.info("> Start the process: "+ processKey);
        JSONObject object = workflowService.startProcess(processKey,businessKey);
        logger.info(String.valueOf(object));
        return (String.valueOf(object));
    }
    @CrossOrigin
    @Operation(summary = "Claim all task by TaskID")
    @PostMapping("/get-tasks/{assignee}/{TaskID}")
    public void getTasks(@PathVariable String TaskID, @PathVariable String assignee) {
        logger.info(">>> Claim assigned tasks for assignee "+ assignee+" <<<");
        workflowService.claimTasks(TaskID,assignee);

    }
    @CrossOrigin
    @Operation(summary = "Complete claimed task by taskID, add variables to process")
    @PostMapping("/complete-task/{assignee}/{taskID}")
    public void completeTaskA(@PathVariable String taskID, @RequestBody HashMap<String,Object> variables, @PathVariable String assignee) {
        logger.info(">>> Complete assigned task for assignee "+ assignee +" <<<");
        logger.info("TaskID : "+taskID);
        workflowService.completeTask(taskID,variables,assignee);
    }

    @CrossOrigin
    @Operation(summary = "Delete Process Instance By Process ID")
    @PostMapping("/deleteProcess/{ProcessID}")
    public void deleteProcess(@PathVariable String ProcessID) {
        logger.info(">>> Deleting ProcessInstance :" +ProcessID+" <<<");
        workflowService.deleteProcessInstance(ProcessID);
    }



}
