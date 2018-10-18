/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.util.h5.monitor;

import java.util.Random;

/**
 *
 * @author Administrator
 */
public class getFourRandom {
    public static void main(String args[]){
         /**
     * 产生4位随机数(0000-9999)
     * @return 4位随机数
     */
        getFourRandom();
        
    }
        public static String getFourRandom(){
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if(randLength<4){
          for(int i=1; i<=4-randLength; i++)
              fourRandom = "0" + fourRandom  ;
      }
            System.out.println(fourRandom);
        return fourRandom;
    } 
}
