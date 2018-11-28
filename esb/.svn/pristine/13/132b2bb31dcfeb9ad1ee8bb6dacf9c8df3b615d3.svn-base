/**
 * 
 */
package cn.edugate.esb.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.springframework.beans.BeanUtils;

/**
 * 
 * @Name: 属性过滤器
 * @Description:  
 * @date  2013-5-21
 * @version V1.0
 */
public class PropertyFilter implements FilterApply {

	
	private Property property;
	private Object value;
	private Operator operator=Operator.anywhere;
	private Boolean caseSensitive = true;//区分大小写,默认不区分
	
	
	
	public Boolean getCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(Boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public static FilterApply eq(String property,Object value)
	{
		PropertyFilter p = buildFilter(property, value);
		p.setOperator(Operator.eq);
		return p;
	}

	public static FilterApply ne(String property,Object value)
	{
		PropertyFilter p = buildFilter(property, value);
		p.setOperator(Operator.ne);
		return p;
	}


	public static FilterApply lt(String property,Object value)
	{
		PropertyFilter p = buildFilter(property, value);
		p.setOperator(Operator.lt);
		return p;
	}
	
	public static FilterApply le(String property,Object value)
	{
		PropertyFilter p = buildFilter(property, value);
		p.setOperator(Operator.le);
		return p;
	}
	public static FilterApply gt(String property,Object value)
	{
		PropertyFilter p = buildFilter(property, value);
		p.setOperator(Operator.gt);
		return p;
	}
	public static FilterApply ge(String property,Object value)
	{
		PropertyFilter p = buildFilter(property, value);
		p.setOperator(Operator.ge);
		return p;
	}
	public static FilterApply in(String property,Collection<Object> value)
	{
		PropertyFilter p = buildFilter(property, value);
		p.setOperator(Operator.in);
		return p;
	}
	public static FilterApply start(String property,Object value)
	{
		PropertyFilter p = buildFilter(property, value);
		p.setOperator(Operator.start);
		return p;
	}
	
	public static FilterApply end(String property,Object value)
	{
		PropertyFilter p = buildFilter(property, value);
		p.setOperator(Operator.end);
		return p;
	}
	
	public static FilterApply anywhere(String property,Object value)
	{
		PropertyFilter p = buildFilter(property, value);
		p.setOperator(Operator.anywhere);
		return p;
	}
	
	private static PropertyFilter buildFilter(String property, Object value) {
		PropertyFilter p=new PropertyFilter();
		p.setProperty(new Property(property));
		p.setValue(value);
		return p;
	}
	
	
	
	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	
	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	public boolean canApply()
	{
		boolean has=false;
		if(this.property!=null)
		{	if(Operator.start.equals(this.operator)
				||Operator.end.equals(this.operator)
				||Operator.anywhere.equals(this.operator))
			{
				has=(this.value!=null&&!this.value.toString().trim().isEmpty());
			}else
			{
				has=true;
			}
		}
		return has;
	}
	
	public boolean apply(Object o)
	{
		boolean result=true;
		if(this.canApply())
		{
			Object v1=this.getPojoValue(o,this.property);
			Object v2=this.value;
			if(this.value instanceof Property)
			{
				v2=this.getPojoValue(o,(Property)this.value);
			}
			if(Operator.eq.equals(this.operator))
			{
				result=eq(v1, v2);
			}else if(Operator.ne.equals(this.operator))
			{
				result=!eq(v1, v2);
			}else if(Operator.lt.equals(this.operator))
			{
				result=compare(v1,v2)<0;
			}else if(Operator.le.equals(this.operator))
			{
				result=compare(v1,v2)<=0;
			}else if(Operator.gt.equals(this.operator))
			{
				result=compare(v1,v2)>0;
			}else if(Operator.ge.equals(this.operator))
			{
				result=compare(v1,v2)>0;
			}else if(Operator.in.equals(this.operator))
			{
				result=in(v1, v2);
			}else if(Operator.start.equals(this.operator))
			{
				result=start(v1, v2);
			}else if(Operator.end.equals(this.operator))
			{
				result=end(v1, v2);
			}else
			{
				result=anywhere(v1, v2);
			}
				
		}
		return result;
	}
	
	private Object getPojoValue(Object pojo,Property property)
	{
		Object value=null;
		PropertyDescriptor pd=BeanUtils.getPropertyDescriptor(pojo.getClass(), property.getName());
		if(pd!=null)
		{
			if(pd.getReadMethod()!=null)
			{
				try {
					value=pd.getReadMethod().invoke(pojo);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	private boolean eq(Object s,Object t)
	{
		boolean result=false;
		if(s==t)
		{
			result=true;
		}else if(s!=null)
		{
			result=s.equals(t);
		}else if(t!=null)
		{
			result=t.equals(s);
		}
		return result;
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int compare(Object v1,Object v2)
	{
		int r=0;
		if(v1==null)
		{
			r=-1;
		}else if(v2==null)
		{
			r=1;
		}else
		{
			if(v1 instanceof Comparable)
			{
				r=((Comparable) v1).compareTo(v2);
			}else
			{
				r=0;
			}
		}
		return r;
	}
	
	private boolean in(Object v1,Object v2)
	{
		boolean r=true;
		if(v1!=null&&v2!=null)
		{
			if(v2 instanceof Collection)
			{
				r=((Collection<?>)v2).contains(v1);
			}else
			{
				r=false;
			}
		}
		return r;
	}
	
	private boolean start(Object v1,Object v2)
	{
		boolean r=true;
		if(v1!=null&&v2!=null)
		{
			r=v1.toString().startsWith(v2.toString());
		}
		return r;
	}
	
	private boolean end(Object v1,Object v2)
	{
		boolean r=true;
		if(v1!=null&&v2!=null)
		{
			r=v1.toString().endsWith(v2.toString());
		}
		return r;
	}
	
	private boolean anywhere(Object v1,Object v2)
	{
		boolean r=true;
		if(v1!=null&&v2!=null)
		{
			if(!this.caseSensitive){
				r=v1.toString().toLowerCase().contains(v2.toString().toLowerCase());
			}
			else{
				r=v1.toString().contains(v2.toString());
			}
		}
		return r;
	}

	public void caseSenSitive(){
		this.caseSensitive = true;
	}


	@Override
	public FilterApply and(FilterApply filter) {
		// TODO Auto-generated method stub
		return PropertyFilters.and(this, filter);
	}



	@Override
	public FilterApply or(FilterApply filter) {
		// TODO Auto-generated method stub
		return PropertyFilters.or(this, filter);
	}

	@Override
	public FilterApply not() {
		// TODO Auto-generated method stub
		return PropertyFilters.not(this);
	}

	@Override
	public FilterApply caseSensitive() {
		this.caseSensitive = true;
		return this;
	}
	
	@Override
	public FilterApply inCaseSensitive() {
		this.caseSensitive = false;
		return this;
	}




}
