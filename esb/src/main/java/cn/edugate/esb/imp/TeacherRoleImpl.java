package cn.edugate.esb.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.ITeacherRoleDao;
import cn.edugate.esb.entity.TeacherRole;
import cn.edugate.esb.service.TeacherRoleService;
/**
 * 角色权限关系Service实现
 * Title:RoleImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年4月24日下午1:39:51
 */
@Component
@Transactional("transactionManager")
public class TeacherRoleImpl implements TeacherRoleService {	
	
	private ITeacherRoleDao teacherRoleDao;	
	
	@Autowired
	public void setTeacherRoleDao(ITeacherRoleDao teacherRoleDao) {
		this.teacherRoleDao = teacherRoleDao;
	}

	

	@Override
	public void deleteByUserId(Integer userId) {
		Criterion criterion = Restrictions.eq("tech_id", userId);
		this.teacherRoleDao.delete(TeacherRole.class, criterion);
	}

	@Override
	public TeacherRole getTeachRole(Integer role_id, Integer tech_id) {
		// TODO Auto-generated method stub
		TeacherRole tr = this.teacherRoleDao.getTeachRole(role_id, tech_id);
		return tr;
	}
	
	@Override
	public void saveOrUpdateUser2Role(List<TeacherRole> user2roleArray) {
		// TODO Auto-generated method stub
		Set<Integer> ssoIds = new HashSet<Integer>();
		List<TeacherRole> list = new ArrayList<TeacherRole>();
		Boolean isNew = true;
		//遍历获得所有需要更新的ssoID
		for(TeacherRole u2r:user2roleArray){
			ssoIds.add(u2r.getTech_id());
		}
		//以用户ssoId为单位进行下一步操作
		for(Integer ssoId:ssoIds){
			//查询当前用户所有已经存在的权限关系
			Criterion criterion = Restrictions.eq("tech_id", ssoId);
			list = this.teacherRoleDao.list(TeacherRole.class, criterion);
			//用【待更新的关系】去比对【当前已经存在的权限关系】
			for(TeacherRole u2rArray:user2roleArray){
				isNew = true;
				for(TeacherRole u2rList:list){
					if(u2rArray.getRole_id()==u2rList.getRole_id()){
						//如果数据库中已有当前关系，则更新此关系，并在list中移除此条数据
						isNew = false;
						u2rList.setUpdate_time(new Date());
						this.teacherRoleDao.saveOrUpdate(u2rList);
						list.remove(u2rList);
						break;
					}
				}
				//如果待更新的关系是一条新数据，则向数据库添加此条数据
				if(isNew){
					this.teacherRoleDao.saveOrUpdate(u2rArray);
				}
			}
			//将遗留的旧关系删除
			for (TeacherRole user2Role : list) {
				this.teacherRoleDao.delete(user2Role);
			}
			
		}
	}


	@Override
	public void saveOrUpdateUser2Role(Integer user_id, String[] ids) {
		Criterion criterion = Restrictions.eq("tech_id", user_id);
		List<TeacherRole> list = this.teacherRoleDao.list(TeacherRole.class, criterion);
		Set<Integer> addids = new HashSet<Integer>();
		Set<Integer> deleteids = new HashSet<Integer>();
		for (TeacherRole teacherRole : list) {
			deleteids.add(teacherRole.getRole_id());
		}
		for (String id : ids) {
			if(!"".equals(id)){
				Integer idint = Integer.parseInt(id);
				if(deleteids.contains(idint)){
					deleteids.remove(idint);
				}else{
					addids.add(idint);
				}
			}
		}
		for (Integer addid : addids) {
			TeacherRole tr = new TeacherRole();
			tr.setTech_id(user_id);
			tr.setRole_id(addid);
			tr.setIs_del(false);
			tr.setInsert_time(new Date());
			this.teacherRoleDao.save(tr);
		}
		for (Integer deleteid : deleteids) {
//			Criterion dcriterion = Restrictions.and(Restrictions.eq("tech_id", user_id)).add(Restrictions.eq("role_id", deleteid));
//			this.teacherRoleDao.delete(TeacherRole.class, dcriterion);
			TeacherRole tr = this.teacherRoleDao.getTeachRole(deleteid, user_id);
			this.teacherRoleDao.delete(tr);
		}		
	}



	@Override
	public void removetr(TeacherRole... trlist) {
		// TODO Auto-generated method stub		
		this.teacherRoleDao.delete(trlist);
	}



	@Override
	public List<TeacherRole> getTeachRoleByRoleId(Integer role_id) {
		// TODO Auto-generated method stub
		Criterion criterion = Restrictions.and(Restrictions.eq("role_id", role_id));
		List<TeacherRole> ls = this.teacherRoleDao.list(TeacherRole.class, criterion, Order.desc("tech2role_id"));
		return ls;
	}



	@Override
	public List<TeacherRole> getTeachRoleByOrg(Integer org_id) {
		// TODO Auto-generated method stub
		return this.teacherRoleDao.getTeacherRoleByOrg(org_id);
	}


}
