package cn.edugate.esb.web.manage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edugate.esb.entity.RoleCode;
import cn.edugate.esb.service.RoleCodeService;
import cn.edugate.esb.util.Constant;

/**
 * 
 * @Name: 角色管理controller
 * @Description: 维护角色(添加和删除角色),维护角色的权限。
 * @date  2013-4-11
 * @version V1.0
 */
@Controller
@RequestMapping("/manage/roleCode")
public class MroleCodeController {
	
	static Logger logger=LoggerFactory.getLogger(MroleCodeController.class);

	@Autowired
	private RoleCodeService roleCodeService;

	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam(value="name",defaultValue="") String name,@RequestParam(value="code",defaultValue="") String code,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/rolecode/list");
		RoleCode roleCode = new RoleCode();
		roleCode.setCode(code);
		roleCode.setName(name);
		List<RoleCode> list = roleCodeService.getList(roleCode);
		Map<Integer,RoleCode> map = new HashMap<Integer,RoleCode>();
		for (RoleCode rc1 : list) {
			if(Constant.NO.equals(rc1.getParent_id())){
				map.put(rc1.getRole_code_id(), rc1);
			}
		}
		for (RoleCode rc2 : list) {
			if(!Constant.NO.equals(rc2.getParent_id()) && map.containsKey(rc2.getParent_id())){
				List<RoleCode> parentList = map.get(rc2.getParent_id()).getList();
				if(null==parentList||0==parentList.size()){
					parentList = new ArrayList<RoleCode>();
				}
				parentList.add(rc2);
				map.get(rc2.getParent_id()).setList(parentList);
			}
		}
		Collection<RoleCode> valueCollection = map.values();
	    List<RoleCode> result = new ArrayList<RoleCode>(valueCollection);
		mav.addObject("list", result);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}

	
}
