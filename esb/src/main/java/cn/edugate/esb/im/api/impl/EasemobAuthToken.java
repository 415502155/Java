package cn.edugate.esb.im.api.impl;

import org.springframework.stereotype.Repository;

import cn.edugate.esb.im.api.AuthTokenAPI;
import cn.edugate.esb.im.TokenUtil;

@Repository
public class EasemobAuthToken implements AuthTokenAPI{

	@Override
	public Object getAuthToken(){
		return TokenUtil.getAccessToken();
	}
}
