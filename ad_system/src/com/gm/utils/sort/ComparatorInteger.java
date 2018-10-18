package com.gm.utils.sort;

import java.util.Comparator;

public class ComparatorInteger implements Comparator<Integer> {
	private boolean sort = true;//默认是升序排序
	
	
	public ComparatorInteger(boolean sort) {
		super();
		this.sort = sort;
	}


	@Override
	public int compare(Integer p1, Integer p2) {//对象integer
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
