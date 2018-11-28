package cn.edugate.esb.comparator;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import cn.edugate.esb.entity.Teacher;

public class TechToSortComparator implements Comparator<Teacher> {
	@Override  
	public int compare(Teacher o1, Teacher o2) {  
		Collator c = Collator.getInstance(Locale.CHINA);
		return c.compare(o1.getTech_name(), o2.getTech_name());
	} 
}