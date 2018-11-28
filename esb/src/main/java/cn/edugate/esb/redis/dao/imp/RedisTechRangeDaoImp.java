package cn.edugate.esb.redis.dao.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.comparator.MapToSortGroupComparator;
import cn.edugate.esb.eduEnum.EnumRoleLabel;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Course;
import cn.edugate.esb.entity.Department;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Group;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisClassesDao;
import cn.edugate.esb.redis.dao.RedisCourseDao;
import cn.edugate.esb.redis.dao.RedisDepartmentDao;
import cn.edugate.esb.redis.dao.RedisGradeDao;
import cn.edugate.esb.redis.dao.RedisGroupDao;
import cn.edugate.esb.redis.dao.RedisGroupMemberDao;
import cn.edugate.esb.redis.dao.RedisOrgUserDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.redis.dao.RedisTechRangeDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;

@Repository
public class RedisTechRangeDaoImp extends RedisGeneratorDao<String, String> implements RedisTechRangeDao {
	
	@Autowired
	private RedisGroupDao redisGroupDao;
	@Autowired
	private RedisCourseDao redisCourseDao;
	@Autowired
	private RedisTeacherDao redisTeacherDao;
	@Autowired
	private RedisGradeDao redisGradeDao;
	@Autowired
	private RedisClassesDao redisClassesDao;
	@Autowired
	private RedisDepartmentDao redisDepartmentDao;
	@Autowired
	private RedisGroupMemberDao redisGroupMemberDao;
	@Autowired
	private RedisOrgUserDao redisOrgUserDao;
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Override
	public boolean add(final TechRange tr) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                if(Constant.IS_DEL_NO==tr.getIs_del()){
            		String json="";
	            	json = util.getJSONFromPOJO(tr);
	            	byte[] value = serializer.serialize(json); 
	            	
	            	//教师下  身份集合
	        		byte[] key  = serializer.serialize("techrange:"+tr.getTech_id());  
	        		byte[] field  = serializer.serialize(tr.getRl_id().toString()+":"+tr.getTr_id().toString()); 
	        		connection.hSet(key,field, value);
	            	
	        		//教师+身份 下数据集合
	        		byte[] keyrl  = serializer.serialize("techrange_rl:"+tr.getTech_id()+":"+tr.getRl_id().toString());  
	        		byte[] fieldrl  = serializer.serialize(tr.getTr_id().toString()); 
	        		connection.hSet(keyrl,fieldrl, value);
	        		
	        		if(tr.getRl_id()==EnumRoleLabel.任课教师.getCode() ){
	        			Map<String,Object> map = new HashMap<String,Object>();
	        			map.put("tech_id", tr.getTech_id());
	        			map.put("clas_id", tr.getClas_id());
	        			map.put("grade_id", tr.getGrade_id());
	        			map.put("cour_id", tr.getCour_id());
	        			map.put("history", tr.getHistory());
	        			map.put("insert_time", tr.getInsert_time());
	        			String sk_json = util.getJSONFromPOJO(map);
	        			byte[] sk_value = serializer.serialize(sk_json); 
	        			
	        			//班级下的 授课教师   
    	        		byte[] keyclas  = serializer.serialize("sk_clas_tech:"+tr.getClas_id());  
    	        		byte[] fieltech  = serializer.serialize(tr.getTech_id()+":"+tr.getCour_id()); 
    	        		connection.hSet(keyclas, fieltech,sk_value);
    	        		
    	        		// 教师授课班级
    	        		byte[] keytech  = serializer.serialize("sk_tech_clas:"+tr.getTech_id());  
    	        		byte[] fielclas  = serializer.serialize(tr.getClas_id()+":"+tr.getCour_id()); 
    	        		connection.hSet(keytech,fielclas, sk_value);
    	        		
    	        		// 年级下 课程
    	        		byte[] keygrade  = serializer.serialize("sk_grade_thcour:"+tr.getGrade_id());  
    	        		byte[] fielcour  = serializer.serialize(tr.getTech_id()+":"+tr.getCour_id()); 
    	        		connection.hSet(keygrade,fielcour, sk_value);
    	        		
    	        		// 年级课程 下班级
    	        		byte[] keygrade2  = serializer.serialize("sk_gradecour_clas:"+tr.getGrade_id()+":"+tr.getCour_id());  
    	        		byte[] fielcour2  = serializer.serialize(tr.getTr_id().toString()); 
    	        		connection.hSet(keygrade2,fielcour2, sk_value);
    	        		
    	        		// 年级课程教师 下班级
    	        		byte[] keygrade6  = serializer.serialize("sk_gradecourtech_clas:"+tr.getGrade_id()+":"+tr.getCour_id()+":"+tr.getTech_id());  
    	        		byte[] fielcour6  = serializer.serialize(tr.getTr_id().toString()); 
    	        		connection.hSet(keygrade6,fielcour6, sk_value);
    	        		
    	        		//年级下 老师
    	        		byte[] keygt  = serializer.serialize("sk_grade_tech:"+tr.getGrade_id());  
    	        		byte[] fielgt  = serializer.serialize(tr.getTech_id()+":"+tr.getClas_id()+":"+tr.getCour_id()); 
    	        		byte[] valueT = serializer.serialize(tr.getTech_id().toString()); 
    	        		connection.hSet(keygt,fielgt, valueT);
	        		
	        		}else if(tr.getRl_id()==EnumRoleLabel.班主任.getCode()){
	        			//班级下的 班主任   
    	        		byte[] keyclasbzr  = serializer.serialize("sk_clas_bzr:"+tr.getClas_id());  
    	        		byte[] valuebzr = serializer.serialize(tr.getTech_id().toString()); 
    	        		byte[] valueT = serializer.serialize(tr.getTech_id().toString()); 
    	        		connection.hSet(keyclasbzr,valuebzr, valueT);
	        		}else if(tr.getRl_id()==EnumRoleLabel.学生组管理员.getCode()){
	        			//学生组下的 管理员   
    	        		byte[] keyclasman  = serializer.serialize("group_stud_manager:"+tr.getGroup_id());  
    	        		byte[] fieldclasman  = serializer.serialize(tr.getTech_id().toString()); 
    	        		byte[] valuebzr = serializer.serialize(tr.getTech_id().toString()); 
    	        		connection.hSet(keyclasman, fieldclasman,valuebzr); 
	        		}else if(tr.getRl_id()==EnumRoleLabel.部门管理员.getCode()){
	        			//部门下的 管理员   
    	        		byte[] keyclasbzr  = serializer.serialize("depinfo_manager:"+tr.getDep_id());  
    	        		byte[] fieldclasman  = serializer.serialize(tr.getTech_id().toString()); 
    	        		byte[] valuebzr = serializer.serialize(tr.getTech_id().toString()); 
    	        		connection.hSet(keyclasbzr, fieldclasman,valuebzr); 
	        		}else if(tr.getRl_id()==EnumRoleLabel.教师组管理员.getCode()){
	        			//教师组 管理员   
    	        		byte[] keyclasbzr  = serializer.serialize("group_tech_manager:"+tr.getGroup_id());  
    	        		byte[] fieldclasman  = serializer.serialize(tr.getTech_id().toString()); 
    	        		byte[] valuebzr = serializer.serialize(tr.getTech_id().toString()); 
    	        		connection.hSet(keyclasbzr, fieldclasman,valuebzr); 
	        		}else if(tr.getRl_id()==EnumRoleLabel.年级组长.getCode()){
	        			//年级xia 组长    
    	        		byte[] keyclasbzr  = serializer.serialize("grade_tech_manager:"+tr.getGrade_id());  
    	        		byte[] fieldclasman  = serializer.serialize(tr.getTech_id().toString()); 
    	        		byte[] valuebzr = serializer.serialize(tr.getTech_id().toString()); 
    	        		connection.hSet(keyclasbzr, fieldclasman,valuebzr); 
	        		}
            	}      
                return true;  
            }
        }, false, true);  
        return result;  
	}
	
	@Override
	public boolean add(final List<TechRange> trs) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                for (TechRange tr : trs) { 
                	//if(Constant.IS_DEL_NO==tr.getIs_del()){
                		String json="";
    	            	json = util.getJSONFromPOJO(tr);
    	            	byte[] value = serializer.serialize(json); 
    	            	
    	            	//教师下  身份集合
    	        		byte[] key  = serializer.serialize("techrange:"+tr.getTech_id());  
    	        		byte[] field  = serializer.serialize(tr.getRl_id().toString()+":"+tr.getTr_id().toString()); 
    	        		connection.hSet(key,field, value);
    	            	
    	        		//教师+身份 下数据集合
    	        		byte[] keyrl  = serializer.serialize("techrange_rl:"+tr.getTech_id()+":"+tr.getRl_id().toString());  
    	        		byte[] fieldrl  = serializer.serialize(tr.getTr_id().toString()); 
    	        		connection.hSet(keyrl,fieldrl, value);
    	        		
    	        		if(tr.getRl_id()==EnumRoleLabel.任课教师.getCode() ){
    	        			Map<String,Object> map = new HashMap<String,Object>();
    	        			map.put("tech_id", tr.getTech_id());
    	        			map.put("clas_id", tr.getClas_id());
    	        			map.put("grade_id", tr.getGrade_id());
    	        			map.put("cour_id", tr.getCour_id());
    	        			map.put("history", tr.getHistory());
    	        			map.put("insert_time", tr.getInsert_time());
    	        			String sk_json = util.getJSONFromPOJO(map);
    	        			byte[] sk_value = serializer.serialize(sk_json); 
    	        			
    	        			//班级下的 授课教师   
	    	        		byte[] keyclas  = serializer.serialize("sk_clas_tech:"+tr.getClas_id());  
	    	        		byte[] fieltech  = serializer.serialize(tr.getTech_id()+":"+tr.getCour_id()); 
	    	        		connection.hSet(keyclas, fieltech,sk_value);
	    	        		
	    	        		// 教师授课班级
	    	        		byte[] keytech  = serializer.serialize("sk_tech_clas:"+tr.getTech_id());  
	    	        		byte[] fielclas  = serializer.serialize(tr.getClas_id()+":"+tr.getCour_id()); 
	    	        		connection.hSet(keytech,fielclas, sk_value);
	    	        		
	    	        		// 年级下 课程
	    	        		byte[] keygrade  = serializer.serialize("sk_grade_thcour:"+tr.getGrade_id());  
	    	        		byte[] fielcour  = serializer.serialize(tr.getTech_id()+":"+tr.getCour_id()); 
	    	        		connection.hSet(keygrade,fielcour, sk_value);
	    	        		
	    	        		// 年级课程 下班级
	    	        		byte[] keygrade2  = serializer.serialize("sk_gradecour_clas:"+tr.getGrade_id()+":"+tr.getCour_id());  
	    	        		byte[] fielcour2  = serializer.serialize(tr.getTr_id().toString()); 
	    	        		connection.hSet(keygrade2,fielcour2, sk_value);
	    	        		
	    	        		// 年级课程教师 下班级
	    	        		byte[] keygrade6  = serializer.serialize("sk_gradecourtech_clas:"+tr.getGrade_id()+":"+tr.getCour_id()+":"+tr.getTech_id());  
	    	        		byte[] fielcour6  = serializer.serialize(tr.getTr_id().toString()); 
	    	        		connection.hSet(keygrade6,fielcour6, sk_value);
	    	        		
	    	        		//年级下 老师
	    	        		byte[] keygt  = serializer.serialize("sk_grade_tech:"+tr.getGrade_id());  
	    	        		byte[] fielgt  = serializer.serialize(tr.getTech_id()+":"+tr.getClas_id()+":"+tr.getCour_id()); 
	    	        		byte[] valueT = serializer.serialize(tr.getTech_id().toString()); 
	    	        		connection.hSet(keygt,fielgt, valueT);
    	        		
    	        		}else if(tr.getRl_id()==EnumRoleLabel.班主任.getCode()){
    	        			//班级下的 班主任   
	    	        		byte[] keyclasbzr  = serializer.serialize("sk_clas_bzr:"+tr.getClas_id());  
	    	        		byte[] valuebzr = serializer.serialize(tr.getTech_id().toString()); 
	    	        		byte[] valueT = serializer.serialize(tr.getTech_id().toString()); 
	    	        		connection.hSet(keyclasbzr,valuebzr, valueT);
    	        		}else if(tr.getRl_id()==EnumRoleLabel.学生组管理员.getCode()){
    	        			//学生组下的 管理员   
	    	        		byte[] keyclasman  = serializer.serialize("group_stud_manager:"+tr.getGroup_id());  
	    	        		byte[] fieldclasman  = serializer.serialize(tr.getTech_id().toString()); 
	    	        		byte[] valuebzr = serializer.serialize(tr.getTech_id().toString()); 
	    	        		connection.hSet(keyclasman, fieldclasman,valuebzr); 
    	        		}else if(tr.getRl_id()==EnumRoleLabel.部门管理员.getCode()){
    	        			//部门下的 管理员   
	    	        		byte[] keyclasbzr  = serializer.serialize("depinfo_manager:"+tr.getDep_id());  
	    	        		byte[] fieldclasman  = serializer.serialize(tr.getTech_id().toString()); 
	    	        		byte[] valuebzr = serializer.serialize(tr.getTech_id().toString()); 
	    	        		connection.hSet(keyclasbzr, fieldclasman,valuebzr); 
    	        		}else if(tr.getRl_id()==EnumRoleLabel.教师组管理员.getCode()){
    	        			//教师组 管理员   
	    	        		byte[] keyclasbzr  = serializer.serialize("group_tech_manager:"+tr.getGroup_id());  
	    	        		byte[] fieldclasman  = serializer.serialize(tr.getTech_id().toString()); 
	    	        		byte[] valuebzr = serializer.serialize(tr.getTech_id().toString()); 
	    	        		connection.hSet(keyclasbzr, fieldclasman,valuebzr); 
    	        		}else if(tr.getRl_id()==EnumRoleLabel.年级组长.getCode()){
    	        			//年级xia 组长    
	    	        		byte[] keyclasbzr  = serializer.serialize("grade_tech_manager:"+tr.getGrade_id());  
	    	        		byte[] fieldclasman  = serializer.serialize(tr.getTech_id().toString()); 
	    	        		byte[] valuebzr = serializer.serialize(tr.getTech_id().toString()); 
	    	        		connection.hSet(keyclasbzr, fieldclasman,valuebzr); 
    	        		}
                	//}
                }                  
                return true;  
            }
        }, false, true);  
        return result;  
	}	

	@Override
	public boolean delete(final TechRange tr) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        		byte[] key  = serializer.serialize("techrange:"+tr.getTech_id());
        		byte[] field  = serializer.serialize(tr.getRl_id().toString()+":"+tr.getTr_id().toString()); 
            	connection.hDel(key,field);
            	
            	byte[] keyrl  = serializer.serialize("techrange_rl:"+tr.getTech_id()+":"+tr.getRl_id().toString());  
        		byte[] fieldrl  = serializer.serialize(tr.getTr_id().toString()); 
            	connection.hDel(keyrl,fieldrl);
            	
            	if(tr.getRl_id()==EnumRoleLabel.任课教师.getCode() ){
	            	byte[] skkey1  = serializer.serialize("sk_clas_tech:"+tr.getClas_id());  
	        		byte[] skfield1  = serializer.serialize(tr.getTech_id()+":"+tr.getCour_id()); 
	            	connection.hDel(skkey1,skfield1);
	            	
	            	byte[] skkey2  = serializer.serialize("sk_tech_clas:"+tr.getTech_id());  
	        		byte[] skfield2  = serializer.serialize(tr.getClas_id()+":"+tr.getCour_id()); 
	            	connection.hDel(skkey2,skfield2);
	            	
	            	byte[] skkey3  = serializer.serialize("sk_grade_thcour:"+tr.getGrade_id());  
	        		byte[] skfield3  = serializer.serialize(tr.getTech_id()+":"+tr.getCour_id()); 
	            	connection.hDel(skkey3,skfield3);
	            	
	            	// 年级课程 下班级
	        		byte[] keygrade2  = serializer.serialize("sk_gradecour_clas:"+tr.getGrade_id()+":"+tr.getCour_id());  
	        		byte[] fielcour2  = serializer.serialize(tr.getTr_id().toString()); 
	        		connection.hDel(keygrade2,fielcour2);
	        		
	        		// 年级课程教师 下班级
	        		byte[] keygrade6  = serializer.serialize("sk_gradecourtech_clas:"+tr.getGrade_id()+":"+tr.getCour_id()+":"+tr.getTech_id());  
	        		byte[] fielcour6  = serializer.serialize(tr.getTr_id().toString()); 
	        		connection.hDel(keygrade6,fielcour6);
	            	
	            	byte[] skkey4  = serializer.serialize("sk_grade_tech:"+tr.getGrade_id());  
	        		byte[] skfield4  = serializer.serialize(tr.getTech_id()+":"+tr.getClas_id()+":"+tr.getCour_id()); 
	            	connection.hDel(skkey4,skfield4);
            	
            	}else if(tr.getRl_id()==EnumRoleLabel.班主任.getCode()){
        			//班级下的 班主任   
	        		byte[] keyclasbzr  = serializer.serialize("sk_clas_bzr:"+tr.getClas_id());  
	        		byte[] valuebzr = serializer.serialize(tr.getTech_id().toString()); 
	        		connection.hDel(keyclasbzr, valuebzr); 
        		}else if(tr.getRl_id()==EnumRoleLabel.学生组管理员.getCode()){
        			//学生组下的 管理员   
	        		byte[] keyclasman  = serializer.serialize("group_stud_manager:"+tr.getGroup_id());  
	        		byte[] fieldclasman  = serializer.serialize(tr.getTech_id().toString()); 
	        		connection.hDel(keyclasman,fieldclasman);
        		}else if(tr.getRl_id()==EnumRoleLabel.部门管理员.getCode()){
        			//部门下的 管理员   
	        		byte[] keyclasbzr  = serializer.serialize("depinfo_manager:"+tr.getDep_id());  
	        		byte[] fieldclasman  = serializer.serialize(tr.getTech_id().toString()); 
	        		connection.hDel(keyclasbzr,fieldclasman);
        		}else if(tr.getRl_id()==EnumRoleLabel.教师组管理员.getCode()){
        			//教师组 管理员   
	        		byte[] keyclasbzr  = serializer.serialize("group_tech_manager:"+tr.getGroup_id());  
	        		byte[] fieldclasman  = serializer.serialize(tr.getTech_id().toString()); 
	        		connection.hDel(keyclasbzr,fieldclasman);
        		}else if(tr.getRl_id()==EnumRoleLabel.年级组长.getCode()){
        			//年级xia 组长    
	        		byte[] keyclasbzr  = serializer.serialize("grade_tech_manager:"+tr.getGrade_id());  
	        		byte[] fieldclasman  = serializer.serialize(tr.getTech_id().toString()); 
	        		
	        		connection.hDel(keyclasbzr,fieldclasman); 
        		}
         
            	
            	
                return true;
            }
        }, false, true);
        return result;
	}

	@Override
	public boolean deleteAll() {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
            	 RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
 				try {
 					deleteRedis(connection,serializer,"techrange:*");
 					deleteRedis(connection,serializer,"techrange_rl:*");
 					deleteRedis(connection,serializer,"sk_clas_tech:*");
 					deleteRedis(connection,serializer,"sk_tech_clas:*");
 					deleteRedis(connection,serializer,"sk_grade_thcour:*");
 					deleteRedis(connection,serializer,"sk_grade_tech:*");
 					
 					deleteRedis(connection,serializer,"sk_clas_bzr:*");
 					deleteRedis(connection,serializer,"group_stud_manager:*");
 					deleteRedis(connection,serializer,"depinfo_manager:*");
 					deleteRedis(connection,serializer,"group_tech_manager:*");
 					deleteRedis(connection,serializer,"sk_gradecour_clas:*");
 					deleteRedis(connection,serializer,"sk_gradecourtech_clas:*");
 					deleteRedis(connection,serializer,"grade_tech_manager:*");
 					
 					
 				} catch (Exception e) {
 					e.printStackTrace();
 					return false;
 				}                
                 return true;
            }  
        });  
        return result;   
	}

	@Override
	public TechRange getByTechRange(final Integer tech_id,
			final Integer rl_id,final Integer tr_id) {
		// TODO Auto-generated method stub
		TechRange result = redisTemplate.execute(new RedisCallback<TechRange>() {  
            public TechRange doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("techrange_rl:"+tech_id.toString()+":"+rl_id.toString());  
                byte[] field  = serializer.serialize(tr_id.toString()); 
                TechRange user = null;
				try {
					byte[] value = connection.hGet(key, field); 
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue))
	                	user = util.getPojoFromJSON(evalue, TechRange.class);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}                
                return user;
            }  
        });  
        return result;
	}

	@Override
	public List<TechRange> getAllOfTechId(final Integer tech_id) {
		List<TechRange> result = redisTemplate.execute(new RedisCallback<List<TechRange>>() {  
            public List<TechRange> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("techrange:"+tech_id);
                List<TechRange> list = new ArrayList<TechRange>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> entryStr : values.entrySet()) {
						String evaluestr = serializer.deserialize(entryStr.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							TechRange evalue = util.getPojoFromJSON(evaluestr, TechRange.class);
							list.add(evalue);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}   
                return list;
            }  
        });  
        return result;
	}

	@Override
	public  List<Map<String,Object>> getIdentityById(final String tech_id,final Integer rl_id,final RedisConnection connection) {
		
			List<Map<String,Object>> result = redisTemplate.execute(new RedisCallback< List<Map<String,Object>>>() {  
				public  List<Map<String,Object>> doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("techrange:"+tech_id);  
	                List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	                Map<String,Object> mapIdentity = new HashMap<String,Object>();
					try {
						Map<byte[], byte[]> values = redisConnection.hGetAll(key);
						List<Map<String,Object>> rllist = new ArrayList<Map<String,Object>>();
						for (Entry<byte[], byte[]> teacher : values.entrySet()) {
							Map<String,Object> map1 = new HashMap<String,Object>();
							String evaluestr = serializer.deserialize(teacher.getValue());
							TechRange evalue = util.getPojoFromJSON(evaluestr, TechRange.class);
							if(EnumRoleLabel.任课教师.getCode()==rl_id&&EnumRoleLabel.任课教师.getCode()==evalue.getRl_id()){
			            		map1.put("rl_id",evalue.getRl_id());
			            		map1.put("history",evalue.getHistory());
			            		map1.put("insert_time",evalue.getInsert_time());
			            		map1.put("rl_name",EnumRoleLabel.任课教师.toString());
			            		map1.put("tr_id",evalue.getTr_id());
			            		Course course = redisCourseDao.getCourseById(evalue.getCour_id().toString(),null);
			            		map1.put("course_name", course.getCour_name());
			            		Grade grade = redisGradeDao.getGradeById(evalue.getGrade_id(),null);
			            		map1.put("grade_name", grade.getGrade_name());
			            		Classes classes = redisClassesDao.getClassesById(evalue.getClas_id().toString(),null);
			            		if(classes!=null)
			            			map1.put("clas_name", classes.getClas_name());
			            		
			            		map1.put("grade_id", evalue.getGrade_id());
			            		map1.put("clas_id", evalue.getClas_id());
			            		map1.put("cour_id", evalue.getCour_id());
			            		rllist.add(map1);
			            	
			            	}else if(EnumRoleLabel.班主任.getCode()==rl_id&&EnumRoleLabel.班主任.getCode()==evalue.getRl_id()){
			        			//班级下的 班主任   
			            		map1.put("rl_id",evalue.getRl_id());
			            		map1.put("history",evalue.getHistory());
			            		map1.put("insert_time",evalue.getInsert_time());
			            		map1.put("rl_name",EnumRoleLabel.班主任.toString());
			            		
			            		
			            		Grade grade = redisGradeDao.getGradeById(evalue.getGrade_id(),null);
			            		map1.put("grade_name", grade.getGrade_name());
			            		Classes classes = redisClassesDao.getClassesById(evalue.getClas_id().toString(),connection);
			            		if(classes!=null)
			            			map1.put("clas_name", classes.getClas_name());
			            		 
			            		rllist.add(map1);
							}else if(EnumRoleLabel.学生组管理员.getCode()==rl_id&&EnumRoleLabel.学生组管理员.getCode()==evalue.getRl_id()){
			        			//学生组下的 管理员  
			        			map1.put("rl_id",evalue.getRl_id());
			            		map1.put("history",evalue.getHistory());
			            		map1.put("insert_time",evalue.getInsert_time());
			            		map1.put("rl_name",EnumRoleLabel.学生组管理员.toString());
			            		
			            		map1.put("group_id",evalue.getGroup_id());
			            		Group group  = redisGroupDao.getGroupById(Integer.parseInt(evalue.getGroup_id()),null);
			            		if(group!=null)
			            			map1.put("group_name",group.getGroup_name());
			        			rllist.add(map1);
		            		}else if(EnumRoleLabel.部门管理员.getCode()==rl_id&&EnumRoleLabel.部门管理员.getCode()==evalue.getRl_id()){
			        			//部门下的 管理员   
			        			map1.put("rl_id",evalue.getRl_id());
			            		map1.put("history",evalue.getHistory());
			            		map1.put("insert_time",evalue.getInsert_time());
			            		map1.put("rl_name",EnumRoleLabel.部门管理员.toString());
			            		
			            		Department department = redisDepartmentDao.getByDepId(evalue.getDep_id(),null);
			            		if(department!=null){
				            		map1.put("dep_id",evalue.getDep_id());
				            		map1.put("dep_name",department.getDep_name());
			            		}
			        			rllist.add(map1);
		            		}else if(EnumRoleLabel.教师组管理员.getCode()==rl_id&&EnumRoleLabel.教师组管理员.getCode()==evalue.getRl_id()){
			        			//教师组 管理员   
			        			map1.put("rl_id",evalue.getRl_id());
			            		map1.put("history",evalue.getHistory());
			            		map1.put("insert_time",evalue.getInsert_time());
			            		map1.put("rl_name",EnumRoleLabel.教师组管理员.toString());
			            		map1.put("group_id",evalue.getGroup_id());
			            		Group group  = redisGroupDao.getGroupById(Integer.parseInt(evalue.getGroup_id()),null);
			            		if(group!=null){
			            			map1.put("group_name",group.getGroup_name());
			            			map1.put("group_type",group.getGroup_type());
			            		}
			        			rllist.add(map1);
			        		}else if(EnumRoleLabel.管理员.getCode()==rl_id&&EnumRoleLabel.管理员.getCode()==evalue.getRl_id()){
			        			// 管理员 
			        			map1.put("rl_id",evalue.getRl_id());
			            		map1.put("history",evalue.getHistory());
			            		map1.put("insert_time",evalue.getInsert_time());
			            		map1.put("rl_name",EnumRoleLabel.管理员.toString());
			        			rllist.add(map1);
			        		}else if(EnumRoleLabel.校长.getCode()==rl_id&&EnumRoleLabel.校长.getCode()==evalue.getRl_id()){
			        			//   
			        			map1.put("rl_id",evalue.getRl_id());
			            		map1.put("history",evalue.getHistory());
			            		map1.put("insert_time",evalue.getInsert_time());
			            		map1.put("rl_name",EnumRoleLabel.校长.toString());
			        			rllist.add(map1);
			        		}else if(EnumRoleLabel.功能管理员.getCode()==rl_id&&EnumRoleLabel.功能管理员.getCode()==evalue.getRl_id()){
			        			//管理员   
			        			map1.put("rl_id",evalue.getRl_id());
			            		map1.put("history",evalue.getHistory());
			            		map1.put("insert_time",evalue.getInsert_time());
			            		map1.put("rl_name",EnumRoleLabel.功能管理员.toString());
			        			rllist.add(map1);
			        		}else if(EnumRoleLabel.分校校长.getCode()==rl_id&&EnumRoleLabel.分校校长.getCode()==evalue.getRl_id()){
			        			//教师组 管理员   
			        			map1.put("rl_id",evalue.getRl_id());
			            		map1.put("history",evalue.getHistory());
			            		map1.put("insert_time",evalue.getInsert_time());
			            		map1.put("rl_name",EnumRoleLabel.分校校长.toString());
			        			rllist.add(map1);
			        		}else if(EnumRoleLabel.财务管理员.getCode()==rl_id&&EnumRoleLabel.财务管理员.getCode()==evalue.getRl_id()){
			        			//教师组 管理员   
			        			map1.put("rl_id",evalue.getRl_id());
			            		map1.put("history",evalue.getHistory());
			            		map1.put("insert_time",evalue.getInsert_time());
			            		map1.put("rl_name",EnumRoleLabel.财务管理员.toString());
			        			rllist.add(map1);
			        		
			        		}else if(EnumRoleLabel.财务审批员.getCode()==rl_id&&EnumRoleLabel.财务审批员.getCode()==evalue.getRl_id()){
			        			//教师组 管理员   
			        			map1.put("rl_id",evalue.getRl_id());
			            		map1.put("history",evalue.getHistory());
			            		map1.put("insert_time",evalue.getInsert_time());
			            		map1.put("rl_name",EnumRoleLabel.财务审批员.toString());
			        			rllist.add(map1);
			        		
			        		}
//			        		else if(EnumRoleLabel.任课教师.getCode()==evalue.getRl_id()){
//			            		map1.put("rl_id",evalue.getRl_id());
//			            		map1.put("history",evalue.getHistory());
//			            		map1.put("insert_time",evalue.getInsert_time());
//			            		map1.put("rl_name",EnumRoleLabel.任课教师.toString());
//			            		
//			            		Course course = redisCourseDao.getCourseById(evalue.getCour_id().toString(),redisConnection);
//			            		map1.put("course_name",course!=null? course.getCour_name():"");
//			            		Grade grade = redisGradeDao.getGradeById(evalue.getGrade_id(),redisConnection);
//			            		map1.put("grade_name",grade!=null? grade.getGrade_name():"");
//			            		Classes classes = redisClassesDao.getClassesById(evalue.getClas_id().toString(),redisConnection);
//			            		map1.put("clas_name",classes!=null? classes.getClas_name():"");
//			            		
//			            		map1.put("grade_id", evalue.getGrade_id());
//			            		map1.put("clas_id", evalue.getClas_id());
//			            		map1.put("cour_id", evalue.getCour_id());
//			            		rllist.add(map1);
//			            	
//			            	}
//			            	else if(EnumRoleLabel.班主任.getCode()==evalue.getRl_id()){
//			        			//班级下的 班主任   
//			            		map1.put("rl_id",evalue.getRl_id());
//			            		map1.put("history",evalue.getHistory());
//			            		map1.put("insert_time",evalue.getInsert_time());
//			            		map1.put("rl_name",EnumRoleLabel.班主任.toString());
//			            		
//			            		
//			            		Grade grade = redisGradeDao.getGradeById(evalue.getGrade_id(),redisConnection);
//			            		map1.put("grade_name",grade!=null? grade.getGrade_name():"");
//			            		Classes classes = redisClassesDao.getClassesById(evalue.getClas_id().toString(),redisConnection);
//			            		map1.put("clas_name",classes!=null? classes.getClas_name():"");
//			            		 
//			            		rllist.add(map1);
//			        		}else if(EnumRoleLabel.学生组管理员.getCode()==evalue.getRl_id()){
//			        			//学生组下的 管理员  
//			        			map1.put("rl_id",evalue.getRl_id());
//			            		map1.put("history",evalue.getHistory());
//			            		map1.put("insert_time",evalue.getInsert_time());
//			            		map1.put("rl_name",EnumRoleLabel.学生组管理员.toString());
//			            		
//			            		map1.put("group_id",evalue.getGroup_id());
//			            		Group group  = redisGroupDao.getGroupById(Integer.parseInt(evalue.getGroup_id()),redisConnection);
//			            		map1.put("group_name",group!=null?group.getGroup_name():"");
//			        			rllist.add(map1);
//			        		}else if(EnumRoleLabel.部门管理员.getCode()==evalue.getRl_id()){
//			        			//部门下的 管理员   
//			        			map1.put("rl_id",evalue.getRl_id());
//			            		map1.put("history",evalue.getHistory());
//			            		map1.put("insert_time",evalue.getInsert_time());
//			            		map1.put("rl_name",EnumRoleLabel.部门管理员.toString());
//			            		
//			            		Department department = redisDepartmentDao.getByDepId(evalue.getDep_id(),redisConnection);
//			            		map1.put("dep_id",evalue.getDep_id());
//			            		map1.put("dep_name",department!=null?department.getDep_name():"");
//			            		
//			        			rllist.add(map1);
//			        		}else if(EnumRoleLabel.教师组管理员.getCode()==evalue.getRl_id()){
//			        			//教师组 管理员   
//			        			map1.put("rl_id",evalue.getRl_id());
//			            		map1.put("history",evalue.getHistory());
//			            		map1.put("insert_time",evalue.getInsert_time());
//			            		map1.put("rl_name",EnumRoleLabel.教师组管理员.toString());
//			            		map1.put("group_id",evalue.getGroup_id());
//			            		Group group  = redisGroupDao.getGroupById(Integer.parseInt(evalue.getGroup_id()),redisConnection);
//			            		map1.put("group_name",group!=null?group.getGroup_name():"");
//			            		map1.put("group_type",group!=null?group.getGroup_type():"");
//			        			rllist.add(map1);
//			        		}else if(EnumRoleLabel.管理员.getCode()==evalue.getRl_id()){
//			        			// 管理员 
//			        			map1.put("rl_id",evalue.getRl_id());
//			            		map1.put("history",evalue.getHistory());
//			            		map1.put("insert_time",evalue.getInsert_time());
//			            		map1.put("rl_name",EnumRoleLabel.管理员.toString());
//			        			rllist.add(map1);
//			        		}else if(EnumRoleLabel.校长.getCode()==evalue.getRl_id()){
//			        			//   
//			        			map1.put("rl_id",evalue.getRl_id());
//			            		map1.put("history",evalue.getHistory());
//			            		map1.put("insert_time",evalue.getInsert_time());
//			            		map1.put("rl_name",EnumRoleLabel.校长.toString());
//			        			rllist.add(map1);
//			        		}else if(EnumRoleLabel.功能管理员.getCode()==evalue.getRl_id()){
//			        			//管理员   
//			        			map1.put("rl_id",evalue.getRl_id());
//			            		map1.put("history",evalue.getHistory());
//			            		map1.put("insert_time",evalue.getInsert_time());
//			            		map1.put("rl_name",EnumRoleLabel.功能管理员.toString());
//			        			rllist.add(map1);
//			        		}else if(EnumRoleLabel.分校校长.getCode()==evalue.getRl_id()){
//			        			//教师组 管理员   
//			        			map1.put("rl_id",evalue.getRl_id());
//			            		map1.put("history",evalue.getHistory());
//			            		map1.put("insert_time",evalue.getInsert_time());
//			            		map1.put("rl_name",EnumRoleLabel.分校校长.toString());
//			        			rllist.add(map1);
//			        		}else if(EnumRoleLabel.职员.getCode()==evalue.getRl_id()){
//			        			//教师组 管理员   
//			        			map1.put("rl_id",evalue.getRl_id());
//			            		map1.put("history",evalue.getHistory());
//			            		map1.put("insert_time",evalue.getInsert_time());
//			            		map1.put("rl_name",EnumRoleLabel.职员.toString());
//			        			rllist.add(map1);
//			        		}
							
			            	mapIdentity.put("Identitys", rllist);
						}					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}   
					list.add(mapIdentity);
	                return list;
	            }  
	        });  
	        return result;
		
	}

	@Override
	public List<Map<String, Object>> getSkClasTech(final String clas_id,final RedisConnection connection) {
		if(null!=connection){
			List<Map<String, Object>> result = redisTemplate.execute(new RedisCallback<List<Map<String, Object>>>() {  
	            public List<Map<String, Object>> doInRedis(RedisConnection redisConnection)  
	                    throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("sk_clas_tech:"+clas_id);  
	           
	                List<Map<String, Object>> tempMap = new ArrayList<Map<String, Object>>();
	                
					try {
						Map<byte[], byte[]> values = connection.hGetAll(key);
						for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
							Map<String, Object> objMap = new HashMap<String, Object>();
							String evaluestr = serializer.deserialize(entrySet.getValue());
							if(StringUtils.isNotEmpty(evaluestr)){
								@SuppressWarnings("unchecked")
								Map<String,Object> evalue = util.getPojoFromJSON(evaluestr, Map.class);
								objMap.put("history", evalue.get("history"));
								objMap.put("cour_id", evalue.get("cour_id"));
								Course course = redisCourseDao.getCourseById(evalue.get("cour_id").toString(),null);
								objMap.put("cour_name", course.getCour_name());
								objMap.put("tech_id", evalue.get("tech_id"));
								Teacher teacher = redisTeacherDao.getByTechId(evalue.get("tech_id").toString(),null);
								objMap.put("tech_name",teacher.getTech_name());
								tempMap.add(objMap);
							}
						}					
					} catch (Exception e) {
						return null;
					}   
	                return tempMap;
	            }  
	        });  
	        return result;
		}else{
			List<Map<String, Object>> result = redisTemplate.execute(new RedisCallback<List<Map<String, Object>>>() {  
	            public List<Map<String, Object>> doInRedis(RedisConnection redisConnection)  
	                    throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("sk_clas_tech:"+clas_id);  
	           
	                List<Map<String, Object>> tempMap = new ArrayList<Map<String, Object>>();
	                
					try {
						Map<byte[], byte[]> values = redisConnection.hGetAll(key);
						for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
							Map<String, Object> objMap = new HashMap<String, Object>();
							String evaluestr = serializer.deserialize(entrySet.getValue());
								if(StringUtils.isNotEmpty(evaluestr)){
								@SuppressWarnings("unchecked")
								Map<String,Object> evalue = util.getPojoFromJSON(evaluestr, Map.class);
								objMap.put("history", evalue.get("history"));
								objMap.put("cour_id", evalue.get("cour_id"));
								Course course = redisCourseDao.getCourseById(evalue.get("cour_id").toString(),null);
								objMap.put("cour_name", course.getCour_name());
								objMap.put("tech_id", evalue.get("tech_id"));
//								Teacher teacher = redisTeacherDao.getByTechId(evalue.get("tech_id").toString(),null);
								byte[] t_key = serializer.serialize("techinfo:"+evalue.get("tech_id").toString());  
								Teacher teacher=null;								
								byte[] t_value = redisConnection.get(t_key); 
								String t_evalue = serializer.deserialize(t_value); 
								if(StringUtils.isNotEmpty(t_evalue)){
									teacher = util.getPojoFromJSON(t_evalue, Teacher.class);
									if(teacher!=null)
										objMap.put("tech_name",teacher.getTech_name());
								}
								
								tempMap.add(objMap);
							}
						}					
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}   
	                return tempMap;
	            }  
	        });  
	        return result;
		}
		
	}
	@Override
	public List<String> getSkClasTechId(final String clas_id) {
		List<String> result = redisTemplate.execute(new RedisCallback<List<String>>() {  
            public List<String> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("sk_clas_tech:"+clas_id);           
                List<String> tempMap = new ArrayList<String>();                
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
						String evaluestr = serializer.deserialize(entrySet.getKey());							
						tempMap.add(evaluestr);
					}					
				} catch (Exception e) {
//						return null;
				}   
                return tempMap;
            }  
        });  
        return result;		
	}

	@Override
	public List<Map<String, Object>> getSkTechClas(final String tech_id) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result = redisTemplate.execute(new RedisCallback<List<Map<String, Object>>>() {  
            public List<Map<String, Object>> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("sk_tech_clas:"+tech_id);  
           
                List<Map<String, Object>> tempMap = new ArrayList<Map<String, Object>>();
               
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
						 Map<String, Object> objMap = new HashMap<String, Object>();
						String evaluestr = serializer.deserialize(entrySet.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							@SuppressWarnings("unchecked")
							Map<String,Object> evalue = util.getPojoFromJSON(evaluestr, Map.class);
							objMap.put("history", evalue.get("history"));
							objMap.put("cour_id", evalue.get("cour_id"));
							Course course = redisCourseDao.getCourseById(evalue.get("cour_id").toString(),null);
							objMap.put("cour_name", course.getCour_name());
							objMap.put("grade_id", evalue.get("grade_id"));
							Grade grade = redisGradeDao.getGradeById(Integer.parseInt(evalue.get("grade_id").toString()),null);
							objMap.put("grade_name",grade.getGrade_name());
							objMap.put("clas_id",evalue.get("clas_id"));
							Classes classes = redisClassesDao.getClassesById(evalue.get("clas_id").toString(),null);
							objMap.put("clas_name",classes.getClas_name());
							tempMap.add(objMap);
						}
					}					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}   
                return tempMap;
            }  
        });  
        return result;
	}

	@Override
	public Map<String, Object> getSkGradeCourse(final String grade_id) {
		// TODO Auto-generated method stub
		Map<String, Object> result = redisTemplate.execute(new RedisCallback<Map<String, Object>>() {  
            public Map<String, Object> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("sk_grade_thcour:"+grade_id);  
           
                Map<String, Object> map = new HashMap<String, Object>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
						Map<String, Object> objMap = new HashMap<String, Object>();
						String evaluestr = serializer.deserialize(entrySet.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							@SuppressWarnings("unchecked")
							Map<String,Object> evalue = util.getPojoFromJSON(evaluestr, Map.class);
							objMap.put("cour_id", evalue.get("cour_id"));
							Course course = redisCourseDao.getCourseById(evalue.get("cour_id").toString(),null);
							objMap.put("cour_name", course.getCour_name());
							map.put(evalue.get("cour_id").toString(), objMap);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}   
                return map;
            }  
        });  
        return result;
	}

	@Override
	public Map<String, Object> getSkGradeTech(final String grade_id,final RedisConnection connection) {
		if(null!=connection){
			Map<String, Object> result = redisTemplate.execute(new RedisCallback<Map<String, Object>>() {  
	            public Map<String, Object> doInRedis(RedisConnection redisConnection)  
	                    throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("sk_grade_tech:"+grade_id);
	                Map<String, Object> map = new HashMap<String, Object>();
					try {
						Map<byte[], byte[]> values = connection.hGetAll(key);
						for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
							String tech_id = serializer.deserialize(entrySet.getValue());
							Teacher teacher = redisTeacherDao.getByTechId(tech_id,null);
							if(teacher!=null)
								map.put(tech_id, teacher);
						}					
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}   
	                return map;
	            }  
	        });  
	        return result;
		}else{
			Map<String, Object> result = redisTemplate.execute(new RedisCallback<Map<String, Object>>() {  
	            public Map<String, Object> doInRedis(RedisConnection redisConnection)  
	                    throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("sk_grade_tech:"+grade_id);
	                Map<String, Object> map = new HashMap<String, Object>();
					try {
						Map<byte[], byte[]> values = redisConnection.hGetAll(key);
						for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
							String tech_id = serializer.deserialize(entrySet.getValue());
							Teacher teacher = redisTeacherDao.getByTechId(tech_id,null);
							if(teacher!=null)
								map.put(tech_id, teacher);
						}					
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}   
	                return map;
	            }  
	        });  
	        return result;
		}
	}

	@Override
	public Map<String, Object> getSkClasBZR(final String clas_id,final RedisConnection connection) {
		if(null!=connection){
			Map<String, Object> result = redisTemplate.execute(new RedisCallback<Map<String, Object>>() {  
	            public Map<String, Object> doInRedis(RedisConnection redisConnection)  
	                    throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("sk_clas_bzr:"+clas_id);
	                Map<String, Object> map = new HashMap<String, Object>();
					try {
						Map<byte[], byte[]> values = connection.hGetAll(key);
						for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
							String tech_id = serializer.deserialize(entrySet.getValue());
							Teacher teacher = redisTeacherDao.getByTechId(tech_id,null);
							if(teacher!=null)
								map.put(tech_id, teacher);
						}					
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}   
	                return map;
	            }  
	        });  
	        return result;
		}else{
			Map<String, Object> result = redisTemplate.execute(new RedisCallback<Map<String, Object>>() {  
	            public Map<String, Object> doInRedis(RedisConnection redisConnection)  
	                    throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("sk_clas_bzr:"+clas_id);
	                Map<String, Object> map = new HashMap<String, Object>();
					try {
						Map<byte[], byte[]> values = redisConnection.hGetAll(key);
						for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
							String tech_id = serializer.deserialize(entrySet.getValue());
//							Teacher teacher = redisTeacherDao.getByTechId(tech_id,null);
//							if(teacher!=null)
//								map.put(tech_id, teacher);
							
							byte[] t_key = serializer.serialize("techinfo:"+tech_id);  
							Teacher teacher=null;								
							byte[] t_value = redisConnection.get(t_key); 
							String t_evalue = serializer.deserialize(t_value); 
							if(StringUtils.isNotEmpty(t_evalue)){
								teacher = util.getPojoFromJSON(t_evalue, Teacher.class);
								if(teacher!=null)
									map.put(tech_id, teacher);
							}
							
							
							
						}					
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}   
	                return map;
	            }  
	        });  
	        return result;
		}
	}

	@Override
	public Map<String, Object> getGroupStudManager(final String group_id) {
		// TODO Auto-generated method stub
		Map<String, Object> result = redisTemplate.execute(new RedisCallback<Map<String, Object>>() {  
            public Map<String, Object> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("group_stud_manager:"+group_id);
                Map<String, Object> map = new HashMap<String, Object>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
						String tech_id = serializer.deserialize(entrySet.getValue());
						Teacher teacher = redisTeacherDao.getByTechId(tech_id,null);
						if(teacher!=null)
							map.put(tech_id, teacher);
						
					}					
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}   
                return map;
            }  
        });  
        return result;
	}

	@Override
	public Map<String, Object> getDepinfoManager(final String dep_id,final RedisConnection connection) {
		if(null!=connection){
			Map<String, Object> result = redisTemplate.execute(new RedisCallback<Map<String, Object>>() {  
	            public Map<String, Object> doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("depinfo_manager:"+dep_id);
	                Map<String, Object> map = new HashMap<String, Object>();
					try {
						Map<byte[], byte[]> values = connection.hGetAll(key);
						for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
							String tech_id = serializer.deserialize(entrySet.getValue());
							Teacher teacher = redisTeacherDao.getByTechId(tech_id,null);
							if(teacher!=null)
								map.put(tech_id, teacher);
						}					
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}   
	                return map;
	            }  
	        });  
			return result;
		}else{
			Map<String, Object> result = redisTemplate.execute(new RedisCallback<Map<String, Object>>() {  
	            public Map<String, Object> doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("depinfo_manager:"+dep_id);
	                Map<String, Object> map = new HashMap<String, Object>();
					try {
						Map<byte[], byte[]> values = redisConnection.hGetAll(key);
						for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
							String tech_id = serializer.deserialize(entrySet.getValue());
							Teacher teacher = redisTeacherDao.getByTechId(tech_id,null);
							if(teacher!=null)
								map.put(tech_id, teacher);
						}					
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}   
	                return map;
	            }  
	        }); 
			return result;
		}
	}

	@Override
	public Map<String, Object> getGroupTechManager(final String group_id) {
		// TODO Auto-generated method stub
		Map<String, Object> result = redisTemplate.execute(new RedisCallback<Map<String, Object>>() {  
            public Map<String, Object> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("group_tech_manager:"+group_id);
                Map<String, Object> map = new HashMap<String, Object>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
						String tech_id = serializer.deserialize(entrySet.getValue());
						Teacher teacher = redisTeacherDao.getByTechId(tech_id,null);
						if(teacher!=null)
							map.put(tech_id, teacher);
						
					}					
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}   
                return map;
            }  
        });  
        return result;
	}

	@Override
	public Map<String, Object> getSkGradeCourClas(final String grade_id,
			final String cour_id) {
		// TODO Auto-generated method stub
		Map<String, Object> result = redisTemplate.execute(new RedisCallback<Map<String, Object>>() {  
            public Map<String, Object> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("sk_gradecour_clas:"+grade_id+":"+cour_id);  
           
                Map<String, Object> map = new HashMap<String, Object>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
						Map<String, Object> objMap = new HashMap<String, Object>();
						String evaluestr = serializer.deserialize(entrySet.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							@SuppressWarnings("unchecked")
							Map<String,Object> evalue = util.getPojoFromJSON(evaluestr, Map.class);
							
							objMap.put("clas_id",evalue.get("clas_id"));
							Classes classes = redisClassesDao.getClassesById(evalue.get("clas_id").toString(),null);
							objMap.put("clas_name",classes.getClas_name());
							map.put(evalue.get("clas_id").toString(), objMap);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}   
                return map;
            }  
        });  
        return result;
	}
	

	@Override
	public Map<String, Object> getSkGradeCourTechClas(final String grade_id,
			final String cour_id,final String tech_id) {
		// TODO Auto-generated method stub
		Map<String, Object> result = redisTemplate.execute(new RedisCallback<Map<String, Object>>() {  
            public Map<String, Object> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("sk_gradecour_clas:"+grade_id+":"+cour_id+":"+tech_id);  
           
                Map<String, Object> map = new HashMap<String, Object>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
						Map<String, Object> objMap = new HashMap<String, Object>();
						String evaluestr = serializer.deserialize(entrySet.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							@SuppressWarnings("unchecked")
							Map<String,Object> evalue = util.getPojoFromJSON(evaluestr, Map.class);
							
							objMap.put("clas_id",evalue.get("clas_id"));
							Classes classes = redisClassesDao.getClassesById(evalue.get("clas_id").toString(),null);
							objMap.put("clas_name",classes.getClas_name());
							map.put(evalue.get("clas_id").toString(), objMap);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}   
                return map;
            }  
        });  
        return result;
	}
	
	
	
	@Override
	public Map<String, Object> getGradeTechManager(final String grade_id,final RedisConnection connection) {
		// TODO Auto-generated method stub
		Map<String, Object> result = redisTemplate.execute(new RedisCallback<Map<String, Object>>() {  
            public Map<String, Object> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("grade_tech_manager:"+grade_id);
                Map<String, Object> map = new HashMap<String, Object>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
						String tech_id = serializer.deserialize(entrySet.getValue());
						
//						Teacher teacher = redisTeacherDao.getByTechId(tech_id,null);
//						if(teacher!=null)
//							map.put(tech_id, teacher);
						
						
						byte[] t_key = serializer.serialize("techinfo:"+tech_id);  
						Teacher teacher=null;								
						byte[] t_value = connection.get(t_key); 
						String t_evalue = serializer.deserialize(t_value); 
						if(StringUtils.isNotEmpty(t_evalue)){
							teacher = util.getPojoFromJSON(t_evalue, Teacher.class);
							if(teacher!=null)
								map.put(tech_id, teacher);
						}
						
					}					
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}   
                return map;
            }  
        });  
        return result;
	}
	
	
	
	
	
	
	
	
	@Override
	public  List<Map<String,Object>> getManagerGroupById(final String tech_id) {
		final MapToSortGroupComparator mc = new MapToSortGroupComparator();
			List<Map<String,Object>> result = redisTemplate.execute(new RedisCallback< List<Map<String,Object>>>() {  
				public  List<Map<String,Object>> doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("techrange:"+tech_id);  
	                List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	                Map<String,Object> mapIdentity = new HashMap<String,Object>();
					try {
						Map<byte[], byte[]> values = redisConnection.hGetAll(key);
						List<Map<String,Object>> rllist = new ArrayList<Map<String,Object>>();
						for (Entry<byte[], byte[]> teacher : values.entrySet()) {
							Map<String,Object> map1 = new HashMap<String,Object>();
							String evaluestr = serializer.deserialize(teacher.getValue());
							TechRange evalue = util.getPojoFromJSON(evaluestr, TechRange.class);
							if(EnumRoleLabel.教师组管理员.getCode()==evalue.getRl_id()){
			        			//教师组 管理员   
			        			map1.put("rl_id",evalue.getRl_id());
			            		map1.put("history",evalue.getHistory());
			            		map1.put("insert_time",evalue.getInsert_time());
			            		map1.put("rl_name",EnumRoleLabel.教师组管理员.toString());
			            		map1.put("group_id",evalue.getGroup_id());
			            		Group group  = redisGroupDao.getGroupById(Integer.parseInt(evalue.getGroup_id()),null);
			            		if(group!=null){
			            			map1.put("group_name",group.getGroup_name());
			            			map1.put("group_type",group.getGroup_type());
			            			map1.put("hx_groupid",group.getHx_groupid());
			            			map1.put("groupmembers", redisGroupMemberDao.getTeacherMembers(group.getGroup_id()+""));
			            		}
			            		if(group!=null&&group.getGroup_type()==Constant.GROUP_TYPE_OUTTER)
			            			rllist.add(map1);
			        		}
						
							
						}
						if(rllist!=null && rllist.size()>0){
//							MapToSortComparator mc = new MapToSortComparator();  
				        	Collections.sort(rllist,mc);
							mapIdentity.put("Identitys", rllist);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}   
					if(mapIdentity!=null &&mapIdentity.size()>0 )
						list.add(mapIdentity);
	                return list;
	            }  
	        });  
	        return result;
		
	}
	
	
	
	
	@Override
	public  List<Map<String,Object>> getIdentityByIds(final String tech_id,final Map<String,String> rl_ids,final RedisConnection connection) {
		
			List<Map<String,Object>> result = redisTemplate.execute(new RedisCallback< List<Map<String,Object>>>() {  
				public  List<Map<String,Object>> doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("techrange:"+tech_id);  
	                List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	                
	                Map<String,Object> retMap = new HashMap<String,Object>();
	                
	                Map<Object,Object> booleanMapRK = new HashMap<Object,Object>();
	                Map<Object,Object> booleanMapNJ = new HashMap<Object,Object>();
	                Map<Object,Object> booleanMapKC = new HashMap<Object,Object>();
	               
					try {
						Map<byte[], byte[]> values = redisConnection.hGetAll(key);
						List<Map<String,Object>> listRK = new ArrayList<Map<String,Object>>();
						List<Map<String,Object>> listBZR = new ArrayList<Map<String,Object>>();
						List<Map<String,Object>> listNJ = new ArrayList<Map<String,Object>>();
						List<Map<String,Object>> listKC = new ArrayList<Map<String,Object>>();
						for (Entry<byte[], byte[]> teacher : values.entrySet()) {
							Map<String,Object> map1 = new HashMap<String,Object>();
							String evaluestr = serializer.deserialize(teacher.getValue());
							TechRange evalue = util.getPojoFromJSON(evaluestr, TechRange.class);
							if(rl_ids.get(EnumRoleLabel.任课教师.getCode().toString())!=null&&EnumRoleLabel.任课教师.getCode()==evalue.getRl_id()){

								map1.put("rl_id",evalue.getRl_id());
			            		map1.put("rl_name",EnumRoleLabel.任课教师.toString());
			            		
			            		if(booleanMapKC.get(evalue.getCour_id())==null){
				            		Map<String,Object> map2 = new HashMap<String,Object>();
				            		Course course = redisCourseDao.getCourseById(evalue.getCour_id().toString(),null);
				            		map2.put("course_name", course.getCour_name());
				            		map2.put("cour_id", evalue.getCour_id());
				            		listKC.add(map2);
				            		booleanMapKC.put(evalue.getCour_id(), evalue.getCour_id());
			            		}
			            		
			            		
			            		if(booleanMapRK.get(evalue.getClas_id())!=null)
			            			continue;
			            		Grade grade = redisGradeDao.getGradeById(evalue.getGrade_id(),null);
			            		map1.put("grade_name", grade.getGrade_name());
			            		map1.put("grade_number", grade.getGrade_number());
			            		Classes classes = redisClassesDao.getClassesById(evalue.getClas_id().toString(),null);
			            		if(classes!=null)
			            			map1.put("clas_name", classes.getClas_name());
			            		
			            		map1.put("grade_id", evalue.getGrade_id());
			            		map1.put("clas_id", evalue.getClas_id());
			            		
			            		booleanMapRK.put(evalue.getClas_id(), evalue.getClas_id());
			            		listRK.add(map1);
			            	
			            	}else if(rl_ids.get(EnumRoleLabel.班主任.getCode().toString())!=null&&EnumRoleLabel.班主任.getCode()==evalue.getRl_id()){
			        			//班级下的 班主任   
			            		map1.put("rl_id",evalue.getRl_id());
			            		map1.put("rl_name",EnumRoleLabel.班主任.toString());
			            		
			            		
			            		//Grade grade = redisGradeDao.getGradeById(evalue.getGrade_id(),null);
			            		Grade grade = redisClassesDao.getGradesByCid(evalue.getClas_id());
			            		
			            		
			            		
			            		map1.put("grade_name", grade.getGrade_name());
			            		Classes classes = redisClassesDao.getClassesById(evalue.getClas_id().toString(),null);
			            		if(classes!=null)
			            			map1.put("clas_name", classes.getClas_name());
			            		map1.put("grade_id", grade.getGrade_id());
			            		map1.put("grade_number", grade.getGrade_number());
			            		map1.put("clas_id", evalue.getClas_id());
			            		listBZR.add(map1);
							}else if(rl_ids.get(EnumRoleLabel.年级组长.getCode().toString())!=null&&EnumRoleLabel.年级组长.getCode()==evalue.getRl_id()){
								if(booleanMapNJ.get(evalue.getClas_id())!=null)
			            			continue;
			        			map1.put("rl_id",evalue.getRl_id());
			            		map1.put("rl_name",EnumRoleLabel.年级组长.toString());
			            		
			            		map1.put("grade_id",evalue.getGrade_id());
			            		Grade grade = redisGradeDao.getGradeById(evalue.getGrade_id(),null);
			            		map1.put("grade_name", grade.getGrade_name());
			            		map1.put("grade_number", grade.getGrade_number());
			            		booleanMapNJ.put(evalue.getGrade_id(), evalue.getGrade_id());
			            		listNJ.add(map1);
		            		}

						}	
						retMap.put("mapRK", listRK);
						retMap.put("mapBZR", listBZR);
						retMap.put("mapNJ", listNJ);
						retMap.put("mapKC", listKC);
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					list.add(retMap);
	                return list;
	            }  
	        });  
	        return result;
		
	}
	
	
	
	
	@Override
	public List<Course> getTechCour(final Integer tech_id) {
		List<Course> result = redisTemplate.execute(new RedisCallback<List<Course>>() {  
            public List<Course> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("techrange:"+tech_id);
                List<Course> list = new ArrayList<Course>();
                Map<String,Object> map = new HashMap<String,Object>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> entryStr : values.entrySet()) {
						String evaluestr = serializer.deserialize(entryStr.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							TechRange evalue = util.getPojoFromJSON(evaluestr, TechRange.class);
							if(evalue.getRl_id()==EnumRoleLabel.任课教师.getCode()){
								Course course = redisCourseDao.getCourseById(evalue.getCour_id().toString(), null);
								if(map.get(course.getCour_id()+"")!=null)
									continue;
								map.put(course.getCour_id()+"", course.getCour_id());
								list.add(course);
							}
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}   
                return list;
            }  
        });  
        return result;
	}
	
	
	
	
	
	@Override
	public List<TechRange> getTechRK(final Integer tech_id) {
		List<TechRange> result = redisTemplate.execute(new RedisCallback<List<TechRange>>() {  
            public List<TechRange> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("techrange:"+tech_id);
                List<TechRange> list = new ArrayList<TechRange>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> entryStr : values.entrySet()) {
						String evaluestr = serializer.deserialize(entryStr.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							TechRange evalue = util.getPojoFromJSON(evaluestr, TechRange.class);
							if(evalue.getRl_id()==EnumRoleLabel.任课教师.getCode()){
								list.add(evalue);
							}
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}   
                return list;
            }  
        });  
        return result;
	}
	
	
	@Override
	public List<Map<String, Object>> getSkClasCourTech(final String clas_id,final String cour_id) {
		List<Map<String, Object>> result = redisTemplate.execute(new RedisCallback<List<Map<String, Object>>>() {  
            public List<Map<String, Object>> doInRedis(RedisConnection redisConnection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("sk_clas_tech:"+clas_id);  
           
                List<Map<String, Object>> tempMap = new ArrayList<Map<String, Object>>();
                
				try {
					Map<byte[], byte[]> values = redisConnection.hGetAll(key);
					for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
						Map<String, Object> objMap = new HashMap<String, Object>();
						String evaluestr = serializer.deserialize(entrySet.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							@SuppressWarnings("unchecked")
							Map<String,Object> evalue = util.getPojoFromJSON(evaluestr, Map.class);
							String ecour_id = evalue.get("cour_id").toString();
							if(!cour_id.equals(ecour_id))
								continue;
							objMap.put("history", evalue.get("history"));
							objMap.put("cour_id", ecour_id);
							Course course = redisCourseDao.getCourseById(ecour_id,null);
							objMap.put("cour_name", course.getCour_name());
							objMap.put("tech_id", evalue.get("tech_id"));
							Teacher teacher = redisTeacherDao.getByTechId(evalue.get("tech_id").toString(),null);
							objMap.put("tech_name",teacher.getTech_name());
							tempMap.add(objMap);
						}
					}					
				} catch (Exception e) {
					return null;
				}   
                return tempMap;
            }  
        });  
        return result;
		
	}

	@Override
	public Map<String,List<Teacher>> getSkClasBZRs(final String clas_ids) {
		Map<String, List<Teacher>> result = redisTemplate.execute(new RedisCallback<Map<String, List<Teacher>>>() {  
            public Map<String, List<Teacher>> doInRedis(RedisConnection redisConnection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Map<String, List<Teacher>> map = new HashMap<String, List<Teacher>>();
                try {
                	
                	String[] ids = clas_ids.split(",");
                	
	                for(int i=0;i<ids.length;i++){
		                byte[] key = serializer.serialize("sk_clas_bzr:"+ids[i]);
						Map<byte[], byte[]> values = redisConnection.hGetAll(key);
						List<Teacher> list = new ArrayList<Teacher>();
						for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
							String tech_id = serializer.deserialize(entrySet.getValue());
							Teacher teacher = redisTeacherDao.getByTechId(tech_id,null);
							if(teacher!=null){
								OrgUser orgUser = redisOrgUserDao.getUserById(teacher.getUser_id().toString());
								teacher.setUser_mobile(orgUser.getUser_mobile());
								teacher.setOpenid(orgUser.getOpenid());
							}
							list.add(teacher);
						}	
						map.put(ids[i], list);
	                }
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}   
                return map;
            } 
		});
		return result; 
	}

	@Override
	public List<TechRange> getTechRangeByOrg(Integer org_id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
