package com.bestinfo.sync.quartz;

import com.bestinfo.sync.quartz.job.K3SyncJob;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class K3SyncQuartz {

    private final Logger logger = Logger.getLogger(K3SyncQuartz.class);

    public void masterThread() {
        logger.warn("******************* K3 sync quartz start *******************");
        WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();
        K3SyncJob k3Job = (K3SyncJob) wac.getBean("k3SyncJob");
        k3Job.masterThread();
        logger.warn("******************* K3 sync quartz complete *******************");
    }
}
