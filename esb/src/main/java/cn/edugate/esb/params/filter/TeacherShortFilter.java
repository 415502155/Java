package cn.edugate.esb.params.filter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"is_del","del_time","update_time","insert_time","tech_card","tech_head","org_id","org_name","dep_name","dep_id","json","user_mobile_type"
})
public interface TeacherShortFilter {

}
