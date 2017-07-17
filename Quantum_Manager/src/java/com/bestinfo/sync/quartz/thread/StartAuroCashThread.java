package com.bestinfo.sync.quartz.thread;

import com.bestinfo.sync.quartz.job.AutoCashJob;
import org.springframework.web.context.WebApplicationContext;

public class StartAuroCashThread extends Thread {

    private WebApplicationContext wac;

    public StartAuroCashThread(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Override
    public void run() {
        //运行电话投注自动兑奖任务
        AutoCashJob job = (AutoCashJob) wac.getBean("autoCashJob");
        job.masterThread();
    }
}
