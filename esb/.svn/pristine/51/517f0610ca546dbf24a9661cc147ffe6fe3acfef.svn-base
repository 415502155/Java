package cn.edugate.esb.authentication;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.springframework.security.core.GrantedAuthority;

@JsonAutoDetect
public class EduGrantedAuthority implements GrantedAuthority,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8576602437437623469L;
	private String authority;
	private int code;
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return authority;
	}

}
