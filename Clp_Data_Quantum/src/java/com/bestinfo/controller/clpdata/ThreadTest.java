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
    public class ThreadTest {  
      
        int j = 100;  
        private volatile long total =0;  
        private volatile int i=0;
        /** 
         * @param args 
         */  
        public static void main(String[] args) {  
            ThreadTest tt = new ThreadTest();  
            Inc inc = tt.new Inc();  
           
            for (int i = 0; i < 10; i++) {  
                Thread t = new Thread(inc);  
                t.start();  
                //t.sleep(1000);
                //sb.append()
            }  
            
            System.out.println("total  :"+tt.getTotal());
        }  
        
        private synchronized String inc() {  
            //sb.append
            System.out.println(Thread.currentThread().getName() + ": " + j++);
//            int temp=j++;
//            total += temp;
//            String result=String.valueOf(temp);
//            System.out.println(result);
             //StringBuffer sb =new StringBuffer();
             //sb.append(result);
             //System.out.println(sb);
            return "";
        }  
      
        class Inc implements Runnable {  
            public void run() {  
                //for (int i = 0; i < 90; i++) {  
                    inc();  
                //}  
            }  
        }  
        public long getTotal(){
          return this.total;
         }
    }  
