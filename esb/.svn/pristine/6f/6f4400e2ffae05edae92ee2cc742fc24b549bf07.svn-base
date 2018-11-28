package cn.edugate.esb.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.IFunctionDao;
import cn.edugate.esb.entity.Function;
import cn.edugate.esb.service.FunctionService;
import cn.edugate.esb.util.Paging;

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
public class FunctionServiceImpl implements FunctionService{

	private static Logger logger = Logger.getLogger(FunctionServiceImpl.class);
	
	@Autowired
	private IFunctionDao functionDao;
	
	@Override
	public Integer add(Function function) {
		function.setIs_del(0);
		function.setInsert_time(new Date());
		try {
			functionDao.save(function);
			return function.getFun_id();
		} catch (Exception e) {
			logger.error("add Function error");	
			return null;
		}
	}
	
	@Override
	public List<Function> getFunctionForMenu(Integer fun_id) {
		try {
			return functionDao.getFunctionForMenu(fun_id);
		} catch (Exception e) {
			logger.error("add Function error");	
			return null;
		}
	}

	@Override
	public Integer delete(Integer id) {
		Function function = getById(id);
		function.setIs_del(1);
		function.setDel_time(new Date());
		try {
			functionDao.update(function);
		} catch (Exception e) {
			logger.error("delete Function error");
		}
		return null;
	}

	@Override
	public Function update(Function function) {
		function.setUpdate_time(new Date());
		try {
			functionDao.update(function);
		} catch (Exception e) {
			logger.error("update Function error");
		}
		return null;
	}

	@Override
	public Function getById(Integer id) {
		Function function = new Function();
		try {
			function = functionDao.getByid(id);
		} catch (Exception e) {
			logger.error("get Function by ID error");
		}
		return function;
	}

	@Override
	public List<Function> getList(Function function) {
		List<Function> list = new ArrayList<Function>();
		try {
			list = functionDao.getList(function);
		} catch (Exception e) {
			logger.error("get FunctionList error");
		}
		return list;
	}

	@Override
	public Paging<Function> getListWithPaging(Paging<Function> paging, Function function) {
		try {
			paging = functionDao.getListWithPaging(paging,function);
		} catch (Exception e) {
			logger.error("get FunctionList With Paging error");
		}
		return paging;
	}

	@Override
	public boolean checkName(String name, String version) {
		return functionDao.checkName(name, version);
	}

	@Override
	public void updateOrder(Integer moduleID) {
		functionDao.updateOrder(moduleID);
	}

	@Override
	public List<Function> getFunctionForOrg(String org_id) {
		// TODO Auto-generated method stub
		try {
			return functionDao.getFunctionForOrg(org_id);
		} catch (Exception e) {
			logger.error("add Function error");	
			return null;
		}
	}

}
