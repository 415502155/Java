package com.gm.utils.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MapSort2 {
	/**
	 * map集合按主键排序,key必须是字符串类型的数字
	 * 
	 * @param unsort_map
	 *            需要排序的集合
	 * @param sort
	 *            false:降序、true:升序
	 * @return
	 */
	public static SortedMap<String, Object> sortByStringNumKey(
			Map<String, Object> unsort_map, Boolean sort) {
		Comparator compara = new ComparatorStringAsNum(sort);
		TreeMap<String, Object> result = new TreeMap<String, Object>(compara);

		Object[] unsort_key = unsort_map.keySet().toArray();

		Arrays.sort(unsort_key, compara);

		for (int i = 0; i < unsort_key.length; i++) {
			result.put(unsort_key[i].toString(), unsort_map.get(unsort_key[i]));
		}
		//return result.tailMap(result.firstKey());
		return result;
	}

	/**
	 * map集合按主键排序,key数字类型
	 * 
	 * @param unsort_map
	 *            需要排序的集合
	 * @param sort
	 *            false:降序、true:升序
	 * @return
	 */
	public static SortedMap<Integer, Object> sortByNumKey(
			Map<Integer, Object> unsort_map, Boolean sort) {
		Comparator compara = new ComparatorInteger(sort);
		TreeMap<Integer, Object> result = new TreeMap<Integer, Object>(compara);

		Object[] unsort_key = unsort_map.keySet().toArray();

		Arrays.sort(unsort_key, compara);

		for (int i = 0; i < unsort_key.length; i++) {
			result.put(Integer.parseInt(unsort_key[i].toString()), unsort_map.get(unsort_key[i]));
		}
		//return result.tailMap(result.firstKey());
		return result;
	}

	/**
	 * map集合按主键排序,key字符串型日期
	 * 
	 * @param unsort_map
	 *            需要排序的集合
	 * @param sort
	 *            false:降序、true:升序
	 * @dateType 是日期格式
	 * @return
	 */
	public static SortedMap<String, Object> sortByStringDateKey(
			Map<String, Object> unsort_map, Boolean sort, String dateType) {
		Comparator compara = new ComparatorStringAsDate(sort, dateType);
		TreeMap<String, Object> result = new TreeMap<String, Object>(compara);

		Object[] unsort_key = unsort_map.keySet().toArray();

		Arrays.sort(unsort_key, compara);

		for (int i = 0; i < unsort_key.length; i++) {
			result.put(unsort_key[i].toString(), unsort_map.get(unsort_key[i]));
		}
		//return result.tailMap(result.firstKey());
		return result;
	}

	/**
	 * map集合按主键排序,key字符串,采用String的compareTo实现排序
	 * 
	 * @param unsort_map
	 *            需要排序的集合
	 * @param sort
	 *            false:降序、true:升序
	 * @dateType 是日期格式
	 * @return
	 */
	public static SortedMap<String, Object> sortByStringKey(
			Map<String, Object> unsort_map, Boolean sort) {
		Comparator compara = new ComparatorString(sort);
		TreeMap<String, Object> result = new TreeMap<String, Object>(compara);

		Object[] unsort_key = unsort_map.keySet().toArray();

		Arrays.sort(unsort_key, compara);

		for (int i = 0; i < unsort_key.length; i++) {
			result.put(unsort_key[i].toString(), unsort_map.get(unsort_key[i]));
		}
		//return result.tailMap(result.firstKey());
		return result;
	}
}
