package com.shihy.springboot.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.shihy.springboot.utils.CommonUtils;

public class ArrayCompareTest {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Integer[] arr1 = {1,2,3,5,6};
		//Integer[] arr1 = {};
		Integer[] arr2 = {2,4,5,7};
		//compareArr(arr1, arr2);
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();
		list1.add(1);list1.add(2);list1.add(11);list1.add(12);
		list2.add(1);list2.add(10);list2.add(11);list2.add(13);list2.add(14);list2.add(15);
		List<Integer> list21 = CommonUtils.existence(list2, list1);
		System.out.println("1集合中存在的元素但是2中不存在---------------- :" + list21);
		List<Integer> list12 = CommonUtils.existence(list1, list2);
		System.out.println("1集合中存在的元素但是2中不存在---------------- :" + list12);
		List<Integer> unionSet = CommonUtils.unionSet(list1, list2);
		System.out.println("1与2并集结果---------------- :" + unionSet);
		List<Integer> intersectionSet = CommonUtils.intersectionSet(list1, list2);
		System.out.println("1与2交集结果---------------- :" + intersectionSet);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void compareArr(Integer[] arr1, Integer[] arr2) {
		List<Integer> list1 = Arrays.asList(arr1);
		List<Integer> list2 = Arrays.asList(arr2);
		
		// 创建集合 求交集
		Collection<Integer> c1 = new ArrayList<Integer>(list1);
		Collection<Integer> c2 = new ArrayList<Integer>(list2);
		c1.retainAll(c2);
		System.out.println("arr1与arr2交集结果：" + c1);
		int len1 = list1.size();
		int len2 = list2.size();
		
		//求arr1存在的元素，arr2不存在
		List<Integer> l1 = new ArrayList<Integer>();
		for(int i = 0; i < len1; i++) {
			Integer num = list1.get(i);
			if (!list2.contains(num)) {
				l1.add(num);
			}
		}
		System.out.println("arr1集合中存在的元素但是arr2中不存在 ：" + l1);
		
		//求arr2存在的元素，arr1不存在
		List<Integer> l2 = new ArrayList<Integer>();
		for(int i = 0; i < len2; i++) {
			Integer num = list2.get(i);
			if (!list1.contains(num)) {
				l2.add(num);
			}
		}
		System.out.println("arr2集合中存在的元素但是arr1中不存在 ：" + l2);
		
		//求并集
		Set result = new HashSet();
		result.addAll(list1);
		result.addAll(list2);
		System.out.println("并集结果：" + result);
	}
}
