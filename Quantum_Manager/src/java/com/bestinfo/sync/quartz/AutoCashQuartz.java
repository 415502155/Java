package com.bestinfo.sync.quartz;

import com.bestinfo.sync.quartz.job.AutoCashJob;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class AutoCashQuartz {

    private static final Logger logger = Logger.getLogger(AutoCashQuartz.class);

    public void masterThread() {
        logger.warn("******************* gambler auto cash quartz start *******************");
        WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();
        AutoCashJob job = (AutoCashJob) wac.getBean("autoCashJob");
        job.masterThread();
        logger.warn("******************* gambler auto cash quartz complete *******************");
    }
}
