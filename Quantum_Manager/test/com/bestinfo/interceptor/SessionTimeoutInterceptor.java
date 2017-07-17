package com.bestinfo.interceptor;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author lvchangrong
 */
public class SessionTimeoutInterceptor implements HandlerInterceptor {

    private final Logger logger = Logger.getLogger(SessionTimeoutInterceptor.class);
    public String[] allowUrls;//还没发现可以直接配置不拦截的资源，所以在代码里面来排除  

    public void setAllowUrls(String[] allowUrls) {
        this.allowUrls = allowUrls;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
        if (null != allowUrls && allowUrls.length >= 1) {
            for (String url : allowUrls) {
                if (requestUrl.contains(url)) {
                    return true;
                }
            }
        }
        //logger.info("拦截到的请求 " + requestUrl);
        if (request.getSession().getAttribute("user") != null) {
            return true;  //返回true，则这个方面调用后会接着调用postHandle(),  afterCompletion()  往下走
        } else {
            // session为空  跳转到登录页面  
            String CONTENT_TYPE = "text/html; charset=GBK";
            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<script>");
            out.println("alert('session为空，请重新登录!');");
            out.println("window.open (' " + request.getContextPath() + "/user/2login  ','_top')");
            out.println("</script>");
            out.println("</html>");
            logger.info("session为空，返回登录页面!");
////            response.sendRedirect(request.getContextPath() + "/user/2login");
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
    }
}
