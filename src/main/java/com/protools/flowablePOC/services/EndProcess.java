package com.protools.flowablePOC.services;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EndProcess implements JavaDelegate {
    private Logger logger = LoggerFactory.getLogger(EndProcess.class);
    @Override
    public void execute(DelegateExecution delegateExecution) {
        logger.info("\t >> Aborting Process ... <<  ");
        // Contenu à analyser
        String surveyName = (String) delegateExecution.getVariable("name");
        logger.info("\t \t >> Failed to create survey " + surveyName);
    }
}
