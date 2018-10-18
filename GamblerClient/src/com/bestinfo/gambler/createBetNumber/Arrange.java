/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.gambler.createBetNumber;

import java.util.ArrayList;

/**
 *
 * @author chenliping
 */
public class Arrange {
     /**
      * 计算给定数组的k维的排列值
      * @param original
      * @param k
      * @return 
      */
    public static int[][] allSort(int[] original, int k) {
        ArrayList<int[]> results = new ArrayList<int[]>();          //存放结果的List，每个元素是一个int[k]的数组，
        if (k == 1) {                                            //相当于从数组中只取出一个元素的排列。
//            System.out.println(original.length);
            int[][] last = new int[original.length][1];
            for (int i = 0; i < original.length; i++) {
                last[i][0] = original[i];
            }
            return last;
        } else {
            for (int i = 0; i < original.length; i++) {            //对数组的元素都是取出一个来,再对剩余的元素求k-1的排列    
                int temp = original[i];                              //取出第i个来，放在temp中
                int[] rest = new int[original.length - 1];             //下面把除了第i个元素，把其余的的合为一个数组rest。
                System.arraycopy(original, 0, rest, 0, i);
                for (int j = i + 1; j < original.length; j++) {              //original[i]以后的元素
                    rest[j - 1] = original[j];
                }
                int[][] tempResult = allSort(rest, k - 1);               //再对rest数组求k-1个元素的排列。结果放在tempResult数组中
//                System.out.println("tempResult's length:" + tempResult.length);
                int[][] result = new int[tempResult.length][k];      //对每一种k-1排列要把temp加在最前面。从面成为k排列，              
//                System.out.println("result's length:" + result.length);
                for (int j = 0; j < result.length; j++) {                  //对每一种k-1排列要把temp加在最前面。从面成为k排列
                    result[j][0] = temp;                             //每一种k排列第一个元素是temp
                    for (int m = 1; m < k; m++) {                          //每一种k排列剩余部分就是k-1排列。把tempResult相应的行照搬过来
                        result[j][m] = tempResult[j][m - 1];
                    }
//                    System.out.println("j:" + j);
//                    System.out.println("result[" + j + "] : " + result[j].length);
                    results.add(result[j]);                         //把k排列加入到结果队列。
//                    System.out.println("results's size :" + results.size());
                }
            }
//            System.out.println("r's size :" + results.size());
            return (results.toArray(new int[0][0]));                    //返回结果。
        }
    }
}
