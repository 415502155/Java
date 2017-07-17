package com.gm.utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MapSort {
	/**
	 * map集合按主键排序
	 * @param unsort_map
	 * @return
	 */
	 public static SortedMap<String, Object> mapSortByKey(Map<String, Object> unsort_map) {
	   TreeMap<String, Object> result = new TreeMap<String, Object>();

	    Object[] unsort_key = unsort_map.keySet().toArray();
	    Arrays.sort(unsort_key);

	    for (int i = 0; i < unsort_key.length; i++) {
	     result.put(unsort_key[i].toString(), unsort_map.get(unsort_key[i]));
	    }
	    return result.tailMap(result.firstKey());
	   }
	 
	 	/**
		 * map集合按主键排序,key必须是字符串类型的数字
		 * @param unsort_map
		 * @return
		 */
		 public static SortedMap<String, Object> mapSortByKeyString(Map<String, Object> unsort_map) {
		   TreeMap<String, Object> result = new TreeMap<String, Object>(new Comparator(){
				@Override
				public int compare(Object o1, Object o2) {
					int p1 = Integer.parseInt((String)o1);
					int p2 = Integer.parseInt((String)o2);
					if(p1 < p2){
						   return -1;
				    }else if(p1 == p2){
						   return 0;
				    }else{
				    	return 1;
				    }
				}});

		    Object[] unsort_key = unsort_map.keySet().toArray();
		    Arrays.sort(unsort_key,new Comparator(){
				@Override
				public int compare(Object o1, Object o2) {
					int p1 = Integer.parseInt((String)o1);
					int p2 = Integer.parseInt((String)o2);
					if(p1 < p2){
						   return -1;
				    }else if(p1 == p2){
						   return 0;
				    }else{
				    	return 1;
				    }
				}
		    	
		    });

		    for (int i = 0; i < unsort_key.length; i++) {
		     result.put(unsort_key[i].toString(), unsort_map.get(unsort_key[i]));
		    }
		    return result.tailMap(result.firstKey());
		   }
	 
	 /**
		 * map集合按主键排序
		 * @param unsort_map
		 * @return
		 */
		 public static SortedMap<Object, Object> mapSortByKeyObj(Map<Object, Object> unsort_map) {
		   TreeMap<Object, Object> result = new TreeMap<Object, Object>();

		    Object[] unsort_key = unsort_map.keySet().toArray();
		    Arrays.sort(unsort_key);

		    for (int i = 0; i < unsort_key.length; i++) {
		     result.put(unsort_key[i], unsort_map.get(unsort_key[i]));
		    }
		    return result.tailMap(result.firstKey());
		 }

}
