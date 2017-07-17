package com.bestinfo.sync.quartz.thread;

import com.bestinfo.sync.quartz.job.WstFileJob;
import org.springframework.web.context.WebApplicationContext;

public class StartWstFileThread extends Thread {

    private WebApplicationContext wac;

    public StartWstFileThread(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Override
    public void run() {
        WstFileJob job = (WstFileJob) wac.getBean("wstFileJob");
        job.masterThread();
    }
}
