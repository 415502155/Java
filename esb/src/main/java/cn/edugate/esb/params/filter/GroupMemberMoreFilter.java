package cn.edugate.esb.params.filter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"is_del","del_time","update_time","insert_time","group_id","mem_id","type","org_id"})
public interface GroupMemberMoreFilter {

}
