package com.bestinfo.sync.quartz;

import com.bestinfo.sync.quartz.job.K80SyncJob;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class K80SyncQuartz {

    private final Logger logger = Logger.getLogger(K80SyncQuartz.class);

    public void masterThread() {
        logger.warn("******************* K80 sync quartz start *******************");
        WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();
        K80SyncJob job = (K80SyncJob) wac.getBean("k80SyncJob");
        job.masterThread();
        logger.warn("******************* K80 sync quartz complete *******************");
    }
}
