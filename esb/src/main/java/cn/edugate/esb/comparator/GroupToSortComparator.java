package cn.edugate.esb.comparator;

import java.util.Comparator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.edugate.esb.util.PinyinUtil;

public class GroupToSortComparator implements Comparator<Map<String,Object>> {
	@Override  
	public int compare(Map<String,Object> o1, Map<String,Object> o2) {  
		if(o1.containsKey("group_name")&&o2.containsKey("group_name")&&null!=o1.get("group_name")&&null!=o2.get("group_name")&&StringUtils.isNotBlank(o1.get("group_name").toString())&&StringUtils.isNotBlank(o2.get("group_name").toString())){
			String s1=PinyinUtil.hanziToPinyin(o1.get("group_name").toString().substring(0, 1));  
			String s2=PinyinUtil.hanziToPinyin(o2.get("group_name").toString().substring(0, 1));  
			if(s1.compareTo(s2)>0) {  
				return 1;  
			} else if(s1.compareTo(s2)<0){  
				return -1;  
			} else{
				return 0;
			}
		}else{
			return 0;
		}
	}
}