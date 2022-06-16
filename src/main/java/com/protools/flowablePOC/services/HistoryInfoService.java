package com.protools.flowablePOC.services;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class HistoryInfoService {
    private Logger logger = LoggerFactory.getLogger(HistoryInfoService.class);
    @Autowired
    private HistoryService historyService;

    @Transactional
    public List<HistoricProcessInstance> getHistoryProcess(){
        List<HistoricProcessInstance> response = historyService.createHistoricProcessInstanceQuery()
                .finished().listPage(0,10);

        return (response);
    };

    @Transactional
    public List<HistoricTaskInstance> getHistoryTask(String processInstanceID){
        List<HistoricTaskInstance> response = historyService.createHistoricTaskInstanceQuery()
                .finished().processInstanceId(processInstanceID).listPage(0,10);

        return (response);
    };

    @Transactional
    public List<HistoricActivityInstance> getHistoryActivity(){
        List<HistoricActivityInstance> response = historyService.createHistoricActivityInstanceQuery().listPage(0,10);
        return response;
    }
}
