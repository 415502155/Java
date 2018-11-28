package cn.edugate.esb.imp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.IAdminDao;
import cn.edugate.esb.entity.Admin;
import cn.edugate.esb.pojo.LoginModel;
import cn.edugate.esb.service.LoginService;
import cn.edugate.esb.util.Utils;

@Component
@Transactional("transactionManager")
public class LoginImpl implements LoginService {
	private static Logger logger = Logger.getLogger(LoginImpl.class);
	
	private IAdminDao adminDao;

	@Autowired
	public void setAdminDao(IAdminDao adminDao) {
		this.adminDao = adminDao;
	}

	@Override
	public LoginModel login(String userName, String password) {
		// TODO Auto-generated method stub
		Admin user = this.adminDao.getUserByName(userName);
		LoginModel model = new LoginModel();
		if(user!=null){
			if(Utils.MD5(password).equals(user.getPassword())){
				model.setSuccess(true);
				model.setFullName(user.getUsername());			
			}else{
				model.setError("密码错误");
			}
		}else{
			model.setError("用户名不存在");
		}
		return model;
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		logger.info("logout");
	}


}
