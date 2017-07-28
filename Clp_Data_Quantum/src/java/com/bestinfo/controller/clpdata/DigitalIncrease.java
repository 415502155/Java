/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.controller.clpdata;

/**
 *
 * @author Administrator
 */
public class DigitalIncrease extends Thread {
 
 Digital digital = null; 

DigitalIncrease(Digital digtal){
  this.digital = digtal;
  start();
 }

public void run(){
  while(true){
   if(digital.isCompleted())
         break; //如果加法操作已经完成了 就退出线程
   digital.increase();
  }
 }
 
 public static void main(String [] args){
  
  Digital digital = new Digital();
  DigitalIncrease [] diArray = new DigitalIncrease[10];
  for(int i = 0; i<10; i++){
   diArray[i]= new DigitalIncrease(digital);  //创建10个线程
  }
  
  //如果加法没完成就让主线程等待
  try {
   synchronized (digital){
    while(!digital.isCompleted())
         digital.wait();
   }
  }catch (InterruptedException e) {
   e.printStackTrace(); 
  }
  
  System.out.println(digital.getTotal());
 }
}
