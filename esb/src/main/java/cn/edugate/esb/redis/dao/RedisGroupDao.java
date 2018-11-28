package cn.edugate.esb.redis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.connection.RedisConnection;

import cn.edugate.esb.entity.Group;

/**
 * 组DAO接口
 * Title:RedisGroupDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午10:28:42
 */
public interface RedisGroupDao {
	/**
	 * 添加组
	 * @param group
	 * @return
	 */
	boolean addGroup(Group group);
	/**
	 * 批量添加组(For Redis)
	 * @param groups
	 * @return 
	 */
	boolean addGroups(List<Group> groups);
	/**
	 * 删除组
	 * @param group
	 * @return
	 */
	boolean deleteGroup(Group group);
	/**
	 * 无条件的删除全部(For Redis)
	 * @return 
	 */
	boolean deleteAllGroups();
	/**
	 * 根据主键获取组
	 * @param GroupId
	 * @return
	 */
	Group getGroupById(Integer GroupId,RedisConnection connection);
	/**
	 * 根据查询条件获取组
	 * @param orgId
	 * @return
	 */
	Map<String,Group> getGroups(Integer orgId,Integer groupType);
	/**
	 * 根据成员信息获取组
	 * @param orgId
	 * @return
	 */
	Map<String,Group> getGroupsByMember(Integer memberId,Integer type);
}
