package cn.edugate.esb.authentication;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * 
 * @Name: 访问控制权限
 * @Description: 
 * @date  2013-5-23
 * @version V1.0
 */

public enum AC {
	read(0x0001,"浏览"),
	write(0x0003,"更新"),
	create(0x0005,"创建"),
	delete(0x0009,"删除"),
	approve(0x0011,"审批"),
	admin(0x00ff,"管理");
	
	static final EnumSet<AC> acs=EnumSet.allOf(AC.class);
	private final String text;
	private final int value;	

	public String getText() {
		return text;
	}

	
	public int getValue() {
		return value;
	}

	private AC(int value,String name){
    	this.value = value;
    	this.text=name;
    }
	
	public boolean hasPermission(AC ac)
	{
		return this.hasPermission(ac.value);
	}
	
	public boolean hasPermission(int value)
	{
		return (this.value&value)==value;
	}
	
	public boolean apply(AC ac)
	{
		return this.apply(ac.value);
	}
	
	public boolean apply(int value)
	{
		return (this.value&value)==this.value;
	}
	
	public static boolean hasPermission(int s,int t)
	{
		return (s&t)==t;
	}
	
	public static int forValue(String...acs)
	{
		int value=0;
		if(acs!=null)
		{
			for(String ac:acs) value=value|AC.valueOf(ac).value;
		}
		return value;
	}
	
	public static int forValue(AC...acs)
	{
		int value=0;
		if(acs!=null)
		{
			for(AC ac:acs)value=value|ac.value;
		}
		return value;
	}
	
	public static List<AC> getAC(int value)
	{
		List<AC> r=new ArrayList<AC>();
		for(AC ac:acs)
		{
			if((value&ac.value)==ac.value)r.add(ac);
		}
		return r;		
	}
	
	
}
