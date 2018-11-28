package cn.edugate.esb.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class Ignores extends HashMap<Class<?>, List<String>>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5476474294168103398L;

	public static Ignores c()
	{
		return new Ignores();
	}
	
	public Ignores add(Class<?> clazz,List<String> ignoreProperties)
	{
		List<String> s=this.get(clazz);
		if(s==null)
		{
			s = new  ArrayList<String>();
			this.put(clazz, s);
		}
		if(s!=null)s.addAll(ignoreProperties);
		return this;
	}
	
	public Ignores add(Class<?> clazz,String... ignoreProperties)
	{
		List<String> s=this.get(clazz);
		if(s==null)
		{
			s = new  ArrayList<String>();
			this.put(clazz, s);
		}
		if(ignoreProperties!=null&&ignoreProperties.length>0)s.addAll(Arrays.asList(ignoreProperties));
		return this;
	}
	
}
