package com.shy.springboot.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.Statement;
import com.shy.springboot.entity.User;
import com.shy.springboot.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
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
        String sql = " UPDATE t_user SET user_name=?, sex=?, birthday=?, update_time=? WHERE user_id=? AND is_del = 0 ";
        return jdbcTemplate.update(sql, user.getUser_name(), user.getSex(), user.getBirthday(), user.getUpdate_time(), user.getUser_id());
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
        List<Object[]> objects = new ArrayList<Object[]>();
        jdbcTemplate.batchUpdate(sql, objects);
        
        
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
}
