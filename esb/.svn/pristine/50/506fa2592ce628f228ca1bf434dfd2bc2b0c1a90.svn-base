package cn.edugate.esb.authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cn.edugate.esb.entity.Member;
import cn.edugate.esb.pojo.LoginModel;
import cn.edugate.esb.service.LoginService;

public class EsbAuthenticationProvider implements AuthenticationProvider {
	private LoginService loginService;
	
	@Autowired
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		PwdAuthenticationToken ldws = (PwdAuthenticationToken)authentication;
		LoginModel response = loginService.login(ldws.getUserName(), ldws.getPassword());
        UsernamePasswordAuthenticationToken result=null;
        if(response.isSuccess()){
        	Member user = new Member();
            user.setUsername(ldws.getUserName());
            user.setPassword(ldws.getPassword()); 
            user.setSession(ldws.getUserName()+response.getCurrentSession());	   
            user.setFullname(response.getFullName());
            
	        result = new UsernamePasswordAuthenticationToken(
	        		user, authentication.getCredentials(),user.getAuthorities());
        }else{        	
	        throw new UsernameNotFoundException(response.getError());
        }
        return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}

}
