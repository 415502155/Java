package com.bestinfo.sync.quartz;


import com.bestinfo.sync.quartz.job.TerminalStatusJob;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class TerminalStatusQuartz {

    private static final Logger logger = Logger.getLogger(TerminalStatusQuartz.class);

    public void masterThread() throws IOException, FileNotFoundException, ParseException {
            logger.warn("******************* TerminalStatusJob quartz start *******************");
            WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();
            TerminalStatusJob job = (TerminalStatusJob) wac.getBean("terminalStatusJob");
            job.masterThread();
            logger.warn("******************* TerminalStatusJob quartz complete *******************");
    }
}
