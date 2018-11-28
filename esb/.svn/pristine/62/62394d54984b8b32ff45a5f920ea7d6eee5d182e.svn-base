package cn.edugate.esb.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;

/**
 * 
 * @Name: pojo对象集合查询器
 * @Description: 支持pojo对象集合的查询排序和重包装
 * @date  2013-5-21
 * @version V1.0
 * @param <E> pojo类型
 * 
 * @see FilterApply
 * @see PropertyFilter
 * @see PropertyFilters
 * @see Order
 * @see Select
 */
public class Selector<E> {
	private final List<E> raw=new ArrayList<E>();
	private final List<Order> orders=new ArrayList<Order>();
	private FilterApply filter=null;
	private int count=0;
	
	public int getCount() {
		return count;
	}

	private  Selector(Iterable<E> p)
	{
		if(p!=null)
		{
			Iterator<E> iterator=p.iterator();
			while(iterator.hasNext())raw.add(iterator.next());		
		}
		count=raw.size();
	}
	
	public static <E> Selector<E> from(Iterable<E> p)
	{
		return new Selector<E>(p);
	}
	
	public Selector<E> where(FilterApply filter)
	{
		this.filter=filter;
		return this;
	}
	
	public Selector<E> orderBy(Order... orders)
	{
		this.orders.clear();
		if(orders!=null){
			for(Order order:orders)
			{
				this.orders.add(order);
			}
		}
		
		return this;
	}
	
	public Selector<E> clearSort()
	{
		this.orders.clear();
		return this;
	}
	
	public List<E> select()
	{
		List<E> data = getData();		
		return data;
	}
	
	public E first()
	{
		E e=null;
		List<E> result=this.select();
		if(result.size()>0)
		{
			e=result.get(0);
		}
		return e;
	}
	
	
	public List<E> select(int start,int limit)
	{
		List<E> data = getData();
		List<E> result = getLimitData(start, limit, data);
		return result;
	}
	
	public <T> Map<T,E> map(Keys<T,E> keys)
	{
		Map<T,E> result=new HashMap<T,E>();
		if(keys!=null)
		{
			List<E> data = getData();
			
			for(E e:data)
			{
				T[] ts=keys.keys(e);
				if(ts!=null)
				{
					for(T t:ts)
					{
						result.put(t, e);
					}
				}
			}
		}
		return result;
	}
	
	public <T> Map<T,List<E>> mapAll(Keys<T,E> keys)
	{
		Map<T,List<E>> result=new HashMap<T,List<E>>();
		if(keys!=null)
		{
			List<E> data = getData();
			
			for(E e:data)
			{
				T[] ts=keys.keys(e);
				if(ts!=null)
				{
					for(T t:ts)
					{
						List<E> es=result.get(t);
						if(es==null)
						{
							es=new ArrayList<E>();
							result.put(t, es);
						}
						es.add(e);
					}
				}
			}
		}
		return result;
	}
	
	public <T> List<E> buildTree(final TreeBuilder<T,E> builder)
	{
		Map<T,E> map=this.map(new Keys<T,E>(){
			@SuppressWarnings("unchecked")
			@Override
			public T[] keys(E e) {
				// TODO Auto-generated method stub
				return (T[])new Object[]{builder.getId(e)};
			}			
		});
		List<E> roots=new ArrayList<E>();
		for(E e:map.values())
		{
			if(builder.getRootId().equals(builder.getParentId(e)))
			{
				roots.add(e);
			}
			E p=map.get(builder.getParentId(e));
			if(p!=null)
			{
				builder.addChild(p, e);
			}
		}		
		return roots;
	}
	
	private <T> String join(List<T> data,String delimiter)
	{
		StringBuilder builder=new StringBuilder();		
		for(T e:data)
		{
			builder.append(e)
			.append(delimiter);
		}
		int nlen=builder.length()-delimiter.length();
		if(nlen>=0)
		{
			builder.setLength(nlen);
		}
		return builder.toString();
	}
	
	public String join(String delimiter)
	{
		List<E> data = getData();
		return join(data,delimiter);
	}
	
	
	
	public String join(String delimiter,Select<String,E> select)
	{
		List<String> data = this.getSelectedData(select);
		
		return join(data,delimiter);
	}

	private <T> List<T> getLimitData(int start, int limit, List<T> data) {
		List<T> result =new ArrayList<T>();
		if(start<data.size())
		{
			for(int i=start;i<data.size()&&i<(start+limit);i++)
			{
				result.add(data.get(i));
			}
		}
		return result;
	}
	
	public <T> List<T>  select(Select<T,E> select)
	{
		List<T> result = getSelectedData(select);
		return result;
	}
	
	public <T> T first(Select<T,E> select)
	{
		T e=null;
		List<T> result =this.select(select);
		if(result.size()>0)
		{
			e=result.get(0);
		}
		return e;
	}
	
	public <T> List<T>  select(Select<T,E> select,int start,int limit)
	{
		List<T> data = getSelectedData(select);
		List<T> result = getLimitData(start, limit, data);
		return result;
	}
	
	private <T> List<T> getSelectedData(Select<T,E> select) {
		List<T> result =new ArrayList<T>();
		List<E> data = getData();
		for(E e:data)
		{
			result.add(select.apply(e));
		}
		return result;
	}

	private List<E> getData() {
		List<E> data =new ArrayList<E>();
		if(filter!=null)
		{
			for(E e:raw)
			{
				if(filter.apply(e))data.add(e);
			}
		}else
		{
			data.addAll(raw);
		}
		this.count=data.size();
		this.sort(data);
		return data;
	}
	
	
	
	private void sort(List<E> data)
	{
		Collections.sort(data,new Comparator<E>()
				{

					@SuppressWarnings({ "unchecked", "rawtypes" })
					@Override
					public int compare(E o1, E o2) {
						// TODO Auto-generated method stub
						int r=0;
						for(Order sort:orders)
						{
							Object v1=this.getValue(o1,sort.getProperty());
							Object v2=this.getValue(o2,sort.getProperty());
							if(v1==v2)
							{
								r=0;
							}else
							{
								if(Direction.ASC.equals(sort.getDirection()))
								{
									if(v1==null)
									{
										r=1;
									}else if(v2==null)
									{
										r=-1;
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
									
									
								}else
								{
									if(v1==null)
									{
										r=-1;
									}else if(v2==null)
									{
										r=1;
									}else
									{
										if(v2 instanceof Comparable)
										{
											r=((Comparable) v2).compareTo(v1);
										}else
										{
											r=0;
										}
									}
									
								}
							}
							if(r!=0)break;
						}
						return r;
					}
					
					private Object getValue(E pojo,String propertyName)
					{
						Object value=null;
						PropertyDescriptor pd=BeanUtils.getPropertyDescriptor(pojo.getClass(), propertyName);
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
			
				});
	}

	
	
	
	
}
