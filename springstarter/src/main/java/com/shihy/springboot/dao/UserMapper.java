package com.shihy.springboot.dao;

import com.shihy.springboot.entity.User;
import com.shihy.springboot.entity.UserExample;
import com.shihy.springboot.excelpojo.UserPojo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
@Component
public interface UserMapper {
	
	@Select("select * from t_user ")
	List<User> getUserList();
	
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExampleWithRowbounds(UserExample example, RowBounds rowBounds);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> getUserListByPage( @Param("pageStartSize") Integer pageStartSize, @Param("pageSize") Integer pageSize, @Param("order")  String order) throws Exception;

    Map<String, Object> getUserInfoByUserId(@Param("user_id") Integer userId);
    
    @SuppressWarnings("rawtypes")
	Map<String, Object> getUserInfoByMap(Map map);
    
    Map<String, Object> getUserRoleMenuInfo(@Param("user_id") Integer userId);
    
    List<Map<String, Object>> getList(@Param("user_name") String userName);
    
    void insertByBatch(List<UserPojo> list);
}