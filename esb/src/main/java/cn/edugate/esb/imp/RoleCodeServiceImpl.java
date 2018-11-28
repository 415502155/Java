package cn.edugate.esb.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.IRoleCodeDao;
import cn.edugate.esb.entity.RoleCode;
import cn.edugate.esb.service.RoleCodeService;

/**
 * 功能服务实现类
 * Title:FunctionServiceImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月11日下午4:04:27
 */
@Component
@Transactional("transactionManager")
public class RoleCodeServiceImpl implements RoleCodeService{

	@Autowired
	private IRoleCodeDao roleCodeDao;

	@Override
	public List<RoleCode> getList(RoleCode roleCode) {
		return roleCodeDao.getList(roleCode);
	}

}
