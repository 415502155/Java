package cn.edugate.esb.service;
 
import java.util.List;
 
import cn.edugate.esb.entity.OrgTree;
 
/**
 * 机构树服务接口
 * Title:OrgTreeService
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月27日下午1:39:01
 */
public interface OrgTreeService {
    /**
     * 添加新的机构树
     * @param orgTree
     */
    public abstract void add(OrgTree orgTree);
    /**
     * 根据主键删除机构树
     * @param id
     * @return
     */
    public abstract Integer delete(int id);
    /**
     * 更新机构树
     * @param orgTree
     * @return
     */
    public abstract OrgTree update(OrgTree orgTree);
    /**
     * 根据主键获取机构树
     * @param id
     * @return
     */
    public abstract OrgTree getOrgById(int id);
    /**
     * 获得所有机构树列表
     * @return
     */
    public abstract List<OrgTree> getOrgTreeList();
}