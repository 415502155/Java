package cn.edugate.esb.params.filter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"is_del","del_time","update_time","insert_time","org_id","user_id","clas_id","grade_id","classList",
		"org_id","user_id","stud_record","stud_card","stud_note"
})
public interface StudentForGroupMemberFilter {

}
