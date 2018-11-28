package cn.edugate.esb.imp;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
 
import cn.edugate.esb.dao.IFieldDao;
import cn.edugate.esb.entity.Field;
import cn.edugate.esb.service.FieldService;
import cn.edugate.esb.util.Constant;
 
/**
 * 场地实现类
 * Title:FieldServiceImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月27日上午10:19:41
 */
@Component
@Transactional("transactionManager")
public class FieldServiceImpl implements FieldService{
 
    private static Logger logger = Logger.getLogger(FieldServiceImpl.class);
    
    @Autowired
    private IFieldDao fieldDao;
    public void setFieldDao(IFieldDao fieldDao) {
        this.fieldDao = fieldDao;
    }
    
    @Override
    public void add(Field field) {
        field.setIs_del(0);
        field.setInsert_time(new Date());
        field.setUpdate_time(new Date());
        fieldDao.save(field);
    }
 
    @Override
    public Integer delete(Integer id) {
        Field field = getById(id);
        field.setIs_del(Constant.IS_DEL_YES);
        field.setDel_time(new Date());
        fieldDao.update(field);
        return null;
    }
 
    @Override
    public Field update(Field field) {
        field.setUpdate_time(new Date());
        fieldDao.update(field);
        return field;
    }
 
    @Override
    public Field getById(Integer id) {
        Field field = new Field();
        try {
            field = fieldDao.get(Field.class, id);
        } catch (Exception e) {
            logger.error("get Field by ID error");
        }
        return field;
    }
 
    @Override
    public List<Field> getList(Field field) {
        List<Field> list = new ArrayList<Field>();
        try {
            list = fieldDao.getList(field);
        } catch (Exception e) {
            logger.error("get FieldList error");
        }
        return list;
    }
 
    @Override
    public List<Field> getAll() {
        //Criterion criterion = Restrictions.conjunction();
        Criterion criterion = Restrictions.and(Restrictions.eq("is_del", 0));
        try {
            return fieldDao.list(Field.class, criterion);
        } catch (Exception e) {
            logger.error("Group getAll error:"+e.getMessage());
            return null;
        }
    }
 
}