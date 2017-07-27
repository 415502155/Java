package com.bestinfo.quartz;

import com.bestinfo.quartz.job.MinuteSaleJob;
import java.io.IOException;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class MinuteSaleQuartz {

//    private static final Logger logger = Logger.getLogger(MinuteSaleQuartz.class);

    public void masterThread() throws IOException {
//            logger.warn("******************* AddMinuteSale quartz start *******************");
            WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();
            MinuteSaleJob job = (MinuteSaleJob) wac.getBean("minuteSaleJob");
            job.masterThread();
//            logger.warn("******************* AddMinuteSale quartz complete *******************");
    }
}
