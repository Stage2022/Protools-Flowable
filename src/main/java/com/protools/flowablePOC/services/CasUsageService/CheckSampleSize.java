package com.protools.flowablePOC.services.CasUsageService;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class CheckSampleSize implements JavaDelegate {
    private Logger logger = LoggerFactory.getLogger(CheckSampleSize.class);
    @Override
    public void execute(DelegateExecution delegateExecution) {
        String sampleSize = (String) delegateExecution.getVariable("sampleSize");
        int sampleSizeInt = Integer.parseInt(sampleSize);
        if (sampleSizeInt<5){
            delegateExecution.setVariable("surveyValid", false);
            logger.info("\t >> ERROR : Sample Size too small");
            logger.info("\t >> Process will be aborted");
        } else {
            delegateExecution.setVariable("surveyValid", true);
        }
    }
}
