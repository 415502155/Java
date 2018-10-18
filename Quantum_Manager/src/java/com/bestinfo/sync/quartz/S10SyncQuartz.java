package com.bestinfo.sync.quartz;

import com.bestinfo.sync.quartz.job.S10SyncJob;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class S10SyncQuartz {

    private final Logger logger = Logger.getLogger(S10SyncQuartz.class);

    public void masterThread() {
        logger.warn("******************* S10 sync quartz start *******************");
        WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();
        S10SyncJob s10Job = (S10SyncJob) wac.getBean("s10SyncJob");
        s10Job.masterThread();
        logger.warn("******************* S10 sync quartz complete *******************");
    }
}
