package cn.edugate.esb.util;

import java.util.HashMap;

class Mixins extends HashMap<Class<?>, Class<?>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1161817385494619888L;

	public static Mixins c()
	{
		return new Mixins();
	}
	
	public Mixins set(Class<?> target, Class<?> mixinSource)
	{
		this.put(target, mixinSource);
		return this;
	}
	
}
