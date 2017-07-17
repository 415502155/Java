package com.bestinfo.sync.quartz;


import com.bestinfo.sync.quartz.job.TerminalStatusJob2;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class TerminalStatusQuartz2 {

    private static final Logger logger = Logger.getLogger(TerminalStatusQuartz2.class);

    public void masterThread() throws IOException {
            logger.warn("******************* TerminalStatusJob2 quartz start *******************");
            WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();
            TerminalStatusJob2 job = (TerminalStatusJob2) wac.getBean("terminalStatusJob2");
            job.masterThread();
            logger.warn("******************* TerminalStatusJob2 quartz complete *******************");
    }
}
