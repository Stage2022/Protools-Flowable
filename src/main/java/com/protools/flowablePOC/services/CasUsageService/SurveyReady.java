package com.protools.flowablePOC.services.CasUsageService;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpClient;

@Component
public class SurveyReady implements JavaDelegate {

    private Logger logger = LoggerFactory.getLogger(SurveyReady.class);
    @Override
    public void execute(DelegateExecution delegateExecution) {
        HttpClient client = HttpClient.newHttpClient();
        String surveyID = (String) delegateExecution.getVariable("idSurvey") ;
        URL url = null;
        try {
            url = new URL("https://coleman.dev.insee.io/survey/"+ String.valueOf(surveyID)+ "/ready");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.getInputStream();
            logger.info("\t >> Survey set state to ready : " + con.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
