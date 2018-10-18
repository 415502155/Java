package com.bestinfo.dao.impl;

import com.bestinfo.dao.IBaseDao;
import com.bestinfo.dao.page.Pagination;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author yangyf
 */
@Repository
public class BaseDaoImpl implements IBaseDao {

    protected static Logger logger = Logger.getLogger(BaseDaoImpl.class);

    /**
     * 返回Integer类型值,比如数量等
     *
     * @param jdbcTemplate
     * @param sql
     * @param args
     * @return
     */
    public Integer queryForInteger(JdbcTemplate jdbcTemplate, final String sql, final Object[] args) {
        try {

            return jdbcTemplate.queryForObject(sql, args, Integer.class);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 返回clazz类型的对象
     *
     * @param jdbcTemplate
     * @param sql
     * @param args
     * @param clazz
     * @return
     */
    public <T> T queryForObject(JdbcTemplate jdbcTemplate, final String sql, final Object[] args,
            final Class<T> clazz) {
        try {
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args);
            if (list != null && list.size() > 0) {
                Map<String, Object> data = list.get(0);
                if (data != null) {
                    T retObj = this.mapObject(data, clazz);
                    return retObj;
                }
            }

            return null;
        } catch (Exception e) {
            logger.error("queryForObject error", e);
            return null;
        }
    }

    /**
     * 返回clazz类型对象List，要求clazz类型的成员变量名与sql中返回的字段名称相同
     *
     * @param jdbcTemplate
     * @param sql
     * @param args
     * @param clazz
     * @return
     */
    protected <T> List<T> queryForList(JdbcTemplate jdbcTemplate, final String sql, final Object[] args,
            final Class<T> clazz) {
        try {            //YangRong 改 ,原位置在for前 9/12
            List<Map<String, Object>> datas = jdbcTemplate.queryForList(sql, args);

            List<T> retList = new ArrayList<>();

            // 遍历List中的所有数据
            for (Map<String, Object> data : datas) {
                // 以clazz中的成员变量为基准，遍历clazz类型的所有成员变量，依次到Map中取值，然后赋值，
                // 没有取到值的不进行赋值
                T distObj = this.mapObject(data, clazz);
                retList.add(distObj);
            }
            return retList;
        } catch (Exception e) {
            logger.error("", e);
            return Collections.emptyList();
        }
    }

    /**
     * 获取某一页的数据
     *
     * @param <T>
     * @param jdbcTemplate
     * @param sql 业务sql
     * @param countSql 数量sql
     * @param pageNumber 当前页码
     * @param pageSize 每页数量
     * @param args 参数
     * @param clazz
     * @return
     */
    public <T> Pagination<T> queryForPage(JdbcTemplate jdbcTemplate, final String sql,
            final String countSql, final int pageNumber, final int pageSize,
            final Object[] args, final Class<T> clazz) {
        //logger.info(sql);
        //logger.info(countSql);

        int total = 0;
        if (args == null) {
            total = jdbcTemplate.queryForObject(countSql, Integer.class);
        } else {
            total = jdbcTemplate.queryForObject(countSql, args, Integer.class);
        }
        int startIndex = (pageNumber - 1) * pageSize + 1;
        if (startIndex <= 0) {
            startIndex = 1;
        }
        int endIndex = startIndex + pageSize - 1;

        String pageSql = "SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (" + sql + ") A WHERE ROWNUM <= " + endIndex + " ) WHERE RN >= " + startIndex;
        logger.info(pageSql);

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(pageSql, args);

        List<T> retList = new ArrayList<>();
        try {
            // 遍历List中的所有数据
            for (Map<String, Object> data : rows) {
                // 以clazz中的成员变量为基准，遍历clazz类型的所有成员变量，依次到Map中取值，然后赋值，
                // 没有取到值的不进行赋值
                T distObj = this.mapObject(data, clazz);
                retList.add(distObj);
            }
        } catch (Exception e) {
            logger.error("", e);
        }

        Pagination<T> pagination = new Pagination(pageNumber, pageSize, total, retList);
        return pagination;
    }

    /**
     * 返回clazz类型的实例，数据来源data，data中的key名称要与clazz的成员变量名称一致
     *
     * @param data
     * @param clazz
     * @return
     * @throws Exception
     */
    private <T> T mapObject(final Map<String, Object> data, final Class<T> clazz)
            throws Exception {
        // 获取所有成员变量
        Field[] fields = clazz.getDeclaredFields();
        T distObj = clazz.newInstance();
        //org.apache.commons.beanutils.PropertyUtils.getWriteMethod(descriptor);
        //org.apache.commons.beanutils.PropertyUtils.setSimpleProperty(bean, name, value);
        //org.apache.commons.beanutils.BeanUtils.setProperty(bean, name, value);
        for (Field field : fields) {
            String fieldName = field.getName();
            Object fieldValue = data.get(fieldName);

            // 如果存在值，则进行赋值
            if (fieldValue != null) {
                PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
                Method setMethod = pd.getWriteMethod();
                // 获取字段类型
                Class type = field.getType();
                if (Integer.class.equals(type)) {
                    // Integer
                    Integer integerVal = Integer.valueOf(fieldValue.toString());
                    setMethod.invoke(distObj, integerVal);
                } else if (Long.class.equals(type)) {
                    // Long
                    Long longVal = Long.valueOf(fieldValue.toString());
                    setMethod.invoke(distObj, longVal);
                } else if (Date.class.equals(type)) {
//                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    // Date
                    Date time = (Date) fieldValue;
                    Date time2 = new Date(time.getTime());
                    setMethod.invoke(distObj, time2);
                    //setMethod.invoke(distObj, TimeUtil.simple_formatter_date_time.parse(fieldValue.toString()));
                } else if (Short.class.equals(type)) {
                    //Short
                    if (fieldValue.getClass().equals(Boolean.class)) {
                        if (fieldValue.equals(Boolean.TRUE)) {
                            fieldValue = new Integer(1);
                        }
                        if (fieldValue.equals(Boolean.FALSE)) {
                            fieldValue = new Integer(0);
                        }
                    }
                    Short shortVal = Short.valueOf(fieldValue.toString());
                    setMethod.invoke(distObj, shortVal);
                } else if (BigInteger.class.equals(type)) {
                    setMethod.invoke(distObj, new BigInteger(fieldValue.toString(), 10));
                } else {
                    setMethod.invoke(distObj, fieldValue);
                }
            }
        }
        return distObj;
    }
}
