package com.protools.flowablePOC.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class HistoryInfoService {
    @Autowired
    private HistoryInfoService historyInfoService;

    @CrossOrigin
    @Operation(summary = "Get Process History")
    @GetMapping(value = "/history/process", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HistoricProcessInstance> getHistoryProcess(){
        return(historyInfoService.getHistoryProcess());
    }

    @CrossOrigin
    @Operation(summary = "Get Tasks History")
    @GetMapping(value = "/history/task", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HistoricTaskInstance> getHistoryTask(){
        return(historyInfoService.getHistoryTask());
    }

    @CrossOrigin
    @Operation(summary = "Get Activity History")
    @GetMapping(value = "/history/activity", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HistoricActivityInstance> getHistoryActivity(){
        return(historyInfoService.getHistoryActivity());
    }

}
