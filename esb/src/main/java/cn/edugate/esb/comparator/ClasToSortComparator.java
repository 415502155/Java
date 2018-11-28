package cn.edugate.esb.comparator;

import java.util.Comparator;
import cn.edugate.esb.entity.Classes;

public class ClasToSortComparator implements Comparator<Classes>{
	@Override  
	public int compare(Classes o1, Classes o2) {  
	  // TODO Auto-generated method stub  
	  int s1=o1.getClas_id();  
	  int s2=o2.getClas_id();  
	  if(s1>s2) {  
	    return 1;  
	  } else {  
	    return -1;  
	  }  
	} 
}
