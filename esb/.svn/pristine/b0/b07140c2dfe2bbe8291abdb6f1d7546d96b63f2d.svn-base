package cn.edugate.esb.imp;
 
import java.util.Date;
import java.util.List;
 
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
 
import cn.edugate.esb.dao.IOrgTreeDao;
import cn.edugate.esb.entity.OrgTree;
import cn.edugate.esb.service.OrgTreeService;
import cn.edugate.esb.util.Constant;
 
/**
 * 机构树服务实现类
 * Title:OrgTreeServiceImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月27日下午1:42:40
 */
@Component
@Transactional("transactionManager")
public class OrgTreeServiceImpl implements OrgTreeService {
 
    private static Logger logger = Logger.getLogger(OrgTreeServiceImpl.class);
    
    private IOrgTreeDao orgTreeDao;
    @Autowired
    public void setOrgTreeDao(IOrgTreeDao orgTreeDao) {
        this.orgTreeDao = orgTreeDao;
    }
 
    @Override
    public void add(OrgTree orgTree) {
        orgTree.setIs_del(Constant.IS_DEL_NO);
        orgTree.setInsert_time(new Date());
        try {
            orgTreeDao.save(orgTree);
        } catch (Exception e) {
            logger.error("OrgTree add error");    
        }
    }
 
    @Override
    public Integer delete(int id) {
        OrgTree oldOrgTree = new OrgTree();
        try {
            oldOrgTree = orgTreeDao.get(OrgTree.class, id);
            oldOrgTree.setIs_del(Constant.IS_DEL_YES);
            oldOrgTree.setDel_time(new Date());
            orgTreeDao.update(oldOrgTree);
        } catch (Exception e) {
            logger.error("OrgTree delete error");    
            return null;
        }
        return oldOrgTree.getOtree_id();
    }
 
    @Override
    public OrgTree update(OrgTree orgTree) {
        try {
            orgTree.setUpdate_time(new Date());
            orgTreeDao.update(orgTree);
        } catch (Exception e) {
            logger.error("OrgTree update error");    
            return null;
        }
        return orgTree;
    }
 
    @Override
    public OrgTree getOrgById(int id) {
        OrgTree orgTree = new OrgTree();
        try {
            orgTree = orgTreeDao.get(OrgTree.class, id);
        } catch (Exception e) {
            logger.error("OrgTree getById error");    
            return null;
        }
        return orgTree;
    }
 
    @Override
    public List<OrgTree> getOrgTreeList() {
        //Criterion criterion = Restrictions.conjunction();
        Criterion criterion = Restrictions.and(Restrictions.eq("is_del", 0));
        try {
            return orgTreeDao.list(OrgTree.class, criterion);
        } catch (Exception e) {
            logger.error("OrgTree getAll error:"+e.getMessage());
            return null;
        }
    }
}