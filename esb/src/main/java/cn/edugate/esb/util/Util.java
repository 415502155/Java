package cn.edugate.esb.util;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.type.TypeReference;
import org.dom4j.Element;
import org.hibernate.criterion.Criterion;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * 
 * @Name: 通用工具接口
 * @Description: 
 * @date  2013-3-20 
 * @version V1.0
 */
public interface Util {

	/**
	 * 得到当前系统时间
	 * @return 当前系统时间
	 */
	public Date now();
	
	/**
	 * 获取只包含日期部分的Date对象
	 * @param date Date对象
	 * @return 只包含日期部分的Date对象
	 */
	public Date getDate(Date date);
	/**
	 * 获取只包含时间部分的Date对象
	 * 注意返回对象为GMT时间的时间部分
	 * @param date Date对象
	 * @return 只包含时间部分的Date对象
	 */
	public Date getTime(Date date);	
	
	
	
	
	
	
	public int toInt(Object value,int def);
	public long toLong(Object value,long def);
	public double toDouble(Object value,double def);
	
	public org.hibernate.criterion.Order[] getOrders(String sort);
	public Filter[] getFilters(String filters);

	public <T> T getPOJOFromXml(Element e, Class<T> pojoClass) throws Exception;
	/**
	 * Get list of POJO from request.
	 * @param data json data from request 
	 * @param pojoClass pojo type
	 * @param ignoreProperties except properties
	 * @return list of POJO
	 */
	public  <T> List<T> getPojoFromRequest(Object data,Class<T> pojoClass);
	
	public  <T> List<T> getPojoFromRequest(Object data,	Class<T> pojoClass,JacksonSetting setting);


	
	
	/**
	 * Transform json data format into POJO object
	 * @param data json data from request 
	 * @param pojoClass pojo type
	 * @param ignoreProperties except properties
	 * @return list of POJO
	 */
	public  <T> T getPojoFromJSON(Object data, Class<T> pojoClass);
	public  <T> T getPojoFromJSON(Object data, Class<T> pojoClass,JacksonSetting setting);
	public  <T> T getPojoFromJSON(Object data, TypeReference<T> typeRef);
	public  <T> T getPojoFromJSON(Object data, TypeReference<T> typeRef,JacksonSetting setting);
	
	/**
	 * Transform json data format into list of POJO objects
	 * @param data json data from request 
	 * @param pojoClass pojo type
	 * @param ignoreProperties except properties
	 * @return list of POJO
	 */
	public  <T> List<T> getPojosFromJSON(Object data, Class<T> pojoClass);
	public  <T> List<T> getPojosFromJSON(Object data, Class<T> pojoClass,JacksonSetting setting);

	/**
	 * Transform pojo object into  json data format
	 * @param data pojo object 
	 * @param ignoreProperties except properties
	 * @return json data
	 */
	public String getJSONFromPOJO(Object data);
	public String getJSONFromPOJO(Object data,JacksonSetting setting);
	
	
	/**
	 * 从包packageName 中获取所有的Class 
	 * @param packageName 
	 * @return 包中所有的Class 
	 */
	public Set<Class<?>> getClasses(String packageName) ;
	
	/**
	 * 从包package 中获取所有的Class
	 * @param pack
	 * @return 包中所有的Class 
	 */
	public Set<Class<?>> getClasses(Package pack) ;
	
	public boolean equals(Object s,Object t);
	
	/**
	 * 字符串是否为空
	 * @param input
	 * @return
	 */
	public boolean emptyStr(String input);
	/**
	 *  根据指定时间范围计算查询条件
	 * @param propertyName 属性名
	 * @param period 时间范围描述信息,如"day","month","quarter","year"分别表示最近一天,最近一个月,最近一个季度,最近一年.
	 * @return
	 */
	public Criterion calculatePeriodCriterion(String propertyName, String period);
	
	public FilterApply calculatePeriodPropertyFilter(String propertyName, String period);
	/**
	 * 截取字符串
	 * @param str 原字符串
	 * @param width 截取长度
	 * @param ellipsis 省略符号
	 * @return
	 */
	public String abbreviate(String str, int width, String ellipsis);
	/**
	 * 过滤掉html代码
	 * @param inputString 输入字符串
	 * @return
	 */
	public String Html2Text(String inputString);
	
	/**
	 * 读取文件
	 * @param path 文件路径
	 * @return
	 */
	public String ReadFile(String path);

	public String SMSsend(String phone, String content) throws Exception;

	public String schoolsms(String tel, String text,String title) throws Exception;
	/**
	 * 获取图片地址
	 * @param id
	 * @return
	 */
	public String getFilePathUrl(Integer id, String ext);

	public String getPathByPicName(String logo);
    /**
     * 上传文件
     * @param request
     * @return 文件名
     */
	public String upload(MultipartHttpServletRequest multipartRequest,String fileName);

	/**
	 * 获取excel网络地址
	 * @param filePathName
	 * @return
	 */
	public Object getPathByExcelName(String filePathName);
	
	
	public String uploadSWF(MultipartHttpServletRequest multipartRequest,String fileName);

	public void schoolsms(String[] es, String content, String title);

	public String wxsms(String phone, String content) throws Exception;

}