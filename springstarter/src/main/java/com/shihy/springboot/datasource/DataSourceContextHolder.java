package com.shihy.springboot.datasource;
import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 动态切换数据源；
 * @data 2019年3月28日 下午3:47:51
 *
 */
@Slf4j
public class DataSourceContextHolder {
	/***
	 * 默认数据源
	 */
    public static final String DEFAULT_DS = "master";
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
 
    /**
	 * @return the contextholder
	 */
	public static ThreadLocal<String> getContextholder() {
		return contextHolder;
	}
    
    /***
     * 
     * @Description: 设置数据源名
     * @param @param dbType
     * @return void  
     * @throws @throws
     */
    public static void setDB(String dbType) {
        log.info("切换到{"+dbType+"}数据源");
        contextHolder.set(dbType);
    }
 
    /***
     * @Description: 获取数据源名
     * @param @return
     * @return String  
     * @throws @throws
     */
    public static String getDB() {
        return (contextHolder.get());
    }
 
    /***
     * @Description: 清除数据源名
     * @param 
     * @return void  
     * @throws @throws
     */
    public static void clearDB() {
        contextHolder.remove();
    }
}
