package cn.edugate.esb.params.filter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"is_del","del_time","update_time","insert_time","clas_id","grade_id","json"
})
public interface StudentFilter {

}
