package cn.edugate.esb.dao.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IIconDao;
import cn.edugate.esb.entity.Icon;
import cn.edugate.esb.util.Constant;

/**
 * Title: IIconImpl
 * Description:图标 
 * Company: 世纪伟业
 * @author Liu Yang
 * @date 2018年8月14日下午1:53:43
 */
@Repository
public class IIconImpl extends BaseDAOImpl<Icon> implements IIconDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Icon> getOnUsing(Integer org_id,Integer identity) {
		Session session = this.factory.getCurrentSession();
		String sql = "";
		if(Constant.IDENTITY_PARENT==identity){
			sql = " SELECT oi.p_show,i.p_key,oi.org_order AS `order` FROM icon i INNER JOIN org_icon oi ON i.icon_id=oi.icon_id AND oi.`status`=1 AND oi.is_del=0 AND oi.org_id=:org_id WHERE i.`status`=1 AND i.is_del=0 AND i.p_show=1 ";
		}else if(Constant.IDENTITY_TEACHER==identity){
			sql = " SELECT oi.t_show,i.t_key,oi.org_order AS `order` FROM icon i INNER JOIN org_icon oi ON i.icon_id=oi.icon_id AND oi.`status`=1 AND oi.is_del=0 AND oi.org_id=:org_id WHERE i.`status`=1 AND i.is_del=0 AND i.t_show=1 ";
		}else if(Constant.IDENTITY_STUDENT==identity){
			sql = " SELECT oi.s_show,i.s_key,oi.org_order AS `order` FROM icon i INNER JOIN org_icon oi ON i.icon_id=oi.icon_id AND oi.`status`=1 AND oi.is_del=0 AND oi.org_id=:org_id WHERE i.`status`=1 AND i.is_del=0 AND i.s_show=1 ";
		}else if(Constant.IDENTITY_WEB==identity){
			sql = " SELECT oi.w_show,i.w_key,oi.org_order AS `order` FROM icon i INNER JOIN org_icon oi ON i.icon_id=oi.icon_id AND oi.`status`=1 AND oi.is_del=0 AND oi.org_id=:org_id WHERE i.`status`=1 AND i.is_del=0 AND i.s_show=1 ";
		}else{
			sql = " SELECT oi.p_show,i.p_key,oi.s_show,i.s_key,oi.t_show,i.t_key,oi.w_show,i.w_key,oi.org_order AS `order` FROM icon i INNER JOIN org_icon oi ON i.icon_id=oi.icon_id AND oi.`status`=1 AND oi.is_del=0 AND oi.org_id=:org_id WHERE i.`status`=1 AND i.is_del=0 ";
		}
		Query query = session.createSQLQuery(sql);
		query.setInteger("org_id", org_id);
		query.setResultTransformer(Transformers.aliasToBean(Icon.class));
		return query.list();
	}

	@SuppressWarnings({ "serial", "unchecked" })
	@Override
	public Map<Integer, List<Icon>> getIconList() {
		Map<Integer,List<Icon>> map = new HashMap<Integer,List<Icon>>();
		String sql = " SELECT oi.org_order as `order`,oi.t_show,oi.s_show,oi.p_show,oi.w_show,oi.org_id,i.t_key,i.s_key,i.p_key,i.w_key FROM org_icon oi INNER JOIN icon i ON oi.icon_id=i.icon_id AND i.is_del=oi.is_del AND i.`status`=oi.`status` WHERE oi.is_del=0 AND oi.`status`=1 ";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Icon.class));
		List<Icon> list = query.list();
		for (final Icon icon : list) {
			if(map.containsKey(icon.getOrg_id())){
				map.get(icon.getOrg_id()).add(icon);
			}else{
				map.put(icon.getOrg_id(), new ArrayList<Icon>(){{add(icon);}});
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Icon> getAllList() {
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT * FROM icon WHERE is_del=0";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Icon.class));
		return query.list();
	}
	
}
