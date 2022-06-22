package com.protools.flowablePOC.services;


import org.flowable.engine.*;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkflowService {
    private Logger logger = LoggerFactory.getLogger(WorkflowService.class);
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    // Process execution
    @Transactional
    public JSONObject startProcess(String ProcessKey){

        runtimeService.startProcessInstanceByKey(ProcessKey);
        List<ProcessInstance> liste = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey(ProcessKey)
                .list();
        logger.info("Process Instance ID : " + liste.get(0).getId());

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("id", liste.get(0).getId());
        jsonResponse.put("name",liste.get(0).getName());
        jsonResponse.put("processKey", ProcessKey);
        jsonResponse.put("startTime", liste.get(0).getStartTime());
        return(jsonResponse);

    }

    @Transactional
    public void claimTasks(String taskID, String assignee){
        List<Task> taskInstances = taskService.createTaskQuery().taskId(taskID).taskAssignee(assignee).active().list();
        if (taskInstances.size() > 0) {
            for (Task t : taskInstances) {
                taskService.addCandidateGroup(t.getId(), "userTeam");
                logger.info("> Claiming task: " + t.getId());
                taskService.claim(t.getId(),assignee);
            }
        } else {
            logger.info("\t \t >> No task found.");
        }
    }

    @Transactional
    public void completeTask(String taskID, HashMap<String,Object> variables, String assignee){
        List<Task> taskInstances = taskService.createTaskQuery().taskId(taskID).taskAssignee(assignee).active().list();
        logger.info("> Completing task from process : " + taskID);
        logger.info("\t > Variables : " + variables.toString());
        if (taskInstances.size() > 0) {
            for (Task t : taskInstances) {
                taskService.addCandidateGroup(t.getId(), "userTeam");
                logger.info("> Completing task: " + t.getId());
                taskService.complete(t.getId(),variables);
            }
        } else {
            logger.info("\t \t >> There are no task for me to complete");
        }
    }
}
