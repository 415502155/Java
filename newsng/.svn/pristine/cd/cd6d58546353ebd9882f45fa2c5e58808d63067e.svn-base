package sng.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import sng.dao.BaseSetRuleManageDao;
import sng.entity.Rulelist;
import sng.pojo.ParamObj;

/**
 * @author magq
 * @version 1.0
 * @desc 基础设置模块-规则设置dao接口实现
 * @data 2018-10-31
 */
@Repository
public class BaseSetRuleManageDaoImpl extends BaseDaoImpl<Rulelist> implements BaseSetRuleManageDao {

    /*
     * 更新规则设置信息(non-Javadoc)
     *
     * @see sng.dao.BaseSetRuleManageDao#updateRuleInfo(sng.entity.Rulelist)
     */
    @Override
    public int updateRuleInfo(Rulelist rulelist) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<>();
        sql.append(" update rulelist set org_id=?");
        paramList.add(rulelist.getOrg_id());
        if (rulelist.getAudit_switch() != null) {
            sql.append(" ,audit_switch=?");
            paramList.add(rulelist.getAudit_switch());
        }
        if (rulelist.getPayment_period() != null) {
            sql.append(" ,payment_period=?");
            paramList.add(rulelist.getPayment_period());
        }
        if (StringUtils.isNotBlank(rulelist.getRefund_instructions())) {
            sql.append(" ,refund_instructions=?");
            paramList.add(rulelist.getRefund_instructions());
        }
        if (rulelist.getAuthentication_method() != null) {
            sql.append(" ,authentication_method=?");
            paramList.add(rulelist.getAuthentication_method());
        }
        if (rulelist.getUpdate_time() != null) {
            sql.append(" ,update_time=?");
            paramList.add(rulelist.getUpdate_time());
        }
        sql.append(" where rule_id =?");
        paramList.add(rulelist.getRule_id());
        sql.append(" and org_id=?");
        paramList.add(rulelist.getOrg_id());

        Session session = this.factory.getCurrentSession();
        Query query = session.createSQLQuery(sql.toString());
        for (int i = 0; i < paramList.size(); i++) {
            query.setParameter(i, paramList.get(i));
        }
        return query.executeUpdate();
    }

    /*
     * (non-Javadoc) 获取缴费设置规则信息
     *
     * @see sng.dao.BaseSetRuleManageDao#queryPaySetInfo(sng.entity.Rulelist)
     */
    @Override
    public List<Rulelist> queryPaySetListInfo(ParamObj paramObj) {
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<>();
        sql.append("select r.* from rulelist r where 1=1");
        if (paramObj.getOrg_id() != null) {
            sql.append(" and r.org_id=? ");
            paramList.add(paramObj.getOrg_id());
        }
        if (paramObj.getIsDel() != null) {
            sql.append(" and r.is_del=?");
            paramList.add(paramObj.getIsDel());
        }
        Session session = this.factory.getCurrentSession();
        Query query = session.createSQLQuery(sql.toString());
        for (int i = 0; i < paramList.size(); i++) {
            query.setParameter(i, paramList.get(i));
        }
        query.setResultTransformer(Transformers.aliasToBean(Rulelist.class));
        List<Rulelist> list = query.list();
        return list;
    }

}
