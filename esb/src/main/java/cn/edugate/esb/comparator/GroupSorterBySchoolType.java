package cn.edugate.esb.comparator;

import java.util.Comparator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.edugate.esb.util.PinyinUtil;

public class GroupSorterBySchoolType implements Comparator<Map<String,Object>>{

	@Override  
	public int compare(Map<String,Object> o1, Map<String,Object> o2) {  
		try {
			if(o1.containsKey("sorter")&&o2.containsKey("sorter")&&null!=o1.get("sorter")&&StringUtils.isNotBlank(o1.get("sorter").toString())&&null!=o2.get("sorter")&&StringUtils.isNotBlank(o2.get("sorter").toString())){

				String s1 = o1.get("sorter").toString();
				String s2 = o2.get("sorter").toString();
				
				int ss1 = getNum(s1);
				int ss2 = getNum(s2);
				
				if(ss1>ss2){
					return -1;
				}else if(ss1<ss2){
					return 1;
				}else if(ss1==ss2){
					String n1 = null != s1 ? (PinyinUtil.hanziToPinyin(s1).substring(0, 1)) : "";
					String n2 = null != s2 ? (PinyinUtil.hanziToPinyin(s2).substring(0, 1)) : "";
					if (n1.compareTo(n2) > 0) {
						return 1;
					} else if (n1.compareTo(n2) < 0) {
						return -1;
					} else {
						return s1.compareTo(s2);
					}
				}else{
					return 0;
				}
			}else{
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	} 
	
	private int getNum(String name){
		if(name.indexOf("幼儿园")>-1){
			return 6;
		}else if(name.indexOf("小学")>-1){
			return 5;
		}else if(name.indexOf("初中")>-1){
			return 4;
		}else if(name.indexOf("高中")>-1){
			return 3;
		}else{
			return 2;
		}
	}
}
