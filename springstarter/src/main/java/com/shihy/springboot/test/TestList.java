package com.shihy.springboot.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
public class TestList {
	public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(100);
        list.add(200);
        list.add(300);
        list.add(300);
        list.add(400);
        list.add(600);
        list.add(500);
        list.add(700);
        list.add(600);
        System.out.println("未去重复 ：" + list);
        //①利用set集合
        /*list = new ArrayList<Integer>(new LinkedHashSet<Integer>(list));
        System.out.println("去重复 未排序 ：" + list);*/
        //②利用contains方法
        List<Integer> withoutList = new ArrayList<Integer>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (!withoutList.contains(list.get(i))) {
                    withoutList.add(list.get(i));
                }
            }
        }
        System.out.println("去重复 未排序 ：" + withoutList);
        Collections.sort(withoutList);
        System.out.println("去重复 已排序 ：" + withoutList);
        //③利用equals方法
        /*if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = list.size() - 1; j > i; j--) {
                    if (list.get(j).equals(list.get(i))) {
                        list.remove(j);
                    }
                }
            }
        }
        System.out.println("去重复 未排序 ：" + list);*/

        //④利用Collections.frequency(List, "value")方法  value在List中的个数
        /*List<Integer> withoutList = new ArrayList<Integer>();
        for (Integer l : list) {
            if (Collections.frequency(withoutList, l) < 1) {
                withoutList.add(l);
            }
        }
        System.out.println("去重复 未排序 ：" + withoutList);*/
        
    
    }
    /***
     * 
     * @Description: TODO
     * @author shy
     * @param 
     * @return void  
     * @throws @throws
     */
    public static void fun() {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("k1", "123");
        map.put("k2", "456");
        map.put("k3", "789");
        map.put("k4", "147");
        map.put("k5", "258");
        map.put("k6", "369");
        map.put("k7", "159");
        map.put(null, "null");
        map.put(null, null);
        Set<String> keySet = map.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = (String) map.get(key);
            System.out.println("key >>>>> :" + key + " value >>>>> :" + value);
        }
        /***
         * hash = hash(key.hashCode)
         * Entry<K,V> entry = table(index_for(hash,table.length))
         */
        String a="白龙马", b="沙和尚", c="八戒", d="唐僧", e="悟空";
        List<String> people=new ArrayList<String>();
        people.add(a);
        people.add(b);
        people.add(c);
        people.set(0, d);   //.set(index, element);
        people.add(1, e);   //.add(index, element);

        for(String p : people){
            System.out.println(p);
        }
    } 						
}
