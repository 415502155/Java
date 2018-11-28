package cn.edugate.esb.redis.dao;
 
import java.util.List;
 

import cn.edugate.esb.entity.OrgTree;
import cn.edugate.esb.entity.Organization;
 
public interface RedisOrgTreeDao {
    /**
     * 添加机构树
     * @param orgTree
     * @return
     */
    boolean addOrgTree(OrgTree orgTree);
    /**
     * 批量添加机构树(For Redis)
     * @param orgTrees
     * @return 
     */
    boolean addOrgTrees(List<OrgTree> orgTrees);
    /**
     * 删除机构树
     * @param orgTree
     * @return
     */
    boolean deleteOrgTree(OrgTree orgTree);
    /**
     * 无条件的删除全部(For Redis)
     * @return 
     */
    boolean deleteAllOrgTrees();
    /**
     * 根据主键获取机构树
     * @param OrgTreeId
     * @return
     */
    OrgTree getOrgTreeById(Integer OrgTreeId);
    
     List<Organization> getOrgsByTreeId(Integer OrgTreeId);
    
}