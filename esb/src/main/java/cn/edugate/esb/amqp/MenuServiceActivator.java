package cn.edugate.esb.amqp;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;

import cn.edugate.esb.entity.Function;
import cn.edugate.esb.entity.Module;
import cn.edugate.esb.service.FunctionService;
import cn.edugate.esb.service.MenuService;
import cn.edugate.esb.service.ModuleService;

public class MenuServiceActivator {

	private static Log logger = LogFactory.getLog(MenuServiceActivator.class);
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private FunctionService functionService;
	@Autowired
	private MenuService menuService;
	
	public void logXml(Message<?> msg) throws Exception {
		if (msg.getPayload() instanceof Integer){
			Integer functionId = (Integer) msg.getPayload();
			List<Module> orgModuleList = moduleService.getModuleForMenu(functionId); 
			if(null!=orgModuleList&&orgModuleList.size()!=0){
				menuService.createModuleMenus(orgModuleList);
			}
			//创建二级菜单
			List<Function> orgFunctionList = functionService.getFunctionForMenu(functionId);
			if(null!=orgFunctionList&&orgFunctionList.size()!=0){
				menuService.createFunctionMenus(orgFunctionList);
			}
		}else{
			logger.info(" PAYLOAD ERROR ###" + msg.getPayload());
		}
	}
}
