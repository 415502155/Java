package cn.edugate.esb.comparator;

import java.util.Comparator;

import cn.edugate.esb.entity.Clas2Student;
import cn.edugate.esb.util.PinyinUtil;

public class StudToSortComparator implements Comparator<Clas2Student> {

	@Override
	public int compare(Clas2Student o1, Clas2Student o2) {
		// TODO Auto-generated method stub
		String s1= o1.getStud_name()!=null? (PinyinUtil.hanziToPinyin(o1.getStud_name()).substring(0, 1)):"";  
		String s2= o2.getStud_name()!=null? (PinyinUtil.hanziToPinyin(o2.getStud_name()).substring(0, 1)):"";
		if(s1.compareTo(s2)>0) {  
		    return 1;  
		} else if(s1.compareTo(s2)<0){  
		    return -1;  
		} else{		  
			return o1.getStud_id().compareTo(o2.getStud_id());
		}
	}

}
