package cn.edugate.esb.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.IKeyValueDao;
import cn.edugate.esb.entity.KeyValue;
import cn.edugate.esb.service.KeyValueService;

/**
 * 菜单服务实现类
 * Title:GradeImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月19日上午9:57:49
 */
@Component
@Transactional("transactionManager")
public class KeyValueImpl implements KeyValueService {

	@Autowired
	private IKeyValueDao keyValueDao;
	
	@Override
	public List<KeyValue> getListByType(Integer type) {
		try {
			return keyValueDao.getListByType(type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
