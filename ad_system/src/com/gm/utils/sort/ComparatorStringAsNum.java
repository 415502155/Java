package com.gm.utils.sort;

import java.util.Comparator;

public class ComparatorStringAsNum implements Comparator<String> {
	private boolean sort = true;//默认是升序排序
	public ComparatorStringAsNum(boolean sort) {
		super();
		this.sort = sort;
	}

	@Override
	public int compare(String o1, String o2) {//对象是String形式的数字
		Double p1 = Double.valueOf(o1);
		Double p2 = Double.valueOf(o2);
		if(sort){//升序
			if(p1 < p2){
				   return -1;
		    }else if(p1 == p2){
				   return 0;
		    }else{
		    	return 1;
		    }
		}else{//降序
			if(p1 > p2){
				   return -1;
		    }else if(p1 == p2){
				   return 0;
		    }else{
		    	return 1;
		    }
		}
	}
}
