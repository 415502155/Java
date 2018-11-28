package cn.edugate.esb.params.filter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"is_del","del_time","update_time","insert_time","org_id","user_id","dep_id","tech_card","tech_note",
		"dep_name","org_name","user_mobile","json","user_mobile_type","role_name","user","user_idnumber","user_mail"
})
public interface TeacherForGroupMemberFilter {

}
