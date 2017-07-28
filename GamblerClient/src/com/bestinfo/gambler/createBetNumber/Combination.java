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
public class Combination {

    /**
     * @param a:组合数组
     * @param k:生成组合个数
     * @return :所有可能的组合数组列表
     */
    public static ArrayList group(int[] a, int m) {
//            Combination zuhe = new Combination();
        ArrayList list = new ArrayList();
        int n = a.length;
        if (n == m) {
            list.add(a);
            return list;
        }
        boolean flag = false; // 是否是最后一种组合的标记

        // 生成辅助数组。首先初始化，将数组前n个元素置1，表示第一个组合为前n个数。
        int[] tempNum = new int[n];
        for (int i = 0; i < n; i++) {
            if (i < m) {
                tempNum[i] = 1;
            } else {
                tempNum[i] = 0;
            }
//                System.out.print(tempNum[i]);
        }
//            print(tempNum);// 打印辅助数组
        list.add(createResult(a, tempNum, m));// 打印第一中默认组合

        do {
            int pose = 0; // 记录改变的位置
            int sum = 0; // 记录改变位置 左侧 1 的个数
            // 然后从左到右扫描数组元素值的“10”组合，找到第一个“10”组合后将其变为“01”
            for (int i = 0; i < (n - 1); i++) {
                if (tempNum[i] == 1 && tempNum[i + 1] == 0) {
                    tempNum[i] = 0;
                    tempNum[i + 1] = 1;
                    pose = i;
                    break;
                }
            }
//                print(tempNum);// 打印辅助数组
            list.add(createResult(a, tempNum, m));// 打印第一中默认组合

            // 同时将其左边的所有“1”全部移动到数组的最左端。
            for (int i = 0; i < pose; i++) {
                if (tempNum[i] == 1) {
                    sum++;
                }
            }

            for (int i = 0; i < pose; i++) {
                if (i < sum) {
                    tempNum[i] = 1;
                } else {
                    tempNum[i] = 0;
                }
            }

            // 判断是否为最后一个组合：当第一个“1”移动到数组的m-n的位置，即n个“1”全部移动到最右端时，就得到了最后一个组合。
            flag = false;
            for (int i = n - m; i < n; i++) {
                if (tempNum[i] == 0) {
                    flag = true;
                }
            }
        } while (flag);

        return list;
    }

    // 根据辅助数组和原始数组生成 结果数组
    private static int[] createResult(int[] a, int[] temp, int m) {
        int[] result = new int[m];
        int j = 0;
        for (int i = 0; i < a.length; i++) {
            if (temp[i] == 1) {
                result[j] = a[i];
//                    System.out.println("result[" + j + "]:" + result[j]);
                j++;
            }
        }
        return result;
    }

    /**
     * 打印全组合数
     *
     * @param list
     */
    public static void printList(ArrayList list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println();
            int[] temp = (int[]) list.get(i);
            for (int j = 0; j < temp.length; j++) {
                System.out.print(temp[j] + " ");
            }
        }
    }

// 打印整数数组的方法
//        public void print(int[] a) {
//            System.out.println("生成的辅助数组为：");
//            for (int i = 0; i < a.length; i++) {
//                System.out.print(a[i]);
//            }
//            System.out.println();
//        }
    public static void main(String[] args) throws Exception {       
//        System.out.println(zuheShu(32, 7));
        
    }

}
