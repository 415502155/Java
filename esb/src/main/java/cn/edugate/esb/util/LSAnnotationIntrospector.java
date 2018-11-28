package cn.edugate.esb.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;



public class LSAnnotationIntrospector extends JacksonAnnotationIntrospector {
	private final Ignores ignores = new Ignores();
	
	
	private LSAnnotationIntrospector(Ignores ignores)
	{
		if(ignores!=null)this.ignores.putAll(ignores);
	}
	
	public static LSAnnotationIntrospector getAnnotationIntrospector(Ignores ignores)
	{
		LSAnnotationIntrospector ai=null;
		if(ignores!=null&&!ignores.isEmpty())
		{
			ai=new LSAnnotationIntrospector(ignores);
		}
		return ai;
	}
	
	
	public String[] findPropertiesToIgnore(AnnotatedClass ac) {
		
		List<String> properties =new ArrayList<String>();
		String[] Properties=super.findPropertiesToIgnore(ac);
		if(Properties!=null)Collections.addAll(properties, Properties);
		List<String> s=ignores.get(ac.getAnnotated());
		if(s!=null)properties.addAll(s);
		return properties.toArray(new String[]{});
	}

}
