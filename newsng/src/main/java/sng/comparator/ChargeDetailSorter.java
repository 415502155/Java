package sng.comparator;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import sng.entity.ChargeDetail;


public class ChargeDetailSorter implements Comparator<ChargeDetail>{
	@Override  
	public int compare(ChargeDetail c1, ChargeDetail c2) {  
		try {
			Collator c = Collator.getInstance(Locale.CHINA);
			String s1=c1.getStud_name();
			String s2=c2.getStud_name();
			if(c.compare(s1,s2)>0){
				return 1;  
			}else if(c.compare(s1,s2)>0){
				return -1;  
			}else{
				return c1.getCd_id().compareTo(c2.getCd_id());
			}
		} catch (Exception e) {
			return 0;
		}
	} 
}
