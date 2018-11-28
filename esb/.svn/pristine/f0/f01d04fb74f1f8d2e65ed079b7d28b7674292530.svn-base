package cn.edugate.esb.entity;
 
import java.util.Date;
 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "org_tree", catalog = "`edugate_base`")
public class OrgTree {
    // 机构树主键
    private Integer otree_id;
    // 机构主键
    private Integer org_id;
    // 子级机构主键
    private Integer lower_id;
    // 创建时间
    private Date insert_time;
    // 更新时间
    private Date update_time;
    // 删除时间
    private Date del_time;
    // 是否删除
    private Integer is_del;
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getOtree_id() {
        return otree_id;
    }
    public void setOtree_id(Integer otree_id) {
        this.otree_id = otree_id;
    }
    public Integer getOrg_id() {
        return org_id;
    }
    public void setOrg_id(Integer org_id) {
        this.org_id = org_id;
    }
    public Integer getLower_id() {
        return lower_id;
    }
    public void setLower_id(Integer lower_id) {
        this.lower_id = lower_id;
    }
    public Date getInsert_time() {
        return insert_time;
    }
    public void setInsert_time(Date insert_time) {
        this.insert_time = insert_time;
    }
    public Date getUpdate_time() {
        return update_time;
    }
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
    public Date getDel_time() {
        return del_time;
    }
    public void setDel_time(Date del_time) {
        this.del_time = del_time;
    }
    public Integer getIs_del() {
        return is_del;
    }
    public void setIs_del(Integer is_del) {
        this.is_del = is_del;
    }
 
}