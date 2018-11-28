package cn.edugate.esb.comparator;

import java.util.Comparator;
import cn.edugate.esb.util.PinyinUtil;

public class GroupSorter implements Comparator<Object> {
	@Override
	public int compare(Object o1, Object o2) {
		Object[] objs1 = (Object[]) o1;
		Object[] objs2 = (Object[]) o2;

		String s1 = null != objs1[2] ? (PinyinUtil.hanziToPinyin(objs1[2].toString()).substring(0, 1)) : "";
		String s2 = null != objs2[2] ? (PinyinUtil.hanziToPinyin(objs2[2].toString()).substring(0, 1)) : "";
		if (s1.compareTo(s2) > 0) {
			return 1;
		} else if (s1.compareTo(s2) < 0) {
			return -1;
		} else {
			return objs1[2].toString().compareTo(objs2[2].toString());
		}
	}
}