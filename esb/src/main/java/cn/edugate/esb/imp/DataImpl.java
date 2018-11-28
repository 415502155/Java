package cn.edugate.esb.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.IDataDao;
import cn.edugate.esb.pojo.Data;
import cn.edugate.esb.service.DataService;

/**
 * 组服务实现类
 * Title:GroupImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午9:33:24
 */
@Component
@Transactional("transactionManager")
public class DataImpl implements DataService {

	@Autowired
	private IDataDao dataDao;

	@Override
	public List<Data> getList(Integer type, String area, String ids) {
		return dataDao.getList(type,area,ids);
	}

}
