package com.bestinfo.sync.quartz;

import com.bestinfo.sync.quartz.job.WstFileJob;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class WstFileQuartz {

    private final Logger logger = Logger.getLogger(WstFileQuartz.class);

    public void masterThread() {
        logger.warn("******************* wst file quartz start *******************");
        WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();
        WstFileJob job = (WstFileJob) wac.getBean("wstFileJob");
        job.masterThread();
        logger.warn("*******************wst file quartz complete *******************");
    }
}
