package cn.edugate.esb.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.IModule2FunctionDao;
import cn.edugate.esb.dao.IModuleDao;
import cn.edugate.esb.entity.Module;
import cn.edugate.esb.entity.Module2Function;
import cn.edugate.esb.service.Module2FunctionService;
import cn.edugate.esb.util.Constant;

/**
 * 模块功能关系服务实现类
 * Title:Module2Function2FunctionImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月11日下午4:18:02
 */
@Component
@Transactional("transactionManager")
public class Module2FunctionImpl implements Module2FunctionService{

	private static Logger logger = Logger.getLogger(Module2FunctionImpl.class);
	
	@Autowired
	private IModule2FunctionDao module2functionDao;
	@Autowired
	private IModuleDao moduleDao;
	
	@Override
	public void add(Module2Function module2function) {
		module2function.setIs_del(0);
		module2function.setInsert_time(new Date());
		try {
			module2functionDao.saveOrUpdate(module2function);
		} catch (Exception e) {
			logger.error("create Module2Function error");	
		}		
	}

	@Override
	public List<Module2Function> getList(Module2Function module2function) {
		List<Module2Function> list = new ArrayList<Module2Function>();
		try {
			list = module2functionDao.getList(module2function);
		} catch (Exception e) {
			logger.error("get Module2FunctionList error");
		}
		return list;
	}

	@Override
	public void update(Module2Function module2function) {
		deleteByIDs(module2function.getMod_id(),module2function.getFun_id());
		add(module2function);
	}

	@Override
	public void deleteByIDs(Integer moduleID, Integer functionID) {
		module2functionDao.deleteByIDs(moduleID,functionID);
	}

	@Override
	public Module getModuleByFunction(Integer functionId) {
		Module module = new Module();
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("is_del", Constant.IS_DEL_NO);
		map.put("fun_id", functionId);
		Criterion criterion = Restrictions.allEq(map);
		try {
			List<Module2Function> list = module2functionDao.list(Module2Function.class, criterion);
			if(null!=list&&list.size()!=0){
				Module2Function mf = list.get(0);
				module = moduleDao.get(Module.class, mf.getMod_id());
			}
		} catch (Exception e) {
			logger.error("Module2Function getModuleByFunction error:"+e.getMessage());
			return module;
		}
		return module;
	}

}
