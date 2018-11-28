package cn.edugate.esb.util;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JacksonSetting {
	static Logger logger=LoggerFactory.getLogger(JacksonSetting.class);
		
	private final Mixins mixins = Mixins.c();
	private final Ignores ignores = Ignores.c();
	
	
	public static JacksonSetting c()
	{
		return new JacksonSetting();
	}
	
	public JacksonSetting reset(Mixins mixins,Ignores ignores)
	{
		if(mixins!=null)this.mixins.putAll(mixins);
		if((ignores!=null))this.ignores.putAll(ignores);
		return this;
	}
	

	public Mixins getMixins() {
		return mixins;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder s = new StringBuilder();
		s.append("ignores:");
		if(ignores!=null)s.append(ignores);
		s.append(";");
		s.append("Mixins:");
		if(mixins!=null)s.append(mixins);
		return s.toString();
	}


	public Ignores getIgnores() {
		return ignores;
	}

	

	public JacksonSetting add(Class<?> clazz,List<String> ignoreProperties)
	{
		this.ignores.add(clazz, ignoreProperties);
		return this;
	}
	
	public JacksonSetting add(Class<?> clazz,String... ignoreProperties)
	{
		this.ignores.add(clazz, ignoreProperties);
		return this;
	}
	
	public JacksonSetting set(Class<?> target, Class<?> mixinSource)
	{
		this.mixins.set(target, mixinSource);
		return this;
	}
	

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int hashcode=JacksonSetting.class.hashCode();
		if(mixins!=null)
		{
			hashcode+=mixins.hashCode();
		}
		if(ignores!=null)
		{
			hashcode+=ignores.hashCode();
		}
		
		return hashcode;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		boolean equals=false;
		if(obj==this)
		{
			equals= true;
		}else
		{
			if(obj  instanceof JacksonSetting)
			{
				JacksonSetting t=(JacksonSetting)obj;
				equals=LSHelper.equals(this.mixins, t.mixins)&&LSHelper.equals(this.ignores, t.ignores);
			}else
			{
				equals=false;
			}
		}
		return equals;
	}
	
	public JacksonSetting copy()
	{
		JacksonSetting copy=new JacksonSetting();
		copy.mixins.putAll(this.mixins);
		copy.ignores.putAll(this.ignores);
		return copy;
	}
}
