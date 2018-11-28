package cn.edugate.esb.comparator;

import java.util.Comparator;
import java.util.Map;

import cn.edugate.esb.util.PinyinUtil;

@SuppressWarnings("rawtypes")
public class MapToSortComparator implements Comparator<Map>{

	@Override
	public int compare(Map o1, Map o2) {
		// TODO Auto-generated method stub
	  String s1=o1.get("mem_name")!=null?(PinyinUtil.hanziToPinyin(o1.get("mem_name").toString()).substring(0, 1)):"";  
	  String s2=o2.get("mem_name")!=null?(PinyinUtil.hanziToPinyin(o2.get("mem_name").toString()).substring(0, 1)):"";
	  if(s1.compareTo(s2)>0) {  
	      return 1;  
	  } else if(s1.compareTo(s2)<0) {  
	      return -1;  
	  } else{
		  String ms1=o1.get("mem_id")!=null?o1.get("mem_id").toString():"";
		  String ms2=o2.get("mem_id")!=null?o2.get("mem_id").toString():"";
		  return ms1.compareTo(ms2);
	  }
//	  return s1.compareTo(s2);
	}
}
