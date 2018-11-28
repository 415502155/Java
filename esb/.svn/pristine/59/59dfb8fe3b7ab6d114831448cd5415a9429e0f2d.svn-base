package cn.edugate.esb.comparator;

import java.util.Comparator;

import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.util.PinyinUtil;

public class ClassesSorter implements Comparator<Classes>{
	@Override  
	public int compare(Classes c1, Classes c2) {  
		String s1 = null != c1.getClas_name() ? (PinyinUtil.hanziToPinyin(c1.getClas_name()).substring(0, 1)) : "";
		String s2 = null != c2.getClas_name() ? (PinyinUtil.hanziToPinyin(c2.getClas_name()).substring(0, 1)) : "";
		if (s1.compareTo(s2) > 0) {
			return 1;
		} else if (s1.compareTo(s2) < 0) {
			return -1;
		} else {
			return c1.getClas_id().compareTo(c2.getClas_id());
		}
	} 
}
