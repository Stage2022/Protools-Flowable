package com.protools.flowablePOC.controllers;

import com.protools.flowablePOC.services.WorkflowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessController {
    private Logger logger = LoggerFactory.getLogger(ProcessController.class);
    @Autowired
    private WorkflowService workflowService;

    @PostMapping(value= "/start-process/{processKey}")
    public void startProcessInstance(@PathVariable String processKey){
        logger.info("> POST request to start the process: "+ processKey);
        workflowService.startProcess(processKey);
    }
}
