package cn.edugate.esb.params.filter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"is_del","del_time","update_time","insert_time","org_name"
})
public interface TeacherFilter {

}
