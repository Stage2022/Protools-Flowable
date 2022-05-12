package com.protools.flowablePOC.services;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class WorkflowService {
    private Logger logger = LoggerFactory.getLogger(WorkflowService.class);
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Transactional
    public String startProcess(String ProcessKey){

        runtimeService.startProcessInstanceByKey(ProcessKey);
        List<ProcessInstance> liste = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey(ProcessKey)
                .list();
        logger.info("Process Instance ID : " + liste.get(0).getId());

        return("Process Instance ID : " + liste.get(0).getId());


    }


    @Transactional
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    @Transactional
    public void claimTasks(String processID, String assignee){
        List<Task> taskInstances = taskService.createTaskQuery().processInstanceId(processID).taskAssignee(assignee).active().list();
        if (taskInstances.size() > 0) {
            for (Task t : taskInstances) {
                taskService.addCandidateGroup(t.getId(), "userTeam");
                logger.info("> Claiming task: " + t.getId());
                taskService.claim(t.getId(),assignee);
            }
        } else {
            logger.info("\t \t >> There are no task for me to work on.");
        }
    }

    @Transactional
    public void completeTask(String processID, HashMap<String,Object> variables, String assignee){
        List<Task> taskInstances = taskService.createTaskQuery().processInstanceId(processID).taskAssignee(assignee).active().list();
        logger.info("> Completing task from process : " + processID);
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
