package cn.edugate.esb.service;
 
import java.util.List;
 
import cn.edugate.esb.entity.Field;
 
/**
 * 场地服务接口
 * Title:FieldService
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月27日上午10:16:20
 */
public interface FieldService {
    /**
     * 添加新的场地
     * @param field
     */
    public abstract void add(Field field);
    /**
     * 根据主键删除场地
     * @param id
     * @return
     */
    public abstract Integer delete(Integer id);
    /**
     * 更新场地
     * @param field
     * @return
     */
    public abstract Field update(Field field);
    /**
     * 根据主键获取场地
     * @param id
     * @return
     */
    public abstract Field getById(Integer id);
    /**
     * 获得场地列表
     * @param field
     * @return
     */
    public abstract List<Field> getList(Field field);
    /**
     * 无条件的获取全部场地(For Redis)
     * @return
     */
    public abstract List<Field> getAll();
}