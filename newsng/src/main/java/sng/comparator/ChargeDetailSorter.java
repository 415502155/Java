package sng.comparator;

import java.util.Comparator;
import java.util.Date;

import sng.entity.ChargeDetail;


public class ChargeDetailSorter implements Comparator<ChargeDetail>{
	@Override  
	public int compare(ChargeDetail c1, ChargeDetail c2) {  
		try {
			Date s1=c1.getStart_time(); 
			Date s2=c2.getStart_time();   
			if(s1.after(s2)) {  
				return -1;  
			} else if(s2.after(s1)){  
				return 1;  
			} else {
				return c1.getCd_id().compareTo(c2.getCd_id());
			}
		} catch (Exception e) {
			return 0;
		}
	} 
}
