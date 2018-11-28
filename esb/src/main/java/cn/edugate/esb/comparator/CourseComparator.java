package cn.edugate.esb.comparator;

import java.util.Comparator;

import cn.edugate.esb.entity.Course;

public class CourseComparator implements Comparator<Course> {

	@Override
	public int compare(Course o1, Course o2) {
		if (o1.getCour_id().intValue() > o2.getCour_id().intValue()) {
			return 1;
		} else if (o1.getCour_id().intValue() < o2.getCour_id().intValue()) {
			return -1;
		} else {
			return 0;
		}
	}
}
