package cn.edugate.esb.util;

/**
 * 
 * @Name: pojo重包装接口
 * @Description: 
 * @date  2013-5-21
 * @version V1.0
 * @param <T>
 * @param <E>
 */
public interface Select<T, E> {
	
	
	public T apply(E e);
		
	
}
