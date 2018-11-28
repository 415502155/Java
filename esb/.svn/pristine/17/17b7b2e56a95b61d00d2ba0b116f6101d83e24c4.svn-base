package cn.edugate.esb.util;

/**
 * 
 * @Name: pojo过滤接口
 * @Description: 
 * @date  2013-5-21
 * @version V1.0
 */
public interface FilterApply {
	public boolean apply(Object o);
	public FilterApply and(FilterApply filter);
	public FilterApply or(FilterApply filter);
	public FilterApply not();
	public FilterApply caseSensitive();
	public FilterApply inCaseSensitive();
}