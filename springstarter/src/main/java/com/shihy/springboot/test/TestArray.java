package com.shihy.springboot.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description add, subtract, multiply and divide
 * @data 2019年2月14日 下午3:41:58
 *
 */
@Slf4j
public class TestArray {

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a", 1);
		map.put("b", 11);
		map.put("c", 111);
		map.put("d", 1111);
		map.put("e", 11111);
		addArray(map);
	}
	
	public static void addArray(Map<String, Object> map) {
		String[] arrayKey = map.keySet().toArray(new String[map.size()]);
		log.info("arrayKey :" + Arrays.toString(arrayKey));

		Set<String> keySet = map.keySet();
		int len = keySet.size();
		Integer [] arrayValue = new Integer[len];
		int i = 0;
		for (String key : keySet) {
			Integer value = (Integer) map.get(key);
			arrayValue[i] = value;
			i++;
		}
		log.info("arrayValue :" + Arrays.toString(arrayValue));
		
		String [] arrays = new String[len];
		int j = 0;
		for (String key : keySet) {
			Integer value = (Integer) map.get(key);
			String keyValue = key + " : " + value;
			arrays[j] = keyValue;
			j++;
		}
		log.info("arrays :" + Arrays.toString(arrays));
	}
}
