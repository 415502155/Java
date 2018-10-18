/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.controller.clpdata;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Administrator
 */
public class Digital {
     private volatile int i =0;  
 private volatile long total =0;  
 private boolean completed = false;
 final Lock l = new ReentrantLock();
 
 public void increase(){ 
  synchronized(this){
   System.out.println(Thread.currentThread().getName()+" is increasing");  //辅助观看结果的 ，可以注释掉
   if(++i <= 1000)
     total += i;
    else{
     this.completed = true;
     this.notifyAll();       //唤醒主线程
    } 
   }
 }

 public long getTotal(){
  return this.total;
 }
 
 public boolean isCompleted(){
  
  return this.completed;
 }
}


