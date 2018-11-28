package cn.edugate.esb.util;

public class Property {
	private final  String name;

	public String getName() {
		return name;
	}

	public Property(String name) {
		assert(name!=null);
		this.name = name;
	}
	
	public static Property c(String name)
	{
		return new Property(name);
	}
}
