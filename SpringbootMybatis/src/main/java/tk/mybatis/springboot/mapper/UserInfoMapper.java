/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package tk.mybatis.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import tk.mybatis.springboot.model.UserInfo;
import tk.mybatis.springboot.model.UserRoleMenuInfo;

@Mapper
public interface UserInfoMapper{
	
	@Select("select * from t_user_info ")
	List<UserInfo>  findUserInfo();
	
	List<UserInfo> queryAll();
	
	List<UserInfo> getPageUserInfo(@Param("start")int start,@Param("end")int end);
	
	@Select("SELECT * FROM t_user_info a, t_role_info b where a.role_id = b.role_id")
	List<UserInfo> getUserRoleInfo();
	
	@Select("select count(1) from t_user_info a, t_role_info b where a.role_id = b.role_id")
	int CountUserRoleInfo();
	
	@Select("select count(1) from t_user_info")
	int CountUserInfo();
	
	List<UserInfo> getUserRolePageInfo(@Param("start")int start,@Param("end")int end);
	
	@Update("update t_user_info set user_name = #{userName} where user_id = #{userId}")
	int updateUserInfo(@Param("userName")String userName,@Param("userId")int userId);
	//校验所添加的信息是否有重复的user_name
	int checkUserName(@Param("userName")String userName);
	//批量注册
	int addUserInfo(@Param("ulist")List<UserInfo> ulist);
	
	List<UserInfo> getUserInfoById(@Param("id")Integer id);
	
	List<UserInfo> getUserInfoByName(@Param("name")String name,@Param("soft")String soft);
	
	List<UserRoleMenuInfo> getUserRoleMenu(@Param("userName")String userName); 
	
	List<UserInfo> getUserRoleMenu1(@Param("userName")String userName); 
}
 