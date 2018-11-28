package cn.edugate.esb.util;

/**
 * 
 * @Name: 属性过滤器组
 * @Description:  
 * @date  2013-5-21
 * @version V1.0
 */
public class PropertyFilters implements FilterApply {
	FilterApply filter1;
	FilterApply filter2;
	FilterLogic logic=FilterLogic.None;
	
	private PropertyFilters()
	{
		
	}
	
	@Override
	public boolean apply(Object o) {
		// TODO Auto-generated method stub
		boolean result=false;
		if(FilterLogic.And.equals(logic))
		{
			result=filter1.apply(o)&&filter2.apply(o);
		}else if(FilterLogic.Or.equals(logic))
		{
			result=filter1.apply(o)||filter2.apply(o);
		}else if(FilterLogic.Not.equals(logic))
		{
			result=!filter1.apply(o);
		}else
		{
			result=filter1.apply(o);
		}
		return result;
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


	
	public static FilterApply not(FilterApply filter1)
	{
		PropertyFilters ps=new PropertyFilters();
		ps.filter1=filter1;
		ps.logic=FilterLogic.None;
		return ps;
	}
	
	public static FilterApply and(FilterApply filter1,FilterApply filter2)
	{
		PropertyFilters ps=new PropertyFilters();
		ps.filter1=filter1;
		ps.filter2=filter2;
		ps.logic=FilterLogic.And;
		return ps;
	}
	
	public static FilterApply or(FilterApply filter1,FilterApply filter2)
	{
		PropertyFilters ps=new PropertyFilters();
		ps.filter1=filter1;
		ps.filter2=filter2;
		ps.logic=FilterLogic.Or;
		return ps;
	}

	@Deprecated
	@Override
	public FilterApply caseSensitive() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Deprecated
	@Override
	public FilterApply inCaseSensitive() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
