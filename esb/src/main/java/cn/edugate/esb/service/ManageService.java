package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.pojo.BackMenuPojo;

/**
 * 
 * @Name: 后台操作服务
 * @Description: 
 * @author HuangCf 
 * @date  2014-5-21
 * @version V1.0
 */
public interface ManageService {


	List<BackMenuPojo> getBackMenus();

	
}
