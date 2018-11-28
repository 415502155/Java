package cn.edugate.esb.comparator;

import java.util.Comparator;

public class MapKeyComparator implements Comparator<String> {

	@Override
	public int compare(String str1, String str2) {
		// TODO Auto-generated method stub
		try {
			Integer obj1 = new Integer(str1);
			Integer obj2 = new Integer(str2);
			return obj1.compareTo(obj2);
		} catch (Exception e) {
			// TODO: handle exception
			return str1.compareTo(str2);
		}		
	}

}
