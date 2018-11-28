package cn.edugate.esb.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


public class EsbAuthenticationFailureHandler implements
		AuthenticationFailureHandler {	 
	private static Logger logger = Logger.getLogger(EsbAuthenticationFailureHandler.class);  
    private String defaultFailureUrl;  
    private boolean forwardToDestination = false;  
    private boolean allowSessionCreation = true;  
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();  
    @Value(value = "/manage")
    private String LOCAL_SERVER_URL;  
      
    public EsbAuthenticationFailureHandler() {  
    }  
  
    public EsbAuthenticationFailureHandler(String defaultFailureUrl) {  
        setDefaultFailureUrl(defaultFailureUrl);  
    }  
  
    /** 
     * Performs the redirect or forward to the {@code defaultFailureUrl} if set, otherwise returns a 401 error code. 
     * <p> 
     * If redirecting or forwarding, {@code saveException} will be called to cache the exception for use in 
     * the target view. 
     */  
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,  
            AuthenticationException exception) throws IOException, ServletException {  
        //认证失败区别前后台：LOGIN URL  
        if(request.getParameter("spring-security-redirect") != null){  
              request.getSession().setAttribute("callUrlFailure", request.getParameter("spring-security-redirect"));  
        }  
        //若有loginUrl 则重定向到后台登录界面  
        if(request.getParameter("loginUrl") != null && !"".equals(request.getParameter("loginUrl"))){  
            defaultFailureUrl = LOCAL_SERVER_URL+"/backlogin.html?validated=false";  
        }  
        //defaultFailureUrl 默认的认证失败回调URL  
        String requestType = request.getHeader("X-Requested-With"); 
        boolean isAjax = false;
        if("XMLHttpRequest".equals(requestType)){
        	isAjax = true;
        }
        if (isAjax) {  
        	JSONObject json = new JSONObject(); 
        	response.setContentType("application/json; charset=UTF-8");
        	PrintWriter responseWriter = response.getWriter();        	
        	try {
        		json.put("success", false);
				json.put("code", exception.getMessage());
				json.put("session", request.getRequestedSessionId());
				json.write(responseWriter);
			} catch (JSONException e) {
				logger.error("LdwsAuthenticationFailureHandler====", e);
			}
        	Cookie cookie=new Cookie("webclientSession",request.getSession().getId());
        	cookie.setPath("/");
        	response.addCookie(cookie);
        	
        	responseWriter.flush();
        	responseWriter.close();  
        } else {  
            saveException(request, exception);  
            if (forwardToDestination) {  
                logger.debug("Forwarding to " + defaultFailureUrl);  
                request.getRequestDispatcher(defaultFailureUrl).forward(request, response);  
            } else {  
                logger.debug("Redirecting to " + defaultFailureUrl);  
                redirectStrategy.sendRedirect(request, response, defaultFailureUrl);  
            }  
        }  
    }  
  
    /** 
     * Caches the {@code AuthenticationException} for use in view rendering. 
     * <p> 
     * If {@code forwardToDestination} is set to true, request scope will be used, otherwise it will attempt to store 
     * the exception in the session. If there is no session and {@code allowSessionCreation} is {@code true} a session 
     * will be created. Otherwise the exception will not be stored. 
     */  
    protected final void saveException(HttpServletRequest request, AuthenticationException exception) {  
        if (forwardToDestination) {  
            request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);  
        } else {  
            HttpSession session = request.getSession(false);  
  
            if (session != null || allowSessionCreation) {  
                request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);  
            }  
        }  
    }  
  
    /** 
     * The URL which will be used as the failure destination. 
     * 
     * @param defaultFailureUrl the failure URL, for example "/loginFailed.jsp". 
     */  
    public void setDefaultFailureUrl(String defaultFailureUrl) {  
        this.defaultFailureUrl = defaultFailureUrl;  
    }  
  
    protected boolean isUseForward() {  
        return forwardToDestination;  
    }  
  
    /** 
     * If set to <tt>true</tt>, performs a forward to the failure destination URL instead of a redirect. Defaults to 
     * <tt>false</tt>. 
     */  
    public void setUseForward(boolean forwardToDestination) {  
        this.forwardToDestination = forwardToDestination;  
    }  
  
    /** 
     * Allows overriding of the behaviour when redirecting to a target URL. 
     */  
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {  
        this.redirectStrategy = redirectStrategy;  
    }  
  
    protected RedirectStrategy getRedirectStrategy() {  
        return redirectStrategy;  
    }  
  
    protected boolean isAllowSessionCreation() {  
        return allowSessionCreation;  
    }  
  
    public void setAllowSessionCreation(boolean allowSessionCreation) {  
        this.allowSessionCreation = allowSessionCreation;  
    }  

}
