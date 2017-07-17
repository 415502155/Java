package com.bestinfo.sync.quartz.thread;

import com.bestinfo.sync.quartz.job.K3SyncJob;
import org.springframework.web.context.WebApplicationContext;

public class StartK3SyncThread extends Thread {

    private WebApplicationContext wac;

    public StartK3SyncThread(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Override
    public void run() {
        //运行K3游戏同步任务
        K3SyncJob k3Job = (K3SyncJob) wac.getBean("k3SyncJob");
        k3Job.masterThread();
    }
}
