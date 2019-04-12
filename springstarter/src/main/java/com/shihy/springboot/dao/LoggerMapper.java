package com.shihy.springboot.dao;

import com.shihy.springboot.entity.Logger;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
@Mapper
@Repository
@Component
public interface LoggerMapper {
	
    int insert(Logger logger);
    /***
     * @author shy
     * @param @param startSize (page*size)
     * @param @param size 
     * @param @return
     * @return List<Logger>  
     * @throws @throws
     */
	List<Logger> getLoggerList(@Param("serchName") String serchName, @Param("action") Integer action, @Param("startSize") int startSize, @Param("size") int size);
	/***
	 * @author shy
	 * @param @param serchName
	 * @param @param action
	 * @param @return
	 * @return Integer  
	 * @throws @throws
	 */
	Integer getLoggerTotalPage(@Param("serchName") String serchName, @Param("action") Integer action);
	/***
	 * @Description: 批量删除（数组）
	 * @author shy
	 * @param @param delIds 数组[]
	 * @param @return
	 * @return Integer  
	 * @throws @throws
	 */
	Integer delLoggerByIds(@Param("delIds") String[] delIds);
	/***
	 * @Description: 批量删除（List）
	 * @author shy
	 * @param @param list List<Integer>
	 * @param @return
	 * @return Integer  
	 * @throws @throws
	 */
	Integer delLoggerByIdList(@Param("list") List<Integer> list);
	/***
	 * @Description: 批量添加
	 * @author shy
	 * @param @param logger
	 * @param @return
	 * @return int  
	 * @throws @throws
	 */
    int batchAddLogger(@Param("list") List<Logger> logger);
    /***
     * @Description: 获取日志列表
     * @author shy
     * @param @param targetName
     * @param @param action
     * @param @param startSize
     * @param @param size
     * @param @return
     * @return List<Map<String,Object>>  
     * @throws @throws
     */
    List<Map<String, Object>> getLoggerInfoList(@Param("target_name") String targetName, @Param("action") Integer action, @Param("startSize") Integer startSize, @Param("size") Integer size);
}