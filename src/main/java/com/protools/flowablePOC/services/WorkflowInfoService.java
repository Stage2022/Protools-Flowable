package com.protools.flowablePOC.services;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstanceQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Service
public class WorkflowInfoService {
    private Logger logger = LoggerFactory.getLogger(WorkflowInfoService.class);
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;
    @Transactional
    public Map<String, FlowElement> getBPMNModel(String processDefinitionId){
        BpmnModel response = repositoryService.getBpmnModel(processDefinitionId);
        return (response.getMainProcess().getFlowElementMap());
    };
    @Transactional
    public JSONObject getAllProcessInstance(){
        List<ProcessInstance> liste = runtimeService.createProcessInstanceQuery()
                .list();

        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i =0; i<liste.size(); i++) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("id", liste.get(i).getId());
            jsonResponse.put("name", liste.get(i).getName());
            jsonResponse.put("processKey", liste.get(i).getProcessDefinitionKey());
            jsonResponse.put("activity", liste.get(i).getActivityId());
            jsonResponse.put("startTime",liste.get(i).getStartTime());
            jsonResponse.put("tenant",liste.get(i).getTenantId());
            jsonResponse.put("ProcessDefinitionId",liste.get(i).getProcessDefinitionId());
            jsonArray.put(jsonResponse);

        }
        responseDetailsJson.put("processes", jsonArray);
        return responseDetailsJson;
    }
    @Transactional
    public JSONArray getTasks(String assignee) {
        List<Task> response = taskService.createTaskQuery().taskAssignee(assignee).list();
        JSONArray jsonArray = new JSONArray();
        for (int i =0; i<response.size(); i++) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("id", response.get(i).getId());
            jsonResponse.put("name", response.get(i).getName());
            jsonResponse.put("processInstance", response.get(i).getProcessInstanceId());
            jsonResponse.put("delegationState", response.get(i).getDelegationState());
            jsonResponse.put("parentTask",response.get(i).getParentTaskId());
            jsonResponse.put("createTime",response.get(i).getCreateTime());
            jsonArray.put(jsonResponse);

        }
        return jsonArray;
    }

    @Transactional
    public JSONArray getAllTasks() {
        List<Task> response = taskService.createTaskQuery().list();
        logger.info(response.toString());

        JSONArray jsonArray = new JSONArray();
        for (int i =0; i<response.size(); i++) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("id", response.get(i).getId());
            jsonResponse.put("name", response.get(i).getName());
            jsonResponse.put("processInstance", response.get(i).getProcessInstanceId());
            jsonResponse.put("delegationState", response.get(i).getDelegationState());
            jsonResponse.put("parentTask",response.get(i).getParentTaskId());
            jsonResponse.put("createTime",response.get(i).getCreateTime());
            jsonArray.put(jsonResponse);

        }
        return jsonArray;

    }
    @Transactional
    public HistoricActivityInstanceQuery getHistory(){
        HistoricActivityInstanceQuery response = historyService.createHistoricActivityInstanceQuery();
        return (response);
    };
}
