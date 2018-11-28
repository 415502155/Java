package cn.edugate.esb.redis.cache;
 
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.OrgTree;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisOrgTreeDao;
import cn.edugate.esb.service.OrgTreeService;
 
/**
 * 机构树缓存
 * Title:OrgTreeCacheProvider
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月27日下午3:01:28
 */
@Cache(name="机构树缓存",entities={OrgTree.class})
public class OrgTreeCacheProvider implements CacheProvider<OrgTree>,java.io.Serializable {
 
    private static final long serialVersionUID = 6890358297898677080L;
 
    static Logger logger=LoggerFactory.getLogger(OrgTreeCacheProvider.class);
    
    private RedisOrgTreeDao redisOrgTreeDao;
    @Autowired
    public void setRedisOrgTreeDao(RedisOrgTreeDao redisOrgTreeDao) {
        this.redisOrgTreeDao = redisOrgTreeDao;
    }
 
    private OrgTreeService orgTreeService;
    @Autowired
    public void setOrgTreeService(OrgTreeService orgTreeService) {
        this.orgTreeService = orgTreeService;
    }
    
    @Override
    public void add(OrgTree orgTree) {
        this.redisOrgTreeDao.addOrgTree(orgTree);
    }
 
    @Override
    public void update(OrgTree orgTree) {
    	OrgTree oldOrgTree = redisOrgTreeDao.getOrgTreeById(orgTree.getOtree_id());
    	if(oldOrgTree!=null)
    		this.redisOrgTreeDao.deleteOrgTree(oldOrgTree);
        
        this.redisOrgTreeDao.addOrgTree(orgTree);
    }
 
    @Override
    public void delete(OrgTree orgTree) {
        this.redisOrgTreeDao.deleteOrgTree(orgTree);
    }
 
    @Override
    public void refreshAll() {
        this.redisOrgTreeDao.deleteAllOrgTrees();
        List<OrgTree> list = orgTreeService.getOrgTreeList();
        this.redisOrgTreeDao.addOrgTrees(list);
    }

	@Override
	public void refreshOrg(String org_id) {
		refreshAll();
	}
}