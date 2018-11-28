package cn.edugate.esb.comparator;

import java.util.Comparator;
import java.util.Map;

import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.util.PinyinUtil;

public class MapKeyTeacherComparator implements Comparator<String> {

	Map<String, Teacher> base;
	public MapKeyTeacherComparator(Map<String, Teacher> tempMap) {
		// TODO Auto-generated constructor stub
		this.base = tempMap; 
	}

	public MapKeyTeacherComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(String str1, String str2) {
		// TODO Auto-generated method stub
//		try {
//			Integer obj1 = new Integer(str1);
//			Integer obj2 = new Integer(str2);
//			return obj1.compareTo(obj2);
//		} catch (Exception e) {
//			// TODO: handle exception
//			return str1.compareTo(str2);
//		}	
		String s1 = base.get(str1).getTech_name() != null ? (PinyinUtil.hanziToPinyin(base.get(str1).getTech_name()).substring(0, 1)) : "";
		String s2 = base.get(str2).getTech_name() != null ? (PinyinUtil.hanziToPinyin(base.get(str2).getTech_name()).substring(0, 1)) : "";
		if (s1.compareTo(s2) > 0) {
			return 1;
		} else if (s1.compareTo(s2) < 0) {
			return -1;
		} else {
			return base.get(str1).getTech_id().compareTo(base.get(str2).getTech_id());
		}
	}

}
