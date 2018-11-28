package sng.comparator;

import java.util.Comparator;

public class MapKeyComparator implements Comparator<String> {

	@Override
	public int compare(String str1, String str2) {
		try {
			Integer obj1 = new Integer(str1);
			Integer obj2 = new Integer(str2);
			return obj1.compareTo(obj2);
		} catch (Exception e) {
			return str1.compareTo(str2);
		}
	}

}
