package cn.edugate.esb.service;

import cn.edugate.esb.entity.FunctionUseRange;

/**
 * 功能适用范围服务接口
 * Title:FunctionUseRangeService
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月16日下午3:39:30
 */
public interface FunctionUseRangeService {
	/**
	 * 添加新的功能适用范围
	 * @param function
	 */
	public abstract void add(FunctionUseRange functionUseRange);
	/**
	 * 添加新的功能适用范围
	 * @param gradeNumber 适用年级字符串，用逗号连接
	 */
	public abstract void addByGradeNumber(String gradeNumber,FunctionUseRange functionUseRange);
	/**
	 * 根据功能主键返回所有使用年级(以逗号连接的字符串)
	 * @param id
	 * @return
	 */
	public abstract String getGradeNumStringByFunID(Integer id);
	/**
	 * 添加新的功能适用范围，同时删除旧的功能适用范围
	 * @param gradeNumber
	 * @param functionUseRange
	 */
	public abstract void updateByGradeNumber(String gradeNumber,FunctionUseRange functionUseRange);
	/**
	 * 根据功能主键删除所有旧的适用范围数据
	 * @param functionID
	 */
	public abstract void deleteByFunctionID(Integer functionID);
}
