package cn.edugate.esb.params.filter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"is_del","del_time","update_time","insert_time","org_id","cam_id","start_time","clas_card",
		"clas_logo","clas_newspaper","student_num","class_master"})
public interface ClassesFilter {

}
