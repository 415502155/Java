package com.shy.springboot.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.Statement;
import com.shy.springboot.encryption.MD5;
import com.shy.springboot.entity.User;
import com.shy.springboot.utils.CommonUtils;
import com.shy.springboot.utils.StaticVariable;

import lombok.extern.slf4j.Slf4j;
@SuppressWarnings("all")
@Component
@Transactional
@Slf4j
public class UserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

    public List<User> getAll(){
        String sql=" select user_id, user_name, user_pass, sex, token, birthday, create_time, update_time, is_del FROM t_user where is_del = 0 ORDER BY create_time DESC ";
        List<User> result=jdbcTemplate.query(sql, (resultSet, i) -> {
        	User user = new User();
        	user.setUser_name(resultSet.getString("user_name"));
        	user.setUser_pass(resultSet.getString("user_pass"));
        	user.setToken(resultSet.getString("token"));
        	user.setSex(resultSet.getInt("sex"));
        	user.setBirthday(resultSet.getDate("birthday"));
        	user.setIs_del(0);
        	user.setCreate_time(resultSet.getDate("create_time"));
        	user.setUpdate_time(resultSet.getDate("update_time"));
            return user;
        });
        return result;
    }
    
    public int add(User user) {
        String sql = " INSERT INTO t_user(user_name, user_pass, birthday, sex, token, create_time, update_time, is_del)VALUES(?,?,?,?,?,?,?,?) ";
        return jdbcTemplate.update(sql, user.getUser_name(), user.getUser_pass(), user.getBirthday(), user.getSex(), user.getToken(), user.getCreate_time(), user.getUpdate_time(), user.getIs_del());
    }
 
    public int update(User user) {
        String sql = " UPDATE t_user SET user_name=?, sex=?, token=?, birthday=?, update_time=? WHERE user_id=? AND is_del = 0 ";
        return jdbcTemplate.update(sql, user.getUser_name(), user.getSex(), user.getToken(), user.getBirthday(), user.getUpdate_time(), user.getUser_id());
    }
 
    public int delete(int id) {
        String sql = "UPDATE t_user SET is_del = 0 WHERE user_id=? ";
        return jdbcTemplate.update(sql, id);
    }
 
    public int count(){
        String sql="SELECT COUNT(0) FROM t_user WHERE is_del = 0 ";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }
 
    public User getById(int id) {
        String sql = " select user_id, user_name, user_pass, sex, token, birthday, create_time, update_time, is_del FROM t_user where is_del = 0 AND  user_id=? ";
        //List<Object[]> objects = new ArrayList<Object[]>();
        //jdbcTemplate.batchUpdate(sql, objects);
        
        return jdbcTemplate.queryForObject(sql, (ResultSet rs, int rowNumber) -> {
        	User user = new User();
        	user.setUser_name(rs.getString("user_name"));
        	user.setUser_pass(rs.getString("user_pass"));
        	user.setToken(rs.getString("token"));
        	user.setSex(rs.getInt("sex"));
        	user.setBirthday(rs.getDate("birthday"));
        	user.setIs_del(0);
        	user.setCreate_time(rs.getDate("create_time"));
        	user.setUpdate_time(rs.getDate("update_time"));
            return user;
        }, id);
    }
    
    public int[] batchInsert(List<User> list) {
    	String sql = " INSERT INTO t_user(user_name, user_pass, birthday, sex, token, create_time, update_time, is_del)VALUES(?,?,?,?,?,now(),now(),0) ";
    	return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, list.get(i).getUser_name());
				ps.setString(2, list.get(i).getUser_pass());
				ps.setString(3, CommonUtils.dateFormat(list.get(i).getBirthday(), "yyyy-MM-dd"));
				ps.setInt(4, list.get(i).getSex());
				ps.setString(5, list.get(i).getToken());
				/*Timestamp createTimeTimestamp = new Timestamp(list.get(i).getCreate_time().getTime());
				ps.setTimestamp(6, createTimeTimestamp);
				Timestamp updateTimeTimestamp = new Timestamp(list.get(i).getUpdate_time().getTime());
				ps.setTimestamp(7, updateTimeTimestamp);
				ps.setInt(8, list.get(i).getIs_del());*/
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
    }
    
    public void batchUpdate() {
    	String sql = "";
    	//jdbcTemplate.batchUpdate(sql, batchArgs)
    } 
    
    /***
     * 添加用户 返回主键
     * @param user
     * @return
     */
    public int insertReturnPrimaryKey(User user) {
        String sql = " INSERT INTO t_user(user_name, user_pass, birthday, sex, token, create_time, update_time, is_del)VALUES(?,?,?,?,?,now(),now(),0) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps  = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setObject(1, user.getUser_name());
	            ps.setObject(2, user.getUser_pass());
	            ps.setObject(3, user.getBirthday());
	            ps.setObject(4, user.getSex());
	            ps.setObject(5, user.getToken());
	            return ps;
			}
		}, keyHolder);
        
    	return keyHolder.getKey().intValue();
    } 
    
    public int[] batchAdd(List<User> list) {
    	String sql = " INSERT INTO t_user(user_name, user_pass, birthday, sex, token, create_time, update_time, is_del)VALUES(?,?,?,?,?,now(),now(),0) ";
    	List<Object[]> params = new ArrayList<Object[]>();
    	for (User user : list) {
			params.add(new Object[] {user.getUser_name(), user.getUser_pass(), user.getBirthday(), user.getSex(), user.getToken()});
		}
    	return jdbcTemplate.batchUpdate(sql, params);
    } 
    
    public Map<String, Object> getUserInfo(Integer id) {
    	final String callProcedureSql = "{call pro_test(?, ?)}";
    	List<SqlParameter> params = new ArrayList<SqlParameter>();  
	    params.add(new SqlInOutParameter("p_id", Types.INTEGER));  
    	params.add(new SqlOutParameter("p_name", Types.VARCHAR));  
    	Map<String, Object> callMap = jdbcTemplate.call( new CallableStatementCreator() {
			
			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement cstmt = con.prepareCall(callProcedureSql);  
				cstmt.registerOutParameter(1, Types.INTEGER);  
				cstmt.registerOutParameter(2, Types.VARCHAR);  
				cstmt.setObject(1, id);//setString(1, id);  
				return cstmt;
			}
		}, params);
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	String p_name = (String) callMap.get("p_name");
    	Integer p_id = (Integer) callMap.get("p_id");
    	log.info("name >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> :" + p_name);
    	log.info("id >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> :" + p_id);
    	returnMap.put("id", p_id);
    	returnMap.put("name", p_name);
    	return returnMap;
    } 
    
    public Map<String, Object> callRecharge(String p_card_no, String p_draw_money){
    	final String callProcedureSql = "{call pro_recharge(?, ?, ?, ?)}";
    	List<SqlParameter> params = new ArrayList<SqlParameter>();  
	    params.add(new SqlInOutParameter("p_card_no", Types.VARCHAR));  
    	params.add(new SqlOutParameter("p_result", Types.INTEGER));  
    	params.add(new SqlOutParameter("p_msg", Types.VARCHAR));
    	params.add(new SqlInOutParameter("p_draw_money", Types.VARCHAR));
    	Map<String, Object> callMap = jdbcTemplate.call( new CallableStatementCreator() {
			
			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement cstmt = con.prepareCall(callProcedureSql);  
				cstmt.registerOutParameter(1, Types.VARCHAR); 
				cstmt.registerOutParameter(2, Types.INTEGER);
				cstmt.registerOutParameter(3, Types.VARCHAR);
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.setObject(1, p_card_no);
				cstmt.setObject(4, p_draw_money);
				return cstmt;
			}
		}, params);
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	String card_no = (String) callMap.get("p_card_no");
    	Integer p_result = (Integer) callMap.get("p_result");
    	String p_msg = (String) callMap.get("p_msg");
    	String draw_money = (String) callMap.get("p_draw_money");
    	log.info("p_card_no >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> :" + card_no);
    	log.info("p_result >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> :" + p_result);
    	log.info("draw_money >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> :" + draw_money);
    	log.info("p_msg >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> :" + p_msg);
    	returnMap.put("p_card_no", card_no);
    	returnMap.put("p_result", p_result);
    	returnMap.put("draw_money", draw_money);
    	returnMap.put("p_msg", p_msg);
    	return returnMap;
    }
    
    
    public void queryUserRecord() {
    	String sql = " SELECT acc.address, acc.user_id, acc.card_no, acc.balance, acc.deposit_bank, cr.money, cr.type, cr.trace_no, cr.trace_time, cr.cr_id FROM account acc INNER JOIN  charge_record cr ON cr.user_id = acc.user_id AND cr.is_del = 0\r\n" + 
    			"WHERE acc.is_del = 0 AND acc.user_id = 1  ";
    	List<Map<String, Object>> result=jdbcTemplate.query(sql, (resultSet, i) -> {
        	Map<String, Object> user = new HashMap<String, Object>();
        	user.put("address", resultSet.getString("address"));
        	user.put("user_id", resultSet.getString("user_id"));
        	user.put("card_no", resultSet.getString("card_no"));
        	user.put("balance", resultSet.getString("balance"));
        	user.put("deposit_bank", resultSet.getString("deposit_bank"));
        	user.put("money", resultSet.getString("money"));
        	user.put("type", resultSet.getInt("type"));
        	user.put("trace_no", resultSet.getString("trace_no"));
        	user.put("balance", resultSet.getString("balance"));
        	user.put("trace_time", CommonUtils.dateFormat((Timestamp) resultSet.getObject("trace_time"), null));
        	user.put("cr_id", resultSet.getObject("cr_id"));
            return user;
        });
    	String jsonString = JSONObject.toJSONString(result);
    	System.out.println("用户的交易记录 ：" + jsonString);
    } 
    
    public List<User> getUserListByName(String userName){
        String sql=" select user_id, user_name, user_pass, sex, token, birthday, create_time, update_time, is_del FROM t_user where is_del = 0 AND user_name = ? ";
        List<User> result=jdbcTemplate.query(sql, (resultSet, i) -> {
        	User user = new User();
        	user.setUser_id(resultSet.getInt("user_id"));
        	user.setUser_name(resultSet.getString("user_name"));
        	user.setUser_pass(resultSet.getString("user_pass"));
        	user.setToken(resultSet.getString("token"));
        	user.setSex(resultSet.getInt("sex"));
        	user.setBirthday(resultSet.getDate("birthday"));
        	user.setIs_del(0);
        	user.setCreate_time(resultSet.getDate("create_time"));
        	user.setUpdate_time(resultSet.getDate("update_time"));
            return user;
        }, userName);
        return result;
    }
    
    @SuppressWarnings("deprecation")
	public Map<String, Object> login (String userName, String userPass) throws Exception {
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	MD5 md5 = new MD5();
    	String digestUserPass = md5.digest(userPass, "MD5");
    	//String sql = " SELECT * FROM t_user u where u.user_name = ? AND u.user_pass = ? AND u.is_del = 0; ";
    	List<User> userList = this.getUserListByName(userName);
    	/***
    	 * 获取初始化设置
    	 */
    	log.info("项目名称 ：" + StaticVariable.projectName + "; 包名 ：" + StaticVariable.packageName + "; 配置名 ：" + StaticVariable.settingName); 
    	if (userList != null && userList.size() > 0) {
    		for (User user : userList) {
    			System.out.println("登录用户名为 ："+ userName +"； 数据库查询对比名称 ：" + user.getUser_name());
    			if (userName.equals(user.getUser_name())) {
    				if (digestUserPass.equals(user.getUser_pass())) {
    					//登录
    					String token = user.getUser_id()+ "_" +CommonUtils.getRandomStr(32, 1);
    					user.setToken(token);
    					System.out.println("token >>>>>>>>>>>>>>>>>>>>>>>>>> :" + user.getToken());
    					System.out.println(user.getUser_name() + "____" + user.getSex() + "____" + user.getBirthday() + "____" + user.getUpdate_time() + "____" + user.getUser_id());
    					this.update(user);
    					returnMap.put("code", 200);
    					returnMap.put("success", "true");
        				returnMap.put("msg", "success");
        				returnMap.put("token", token);
    				} else {
    					returnMap.put("success", "false");
    					returnMap.put("code", 400);
    					returnMap.put("msg", "密码错误，请重新输入；");
    				}
				} else {
					returnMap.put("success", "false");
					returnMap.put("code", 400);
					returnMap.put("msg", "不存在该用户；");
				}

    		}
		}
    	return returnMap;
    }
    
    
    public Map<String, Object> register (String userName, String userPass, Integer sex, String birthday) throws Exception {
    	//校验注册用户是否已存在，因为设计身份证号码或电话号码唯一标识，现在用名称做校验
    	List<User> userListByName = this.getUserListByName(userName);
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	if (userListByName != null || userListByName.size() > 0) {
        	returnMap.put("code", 400);
        	returnMap.put("success", "false");
    		returnMap.put("msg", "该用户已注册，请登录；");
        	return returnMap;
    	} else {
        	MD5 md5 = new MD5();
        	String digestUserPass = md5.digest(userPass, "MD5");
        	User user = new User();
        	Date birthdayDate = CommonUtils.stringToDate(birthday, null);
        	user.setUser_name(userName);
        	user.setUser_pass(digestUserPass);
        	user.setSex(sex);
        	user.setBirthday(birthdayDate);
        	user.setToken(null);
        	int insertReturnPrimaryKey = this.insertReturnPrimaryKey(user);
        	log.info("注册的用户的id ：" + insertReturnPrimaryKey);
        	returnMap.put("code", 200);
    		returnMap.put("msg", "success");
        	return returnMap;
    	}
    	
    }
    
    /***
     * 
     * @param page 页数
     * @param pageNum 条目数
     * @return
     */
    public List<User> getUserPage(Integer page, Integer pageNum){
    	String sql = "SELECT * FROM t_user u WHERE \r\n" + 
    			"u.is_del = 0\r\n" + 
    			"limit ?,?";
        List<User> result=jdbcTemplate.query(sql, (resultSet, i) -> {
        	User user = new User();
        	user.setUser_id(resultSet.getInt("user_id"));
        	user.setUser_name(resultSet.getString("user_name"));
        	user.setUser_pass(resultSet.getString("user_pass"));
        	user.setToken(resultSet.getString("token"));
        	user.setSex(resultSet.getInt("sex"));
        	user.setBirthday(resultSet.getDate("birthday"));
        	user.setIs_del(0);
        	user.setCreate_time(resultSet.getTimestamp("create_time"));
        	user.setUpdate_time(resultSet.getTimestamp("update_time"));
            return user;
        }, (page-1)*pageNum, pageNum);

        return result;
    }
    
    public User getUser(String id){
        String sql = "SELECT * FROM t_user u ";
        sql += " WHERE u.user_id= '"+id+"'";
        return this.jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class));
    }
    
    public List<User> queryUserPageList(String name, Integer page, Integer pageSize) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sb = new StringBuffer(" SELECT * FROM t_user u WHERE u.is_del = 0 ");
        if (StringUtils.isNotBlank(name)) {
        	name = "%" + name + "%";
        	sb.append(" AND u.user_name like ? ");        	
        	params.add(name);
        }
        sb.append(" limit ?,? ");
        params.add((page-1)*pageSize);
        params.add(page*pageSize);
        List<User> list = jdbcTemplate.query(sb.toString(), params.toArray(), new BeanPropertyRowMapper(User.class));
        return list;
    }
    
    /***
     * 
     * @param name 名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 页数
     * @param pageSize 每页条数
     * @return
     */
    public Map<String, Object>  getUserMapList(String name, String startTime, String endTime, Integer page, Integer pageSize) {
    	
    	List<Object> params = new ArrayList<Object>();
    	List<Object> paramscount = new ArrayList<Object>();
    	StringBuffer sql = new StringBuffer(" SELECT * FROM t_user u WHERE u.is_del = 0 ");
    	StringBuffer sqlcount = new StringBuffer(" SELECT COUNT(*) num FROM t_user u WHERE u.is_del = 0 ");
    	if (StringUtils.isNotBlank(startTime)) {
    		sql.append(" AND u.birthday >= ? ");
    		params.add(startTime);
    		sqlcount.append(" AND u.birthday >= ? ");
    		paramscount.add(startTime);
    	}
    	if (StringUtils.isNotBlank(endTime)) {
    		sql.append(" AND u.birthday <= ? ");
    		params.add(endTime);
    		sqlcount.append(" AND u.birthday <= ? ");
    		paramscount.add(endTime);
    	}
    	if (StringUtils.isNotBlank(name)) {
    		name = "%" +name+ "%";
    		sql.append(" AND u.user_name like ? ");
    		params.add(name);
    		sqlcount.append(" AND u.user_name like ? ");
    		paramscount.add(name);
    	}
    	sql.append(" limit ?,? ");
        params.add((page-1)*pageSize);
        params.add(page*pageSize);
        // 计算总页数、总条目数
        Integer count = 0;//总条目数
        Integer totalPage = 0;//总页数
        List<User> userMapList = null;
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
        	userMapList = jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper(User.class));
        	if (paramscount == null || paramscount.size() > 0) {
        		count = jdbcTemplate.queryForObject(sqlcount.toString(), paramscount.toArray(), Integer.class);
        	} else {
        		count = jdbcTemplate.queryForObject(sqlcount.toString(), Integer.class);
        	}
        	if (count%pageSize == 0) {
        		totalPage = count/pageSize;
        	} else {
        		totalPage = count/pageSize + 1;
        	}
        	returnMap.put("code", 200);
        	returnMap.put("msg", "success");
        	returnMap.put("data", userMapList);
        	returnMap.put("count", count);
        	returnMap.put("totalPage", totalPage);
        	returnMap.put("curPage", page);
        	returnMap.put("pageSize", pageSize);
		} catch (Exception e) {
			log.info("getUserMapList ex :" + e);
			e.printStackTrace();
			returnMap.put("code", 400);
        	returnMap.put("msg", "exception");
        	returnMap.put("data", null);
        	returnMap.put("count", 0);
        	returnMap.put("totalPage", 0);
		}
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql.toString(), params.toArray());
		System.out.println("List<Map<String, Object>> queryForList >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> :" + JSONObject.toJSONString(queryForList));
		for (Map<String, Object> map : queryForList) {
			String object = (String) map.get("user_name");
			Timestamp object2 = (Timestamp) map.get("birthday");
			log.info("----------------------- :" + object + "——————————————————————— ：" + CommonUtils.dateFormat(object2, "yyyy-MM-dd"));
		}
		
		
		return returnMap;
    } 
}
