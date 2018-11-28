package sng.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SsoFilter implements Filter {
	
	private String sso_url;
	private String sso_key;	
	private Set<String> prefixIignores = new HashSet<String>();
	public String getSso_key() {
		return sso_key;
	}
	public void setSso_key(String sso_key) {
		this.sso_key = sso_key;
	}
	public String getSso_url() {
		return sso_url;
	}

	public void setSso_url(String sso_url) {
		this.sso_url = sso_url;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		String cp = filterConfig.getServletContext().getContextPath(); 
        this.sso_url = filterConfig.getInitParameter("sso_url");  
        this.sso_key = filterConfig.getInitParameter("sso_key");  
        String ignoresParam = filterConfig.getInitParameter("ignores");  
        String[] ignoreArray = ignoresParam.split(",");  
        for (String s : ignoreArray) {  
            prefixIignores.add(cp + s);  
        }
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest hsreq=(HttpServletRequest) servletRequest; 
		HttpServletResponse hsres=(HttpServletResponse) servletResponse; 
		HttpSession session = hsreq.getSession();
		hsreq.setAttribute("service", this.sso_key);
		hsreq.setAttribute("sso_url", this.sso_url);
		hsreq.setAttribute("login_url", this.sso_url+"/appLogin");
		if (canIgnore(hsreq)) {  
			filterChain.doFilter(servletRequest, servletResponse);
            return;  
        } 
		if(session.getAttribute("token")!=null&&session.getAttribute("udid")!=null){
			filterChain.doFilter(servletRequest, servletResponse);
		}else{
			String uri=hsreq.getRequestURI();
			String url = sso_url+"/validtoken"+"?service="+this.sso_key+"&uri="+uri+"&jsessionid="+session.getId();
			hsres.sendRedirect(url);
			hsres.flushBuffer();
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	
	private boolean canIgnore(HttpServletRequest request) {  
        String url = request.getRequestURI();  
        for (String ignore : prefixIignores) {  
            if (url.startsWith(ignore)) {  
                return true;  
            }  
        }  
        return false;  
    }

}
