package cn.edugate.esb.comparator;

import java.util.Comparator;
import java.util.Map;

import cn.edugate.esb.util.PinyinUtil;

@SuppressWarnings("rawtypes")
public class MapToSortGroupComparator implements Comparator<Map>{

	@Override
	public int compare(Map o1, Map o2) {
		// TODO Auto-generated method stub
	  String s1=o1.get("group_name")!=null?(PinyinUtil.hanziToPinyin(o1.get("group_name").toString()).substring(0, 1)):"";  
	  String s2=o2.get("group_name")!=null?(PinyinUtil.hanziToPinyin(o2.get("group_name").toString()).substring(0, 1)):"";
	  if(s1.compareTo(s2)>0) {  
	    return 1;  
	  } else if((s1.compareTo(s2)<0)) {  
	    return -1;  
	  } else{
		  String o1s = o1.get("group_id")!=null?o1.get("group_id").toString():"";
		  String o2s = o2.get("group_id")!=null?o2.get("group_id").toString():"";
		  return o1s.compareTo(o2s);
	  }
//	  return s1.compareTo(s2);
	}
}
