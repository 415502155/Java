package sng.comparator;

import java.util.Comparator;
import java.util.Date;

import sng.entity.Charge;


public class ChargeSorter implements Comparator<Charge>{
	@Override  
	public int compare(Charge c1, Charge c2) {  
		try {
			Date s1=c1.getStart_time(); 
			Date s2=c2.getStart_time();   
			if(s1.after(s2)) {  
				return -1;  
			} else if(s2.after(s1)){  
				return 1;  
			} else {
				return c1.getCid().compareTo(c2.getCid());
			}
		} catch (Exception e) {
			return 0;
		}
	} 
}
