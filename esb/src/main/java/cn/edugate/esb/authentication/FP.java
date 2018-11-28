package cn.edugate.esb.authentication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @Name: 功能权限定义
 * @Description: 
 * @date  2013-5-23
 * @version V1.0
 */
public class FP implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String name;
	private final String text;
	private final String fun_id;
	private final List<AC> acs=new ArrayList<AC>();
	
	public String getFun_id() {
		return fun_id;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * 功能名称
	 * @return 功能名称
	 */
	public String getText() {
		return text;
	}
	/**
	 * 访问控制权限列表
	 * @return 访问控制权限列表
	 */
	public List<AC> getAcs() {
		return Collections.unmodifiableList(acs);
	}
	
	
	public FP(String name,String text,String fun_id, String[] acs) {
		this(name,text,fun_id);
		if(acs!=null)
		{
			for(String ac:acs)
			{
				AC _ac=AC.valueOf(ac);
				this.acs.add(_ac);
			}
		}
	}
	
	public FP(String name,String text,String fun_id, AC ...acs) {
		this.name=name;
		this.text = text;
		this.fun_id=fun_id;
		if(acs!=null)
		{
			for(AC ac:acs)this.acs.add(ac);
		}
	}

	public FP() {
		// TODO Auto-generated constructor stub
		this.name="";
		this.text="";
		this.fun_id="";
	}
	
	

}
