package com.bestinfo.sync.quartz.thread;

import com.bestinfo.sync.quartz.job.K80SyncJob;
import org.springframework.web.context.WebApplicationContext;

public class StartK80SyncThread extends Thread {

    private WebApplicationContext wac;

    public StartK80SyncThread(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Override
    public void run() {
        //运行K80游戏同步任务
        K80SyncJob job = (K80SyncJob) wac.getBean("k80SyncJob");
        job.masterThread();
    }
}
