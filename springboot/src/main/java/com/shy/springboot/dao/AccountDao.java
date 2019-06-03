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
import com.shy.springboot.entity.Account;
import com.shy.springboot.entity.User;
import com.shy.springboot.utils.CommonUtils;
import com.shy.springboot.utils.StaticVariable;
import lombok.extern.slf4j.Slf4j;
@SuppressWarnings("all")
@Component
@Transactional
@Slf4j
public class AccountDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/***
	 * 
	 * @param depositBank
	 * @param cardNo
	 * @param page
	 * @param pageSize
	 * @return
	 */
    public Map<String, Object>  getAccountMapList(String depositBank, String cardNo, Integer page, Integer pageSize) {
    	
    	List<Object> params = new ArrayList<Object>();
    	List<Object> paramscount = new ArrayList<Object>();
    	StringBuffer sql = new StringBuffer(" SELECT * FROM account a WHERE a.is_del = 0 ");
    	StringBuffer sqlcount = new StringBuffer(" SELECT COUNT(*) num FROM account a WHERE a.is_del = 0 ");
    	if (StringUtils.isNotBlank(depositBank)) {
    		depositBank = "%" +depositBank+ "%";
    		sql.append(" AND a.deposit_bank like ? ");
    		params.add(depositBank);
    		sqlcount.append(" AND a.deposit_bank like ? ");
    		paramscount.add(depositBank);
    	}
    	if (StringUtils.isNotBlank(cardNo)) {
    		sql.append(" AND a.card_no = ? ");
    		params.add(cardNo);
    		sqlcount.append(" AND a.card_no = ? ");
    		paramscount.add(cardNo);
    	}
    	sql.append(" limit ?,? ");
        params.add((page-1)*pageSize);
        params.add(page*pageSize);
        // 计算总页数、总条目数
        Integer count = 0;//总条目数
        Integer totalPage = 0;//总页数
        List<User> accountMapList = null;
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
        	accountMapList = jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper(Account.class));
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
        	returnMap.put("data", accountMapList);
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
		/*List<Account> queryForList = jdbcTemplate.queryForList(sql.toString(), Account.class, params.toArray());
		System.out.println("List<Map<String, Object>> queryForList >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> :" + JSONObject.toJSONString(queryForList));
		for (Map<String, Object> map : queryForList) {
			String object = (String) map.get("user_name");
			Timestamp object2 = (Timestamp) map.get("birthday");
			log.info("----------------------- :" + object + "——————————————————————— ：" + CommonUtils.dateFormat(object2, "yyyy-MM-dd"));
		}		
		*/		
		return returnMap;
    } 
    
    public Account getAccountById(Integer id) {
    	List<Object> params = new ArrayList<Object>();
    	params.add(id);
    	String sql = " SELECT * FROM account a WHERE a.is_del = 0 AND a.acc_id = ? ";
    	List<Account> list = jdbcTemplate.query(sql, params.toArray(), new BeanPropertyRowMapper(Account.class));
    	Account account = null;
    	if (list != null && list.size() > 0) {
    		account = list.get(0);
    	} 
    	return account;
    }
    
    /*public int add(User user) {
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
				Timestamp createTimeTimestamp = new Timestamp(list.get(i).getCreate_time().getTime());
				ps.setTimestamp(6, createTimeTimestamp);
				Timestamp updateTimeTimestamp = new Timestamp(list.get(i).getUpdate_time().getTime());
				ps.setTimestamp(7, updateTimeTimestamp);
				ps.setInt(8, list.get(i).getIs_del());
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
    }

    *//***
     * 添加用户 返回主键
     * @param user
     * @return
     *//*
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
    }*/ 
    
 
}
