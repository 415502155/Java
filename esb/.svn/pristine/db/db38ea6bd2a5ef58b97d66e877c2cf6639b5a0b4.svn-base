package cn.edugate.esb.authentication;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.PortMapperImpl;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.security.web.util.UrlUtils;
import cn.edugate.esb.Result;
import cn.edugate.esb.util.Util;

/**
 * 
 *
 */
public class Http401UnauthorizedEntryPoint implements
		AuthenticationEntryPoint {
	static Logger logger=LoggerFactory.getLogger(Http401UnauthorizedEntryPoint.class);
	
	private PortMapper portMapper = new PortMapperImpl();

    private PortResolver portResolver = new PortResolverImpl();
	
	private String loginFormUrl="/manage/login";

    private boolean forceHttps = false;

    private boolean useForward = false;
    
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	private Util util;	
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}
	
	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		boolean isForm = false;
		String cpath = request.getContextPath();
        String path = request.getRequestURI();
        if(!path.startsWith(cpath+"/"+"api")){
        	isForm = true;
        }
        
        if (!isForm) {		
			if (logger.isDebugEnabled()) {
	            logger.debug("Pre-authenticated entry point called. Rejecting access");
	        }
			logger.error("jsessionid======================={}",request.getSession().getId());
			logger.error("path======================={}",request.getPathInfo());
			logger.error("message======================={}",authException.getMessage());
			
			Result<String> result= new Result<String>();
			result.setSuccess(false);	
			result.setCode(401);
			result.setMessage("用户未认证，请求失败");
			String json=this.util.getJSONFromPOJO(result);
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=utf-8");
			
			ServletOutputStream output=response.getOutputStream();
			output.println(json);
			output.flush();
//			PrintWriter responseWriter = response.getWriter();
//			responseWriter.write(json);
//			response.getWriter().print(json);
//			responseWriter.flush();
//			responseWriter.close();
        }else{
        	String redirectUrl = null;

            if (useForward) {

                if (forceHttps && "http".equals(request.getScheme())) {
                    // First redirect the current request to HTTPS.
                    // When that request is received, the forward to the login page will be used.
                    redirectUrl = buildHttpsRedirectUrlForRequest(request);
                }

                if (redirectUrl == null) {
                    String loginForm = determineUrlToUseForThisRequest(request, response, authException);

                    if (logger.isDebugEnabled()) {
                        logger.debug("Server side forward to: " + loginForm);
                    }

                    RequestDispatcher dispatcher = request.getRequestDispatcher(loginForm);

                    dispatcher.forward(request, response);

                    return;
                }
            } else {
                // redirect to login page. Use https if forceHttps true

                redirectUrl = buildRedirectUrlToLoginPage(request, response, authException);

            }

            redirectStrategy.sendRedirect(request, response, redirectUrl);
        }
	}
	
	
	/**
     * Builds a URL to redirect the supplied request to HTTPS. Used to redirect the current request
     * to HTTPS, before doing a forward to the login page.
     */
    protected String buildHttpsRedirectUrlForRequest(HttpServletRequest request)
            throws IOException, ServletException {

        int serverPort = portResolver.getServerPort(request);
        Integer httpsPort = portMapper.lookupHttpsPort(Integer.valueOf(serverPort));

        if (httpsPort != null) {
            RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
            urlBuilder.setScheme("https");
            urlBuilder.setServerName(request.getServerName());
            urlBuilder.setPort(httpsPort.intValue());
            urlBuilder.setContextPath(request.getContextPath());
            urlBuilder.setServletPath(request.getServletPath());
            urlBuilder.setPathInfo(request.getPathInfo());
            urlBuilder.setQuery(request.getQueryString());

            return urlBuilder.getUrl();
        }

        // Fall through to server-side forward with warning message
        logger.warn("Unable to redirect to HTTPS as no port mapping found for HTTP port " + serverPort);

        return null;
    }
    
    /**
     * Allows subclasses to modify the login form URL that should be applicable for a given request.
     *
     * @param request   the request
     * @param response  the response
     * @param exception the exception
     * @return the URL (cannot be null or empty; defaults to {@link #getLoginFormUrl()})
     */
    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) {

        return getLoginFormUrl();
    }
	
    protected String buildRedirectUrlToLoginPage(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) {

        String loginForm = determineUrlToUseForThisRequest(request, response, authException);

        if (UrlUtils.isAbsoluteUrl(loginForm)) {
            return loginForm;
        }

        int serverPort = portResolver.getServerPort(request);
        String scheme = request.getScheme();

        RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();

        urlBuilder.setScheme(scheme);
        urlBuilder.setServerName(request.getServerName());
        urlBuilder.setPort(serverPort);
        urlBuilder.setContextPath(request.getContextPath());
        urlBuilder.setPathInfo(loginForm);

        if (forceHttps && "http".equals(scheme)) {
            Integer httpsPort = portMapper.lookupHttpsPort(Integer.valueOf(serverPort));

            if (httpsPort != null) {
                // Overwrite scheme and port in the redirect URL
                urlBuilder.setScheme("https");
                urlBuilder.setPort(httpsPort.intValue());
            } else {
                logger.warn("Unable to redirect to HTTPS as no port mapping found for HTTP port " + serverPort);
            }
        }

        return urlBuilder.getUrl();
    }
	
    public String getLoginFormUrl() {
        return loginFormUrl;
    }
}
