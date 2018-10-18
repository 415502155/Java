package com.gm.utils.sort;

import java.sql.Timestamp;
import java.util.Comparator;

import com.gm.utils.DateUtil;

public class ComparatorStringAsDate implements Comparator<String> {
	private boolean sort = true;//默认是升序排序
	private String format = DateUtil.DATE_FORMAT;
	
	
	
	public ComparatorStringAsDate(boolean sort, String format) {
		super();
		this.sort = sort;
		this.format = format;
	}

	@Override
	public int compare(String o1, String o2) {
		Timestamp p1 = DateUtil.cString2Timestamp(o1, format);
		Timestamp p2 = DateUtil.cString2Timestamp(o2, format);
		if(sort){//升序
			if(p1.before(p2)){
				return -1;
		    }else if(p1 == p2){
				return 0;
		    }else{
		    	return 1;
		    }
		}else{//降序
			if(p1.after(p2)){
				   return -1;
		    }else if(p1 == p2){
				   return 0;
		    }else{
		    	return 1;
		    }
		}
	}

}
