/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.controller.clpdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Administrator
 */
@Controller
public class ThreadPoolController {
     @Autowired
    ThreadPoolManager  tpm;

    @RequestMapping("/pool")
    @ResponseBody
    public Object test() {
        for (int i = 0; i < 500; i++) {
            //模拟并发500条记录
            tpm.processOrders(Integer.toString(i));
        }

        return "ok";
    }
}
