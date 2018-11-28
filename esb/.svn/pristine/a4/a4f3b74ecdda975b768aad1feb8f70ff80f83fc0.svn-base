package cn.edugate.esb.comparator;

import java.util.Comparator;

import cn.edugate.esb.entity.Field;

public class FieldComparator implements Comparator<Field> {

	@Override
	public int compare(Field o1, Field o2) {
		if (o1.getField_id().intValue() > o2.getField_id().intValue()) {
			return 1;
		} else if (o1.getField_id().intValue() < o2.getField_id().intValue()) {
			return -1;
		} else {
			return 0;
		}
	}
}
