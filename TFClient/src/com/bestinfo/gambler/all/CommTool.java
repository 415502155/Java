/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.gambler.all;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 *
 * @author chenliping
 */
public class CommTool {

    private static final Logger logger = Logger.getLogger(CommTool.class);

    /**
     * 遍历文件夹
     *
     * @param strPath 需要遍历的文件目录
     * @param filelist 存放目录下面的文件
     */
    public static void refreshFileList(String strPath, ArrayList<String> filelist) {
        File dir = new File(strPath);
        File[] files = dir.listFiles();

        if (files == null) {
            logger.error(strPath + " 目录中无文件！");
            return;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                refreshFileList(files[i].getPath(), filelist);
            } else {
//                String strFileName = files[i].getAbsolutePath().toLowerCase();//取绝对路径
                String strFileName = files[i].getPath();
//                if (strFileName.equals(StaticVariable.NUMBERMODELFILE)) {
//                    continue;
//                }//排除模板文件
//                System.out.println("---" + strFileName);
                filelist.add(strFileName);
            }
        }
    }

    /**
     * 生成指定范围[litter,max]内的不重复n个int数
     *
     * @param litter
     * @param max
     * @param n
     */
    public static int[] getRandInt(int litter, int max, int n) {
        int[] intRet = new int[n];
        int intRd = 0; //存放随机数
        int count = 0; //记录生成的随机数个数
        int flag = 0; //是否已经生成过标志
        while (count < n) {
//            Random rdm = new Random(System.currentTimeMillis());
            Random rdm = new Random();
//            intRd = Math.abs(rdm.nextInt()) % 32 + 1;
            intRd = litter + rdm.nextInt(max - litter + 1);
            for (int i = 0; i < count; i++) {
                if (intRet[i] == intRd) {
                    flag = 1;
                    break;
                } else {
                    flag = 0;
                }
            }
            if (flag == 0) {
                intRet[count] = intRd;
                count++;
            }
        }
//        System.out.println("随机数:");
//        for (int t = 0; t < n; t++) {
//            System.out.println(t+":" + intRet[t]);            
//        }
        return intRet;
    }

    private static BigInteger jiecheng(int num) {
        if (num == 0) {
            return BigInteger.valueOf(1);
        } else {
            return BigInteger.valueOf(num).multiply(jiecheng(num - 1));
        }
    }

    //计算从n中取m个数的组合的总数值
    //combin(n,m)=n(n-1)…(n-m+1)/1*2…m=n!/m!(n-m)!
    //n为可选的集合数组的长度,m为组合需要的个数
    public static BigInteger zuheShu(int n, int m) {
//        return jiecheng(n) / (jiecheng(m) * jiecheng(n - m));
        return jiecheng(n).divide(jiecheng(m).multiply(jiecheng(n - m)));
    }

//    p(n,m)=n(n-1)(n-2)……(n-m+1)= n!/(n-m)!(规定0!=1)
    public static BigInteger pailieShu(int n, int m) {
//        return jiecheng(n) / jiecheng(n - m);
        return jiecheng(n).divide(jiecheng(n - m));
    }

    /**
     * 校验数字 如果出错，返回错误描述 返回为空，表示校验正确
     */
    public static String examinenumber(String number) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^\\+?[1-9][0-9]*$");
        java.util.regex.Matcher ticketnumSelectmatch = pattern.matcher(number);
        if (!ticketnumSelectmatch.matches()) {
            return "请输入有效数字！";
        }
        return null;
    }

    /**
     * 将号码写进文件
     *
     * @param number
     * @throws Exception
     */
    public static void WriteStringTofile(ArrayList<String> number, String path) throws Exception {
        File file = new File(path);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
        for (String string : number) {
            bw.write(string);
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    public static void WriteStringTofile(String number, String path) throws Exception {
        File file = new File(path);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
        bw.write(number);
        bw.newLine();
        bw.flush();
        bw.close();
    }

    /**
     * 从文件读取指定行信息
     *
     * @param filePath
     * @param startLine
     * @param endLine
     * @return
     */
    public List<String> getIndicateLines(String filePath, int startLine, int endLine) {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            logger.error("", e);
            return null;
        }
        byte[] b = new byte[1];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        boolean eol = false;
        List<String> multiLine = new ArrayList<String>();
        int currentLine = 0;
        boolean startRead = false;
        try {
            while (bis.read(b, 0, 1) != -1) {
                if (startLine == 1) {
                    startRead = true;
                }
                if (b[0] == 13) {
                    eol = true;
                } else {
                    if (eol) {
                        if (b[0] == 10) {
                            ++currentLine;
//                        System.out.println("currentLine:" + currentLine);
                            if (currentLine == startLine - 1) {
                                startRead = true;
                            }
                            if (currentLine > endLine) {
                                break;
                            } else {
                                if (currentLine >= startLine) {
//                                System.out.println("current line:" + currentLine);
                                    String stemp = bos.toString();
                                    if (!stemp.equals("")) {
                                        multiLine.add(stemp);
                                    }
                                    bos.reset();
                                    continue;
                                }
                            }
                        }
                        eol = false;
                    }
                }

                if (startRead && b[0] != 10 && b[0] != 13) {
                    bos.write(b, 0, 1);
                }
            }
            bis.close();
            bis = null;
            bos.close();
            bos = null;
        } catch (IOException e) {
            logger.error("", e);
            return null;
        }
        return multiLine;
    }


    public static String getTime(long tm) {
        int ms = (int) (tm % 1000);
        tm /= 1000;
        int sc = (int) (tm % 60);
        tm /= 60;
        int mn = (int) (tm % 60);
        tm /= 60;
        int hr = (int) (tm % 24);
        long dy = tm / 24;
        return dy + " days " + hr + " hours " + mn + " minutes " + sc + "." + ms + " sec ";
    }
    
   
    
    

    public static void main(String[] args) throws Exception {
//        getRandInt(1, 2, 2);
//        System.out.println(getTime(5000));   
        int max = 20;
        int min = 10;
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int s = random.nextInt(max) % (max - min + 1) + min;
            System.out.println(s);
        }

//        int litter = 10;
//        int max = 20;
//        for (int i = 0; i < 10; i++) {
//            Random rdm = new Random();
//            int intRd = litter + rdm.nextInt(max - litter + 1);
//            System.out.println(intRd);
//        }

    }
}
