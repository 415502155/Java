/**
 * 
 */
package cn.edugate.esb.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;

/**
 * @name: Filter
 * @description: 过滤器
 * @date 2013-4-27 下午2:26:59
 * @version v1.0
 */
@SuppressWarnings("deprecation")
@JsonAutoDetect
public class Filter {

	
	private String property;
	private Object value;
	private Object val;
	private Operator operator=Operator.anywhere;
	
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
		if(this.value instanceof String)
		{
			this.val= this.value.toString();
		}
		if(this.value instanceof Map)
		{
			this.val= ((Map<?, ?>) this.value).get("val");
			Object op=((Map<?, ?>) this.value).get("operator");
			if(op!=null)operator=Enum.valueOf(Operator.class, op.toString());
		}
		
	}

	
	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	public Object getVal() {
		
		return val;
	}
	
	
	@JsonIgnore
	public boolean hasCriterion()
	{
		boolean has=false;
		if(this.property!=null&&!this.property.trim().isEmpty())
		{	if(Operator.start.equals(this.operator)
				||Operator.end.equals(this.operator)
				||Operator.anywhere.equals(this.operator))
			{
				has=(this.value!=null&&this.val!=null);//&&!this.value.trim().isEmpty());
			}else
			{
				has=true;
			}
		}
		return has;
	}
	@JsonIgnore
	public Criterion getCriterion()
	{
		Criterion c=null;
		if(this.hasCriterion())
		{
			if(Operator.eq.equals(this.operator))
			{
				c=Expression.eq(this.getProperty(), this.getVal());
			}else if(Operator.ne.equals(this.operator))
			{
				c=Expression.ne(this.getProperty(), this.getVal());
			}else if(Operator.lt.equals(this.operator))
			{
				c=Expression.lt(this.getProperty(), this.getVal());
			}else if(Operator.le.equals(this.operator))
			{
				c=Expression.le(this.getProperty(), this.getVal());
			}else if(Operator.gt.equals(this.operator))
			{
				c=Expression.gt(this.getProperty(), this.getVal());
			}else if(Operator.ge.equals(this.operator))
			{
				c=Expression.ge(this.getProperty(), this.getVal());
			}else if(Operator.start.equals(this.operator))
			{
				c=Expression.ilike(this.getProperty(), this.getVal().toString(),MatchMode.START);
			}else if(Operator.end.equals(this.operator))
			{
				c=Expression.ilike(this.getProperty(), this.getVal().toString(),MatchMode.END);
			}else if(Operator.in.equals(this.operator))
			{
				List<Object> data=new ArrayList<Object>();
				if(this.getVal().getClass().isArray())
				{
					Object[] array=(Object[])this.getVal();
					data.addAll(Arrays.asList(array));
					
				}
				if(this.getVal()instanceof Collection)
				{
					@SuppressWarnings("unchecked")
					Collection<Object> collection=(Collection<Object>)this.getVal();
					data.addAll(collection);					
				}
				
				c=Expression.in(this.getProperty(), data);
				
			}else
			{
				c=Expression.ilike(this.getProperty(), this.getVal().toString(),MatchMode.ANYWHERE);
			}
				
		}
		return c;
	}
}
