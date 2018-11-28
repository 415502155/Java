package cn.edugate.esb.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.IFunctionDao;
import cn.edugate.esb.dao.IModuleDao;
import cn.edugate.esb.entity.Module;
import cn.edugate.esb.service.ModuleService;
import cn.edugate.esb.util.Paging;

/**
 * 模块服务接口
 * Title:ModuleServiceImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月11日下午4:06:59
 */
@Component
@Transactional("transactionManager")
public class ModuleServiceImpl implements ModuleService {

	private static Logger logger = Logger.getLogger(ModuleServiceImpl.class);

	@Autowired
	private IModuleDao moduleDao;
	public void setModuleDao(IModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}
	@Autowired
	private IFunctionDao functionDao;
	public void setFunctionDao(IFunctionDao functionDao) {
		this.functionDao = functionDao;
	}

	@Override
	public void add(Module module) {
		module.setIs_del(0);
		module.setInsert_time(new Date());
		try {
			moduleDao.saveOrUpdate(module);
		} catch (Exception e) {
			logger.error("create Module error");	
		}
	}

	@Override
	public Integer delete(Integer id) {
		Module module = getById(id);
		module.setIs_del(1);
		module.setDel_time(new Date());
		try {
			moduleDao.update(module);
		} catch (Exception e) {
			logger.error("delete Module error");
		}
		return null;
	}

	@Override
	public Module update(Module module) {
		module.setUpdate_time(new Date());
		try {
			moduleDao.update(module);
		} catch (Exception e) {
			logger.error("update Module error");
		}
		return null;
	}

	@Override
	public Module getById(Integer id) {
		Module module = new Module();
		try {
			module = moduleDao.get(Module.class, id);
		} catch (Exception e) {
			logger.error("get Module by ID error");
		}
		return module;
	}

	@Override
	public List<Module> getList(Module module) {
		List<Module> list = new ArrayList<Module>();
		try {
			list = moduleDao.getList(module);
		} catch (Exception e) {
			logger.error("get ModuleList error");
		}
		return list;
	}

	@Override
	public Paging<Module> getListWithPaging(Paging<Module> paging, Module module) {
		try {
			paging = moduleDao.getListWithPaging(paging,module);
		} catch (Exception e) {
			logger.error("get ModuleList WithPaging error");
		}
		return paging;
	}

	@Override
	public Paging<Module> getQueryListWithPaging(Paging<Module> paging,
			Module module) {
		try {
			paging = moduleDao.getQueryListWithPaging(paging,module);
		} catch (Exception e) {
			logger.error("get QueryModuleList With Paging error");
		}
		return paging;
	}

	@Override
	public Paging<Module> setFunctionsForModule(Paging<Module> page) {
		List<Module> mList = page.getData();
		for (Module module : mList) {
			module.setFunctionList(functionDao.getListByModule(module));
		}
		page.setData(mList);
		return page;
	}

	@Override
	public boolean checkName(String name) {
		return moduleDao.checkName(name);
	}

	@Override
	public void updateOrder() {
		moduleDao.updateOrder();
	}

	@Override
	public List<Module> getModuleForMenu(Integer fun_id) {
		return moduleDao.getModuleForMenu(fun_id);
	}

}
