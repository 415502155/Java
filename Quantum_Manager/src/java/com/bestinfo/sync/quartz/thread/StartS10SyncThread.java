package com.bestinfo.sync.quartz.thread;

import com.bestinfo.sync.quartz.job.S10SyncJob;
import org.springframework.web.context.WebApplicationContext;

public class StartS10SyncThread extends Thread {

    private WebApplicationContext wac;

    public StartS10SyncThread(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Override
    public void run() {
        //运行S10游戏同步任务
        S10SyncJob s10Job = (S10SyncJob) wac.getBean("s10SyncJob");
        s10Job.masterThread();
    }
}
