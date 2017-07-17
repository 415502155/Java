package com.gm.utils.sort;

import java.util.Comparator;

public class ComparatorString implements Comparator<String> {
	private boolean sort = true;// 默认是升序排序

	
	public ComparatorString(boolean sort) {
		super();
		this.sort = sort;
	}


	@Override
	public int compare(String o1, String o2) {
		int com = o1.compareTo(o2);
		if (com == 0) { // "a","a" 比较
			return 0;
		}

		if (sort) {
			if (com < 0) {// "a","b" 比较
				return -1;
			}else{// "b","a" 比较
				return 1;
			}
		}else{
			if (com > 0) {// "a","b" 比较
				return -1;
			}else{// "b","a" 比较
				return 1;
			}
		}
	}
}
