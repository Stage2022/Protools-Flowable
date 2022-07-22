package com.protools.flowablePOC.services;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class HistoryInfoService {
    private Logger logger =LogManager.getLogger(HistoryInfoService.class);
    @Autowired
    private HistoryService historyService;

    @Transactional
    public List<HistoricProcessInstance> getHistoryProcess(){
        List<HistoricProcessInstance> response = historyService.createHistoricProcessInstanceQuery()
                .finished().orderByProcessInstanceStartTime().desc().listPage(0,100);

        return (response);
    };

    @Transactional
    public List<HistoricTaskInstance> getHistoryTask(String processDefinitionID){
        List<HistoricTaskInstance> response = historyService.createHistoricTaskInstanceQuery()
                .processDefinitionId(processDefinitionID).listPage(0,100);

        return (response);
    };

    @Transactional
    public List<HistoricActivityInstance> getHistoryActivity(){
        List<HistoricActivityInstance> response = historyService.createHistoricActivityInstanceQuery().orderByHistoricActivityInstanceStartTime().desc().listPage(0,100);
        return response;
    }
}
