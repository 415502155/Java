package com.java.collection.schedule;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.expression.Dates;

@Component
public class Jobs {

    public final static long ONE_Minute =  60 * 1000;
    
    @Scheduled(fixedDelay=ONE_Minute)
    public void fixedDelayJob(){
        System.out.println("fixedDelayJob >>fixedDelay执行...."+new Date());
    }
    
    @Scheduled(fixedRate=ONE_Minute)
    public void fixedRateJob(){
        System.out.println("fixedRateJob >>fixedRate执行...."+new Date());
    }

    @Scheduled(cron="0 15 3 * * ?")
    public void cronJob(){
        System.out.println("cronJob >>cron执行...."+new Date());
    }

}
