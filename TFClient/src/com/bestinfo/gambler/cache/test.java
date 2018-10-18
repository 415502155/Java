/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.gambler.cache;

/**
 *
 * @author Admin
 */
public class test {

    public static void main(String[] args) {
//        System.out.println(CacheManager.getSimpleFlag("alksd"));
//        CacheManager.putCache("abc", new Cache());
//        CacheManager.putCache("def", new Cache());
//        CacheManager.putCache("ccc", new Cache());
//        CacheManager.clearOnly("");
//        Cache c = new Cache();
//        for (int i = 0; i < 10; i++) {
//            CacheManager.putCache("" + i, c);
//        }
//        CacheManager.putCache("aaaaaaaa", c);
//        CacheManager.putCache("abchcy;alskd", c);
//        CacheManager.putCache("cccccccc", c);
//        CacheManager.putCache("abcoqiwhcy", c);
        Cache d = new Cache();
        d.setValue("1234567890");
        CacheManager.putCache("aaa", d);
        System.out.println(CacheManager.getCacheInfo("aaa").getValue()) ;
//        System.out.println("删除前的大小：" + CacheManager.getCacheSize());
//        CacheManager.getCacheAllkey();
//        CacheManager.clearAll("aaaa");
//        System.out.println("删除后的大小：" + CacheManager.getCacheSize());
//        CacheManager.getCacheAllkey();
    }
}
