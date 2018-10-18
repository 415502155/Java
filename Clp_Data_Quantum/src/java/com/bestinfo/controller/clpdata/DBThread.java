/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.controller.clpdata;

//线程池中工作的线程

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")//spring 多例
public class DBThread implements Runnable {
    private String msg;
    //private Logger log = LoggerFactory.getLogger(DBThread.class);

//    @Autowired
//    SystemLogService systemLogService;



    @Override
    public void run() {
        //模拟在数据库插入数据
        Systemlog systemlog = new Systemlog();
        systemlog.setTime(new Date());
        systemlog.setLogdescribe(msg);
        //systemLogService.insert(systemlog);
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
