package com.bestinfo.sync.quartz;


import com.bestinfo.sync.quartz.job.TerminalkeyJob;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class TerminalkeyQuartz {

    private static final Logger logger = Logger.getLogger(TerminalkeyQuartz.class);

    public void masterThread() throws IOException {
            logger.warn("******************* terminalkey quartz start *******************");
            WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();
            TerminalkeyJob job = (TerminalkeyJob) wac.getBean("terminalkeyJob");
            job.masterThread();
            logger.warn("******************* terminalkey quartz complete *******************");
    }
}
