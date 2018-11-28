package cn.edugate.esb.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

public class MyAccessDeniedHandler extends AccessDeniedHandlerImpl {
	private String accessDeniedUrl;

	public MyAccessDeniedHandler() {

	}

	public MyAccessDeniedHandler(String accessDeniedUrl) {

		this.accessDeniedUrl = accessDeniedUrl;

	}

	@Override
	public void handle(HttpServletRequest request,

	HttpServletResponse response,

	AccessDeniedException accessDeniedException) throws IOException,

	ServletException {

		JSONObject json = new JSONObject(); 
    	response.setContentType("application/json; charset=UTF-8");
    	PrintWriter responseWriter = response.getWriter();        	
    	try {
    		json.put("success", false);
			json.put("message", "用户没有操作权限");  				
			json.put("code", "403");
			json.put("data", "");
//			json.put("session", request.getRequestedSessionId());
			json.write(responseWriter);
		} catch (JSONException e) {
			logger.error("LdwsAuthenticationFailureHandler====", e);
		}
    	
    	responseWriter.flush();
    	responseWriter.close();

	}

	public String getAccessDeniedUrl() {

		return accessDeniedUrl;

	}

	public void setAccessDeniedUrl(String accessDeniedUrl) {

		this.accessDeniedUrl = accessDeniedUrl;

	}
}
