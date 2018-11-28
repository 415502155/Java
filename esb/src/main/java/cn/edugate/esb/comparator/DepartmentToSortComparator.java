package cn.edugate.esb.comparator;

import java.util.Comparator;
import cn.edugate.esb.entity.Department;
import cn.edugate.esb.util.PinyinUtil;

public class DepartmentToSortComparator implements Comparator<Department> {
	@Override  
	public int compare(Department o1, Department o2) {  
	  // TODO Auto-generated method stub  
	  String s1=o1.getDep_name()!=null?(PinyinUtil.hanziToPinyin(o1.getDep_name()).substring(0, 1)):"";  
	  String s2=o2.getDep_name()!=null?(PinyinUtil.hanziToPinyin(o2.getDep_name()).substring(0, 1)):"";  
	  if(s1.compareTo(s2)>0) {  
	    return 1;  
	  } else if(s1.compareTo(s2)<0){  
	    return -1;  
	  } else{
		  return o1.getDep_id().compareTo(o2.getDep_id());
	  }
//	  return s1.compareTo(s2);
	} 
}