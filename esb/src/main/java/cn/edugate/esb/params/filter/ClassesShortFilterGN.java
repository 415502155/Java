package cn.edugate.esb.params.filter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"is_del","del_time","update_time","insert_time","grade","campus","class_master","students","teachers","masters","student_num",
		"teacher_num","clas_card","clas_newspaper","clas2stud_id","cam_name"})
public interface ClassesShortFilterGN {

}
