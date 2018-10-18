package com.gm.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.Before;

@SuppressWarnings({ "serial", "rawtypes" })
@Controller
@Scope("prototype")
@ParentPackage(value = "struts-default")
@InterceptorRefs({ @InterceptorRef("annotationWorkflow"),
		@InterceptorRef("defaultStack") })
public class BaseAction extends ActionSupport {

	public Logger log = Logger.getLogger(getClass()); // 日志

	public BaseAction() {
	}

	@Before
	public void interceptor() throws Exception {
	}

	/* 得到web跟目录路径 */
	public String getWebRootpath() {
		return ServletActionContext.getServletContext().getRealPath("/");
	}

	/* 得到ServletContext */
	public ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected HttpSession getSession() {
		return ServletActionContext.getRequest().getSession(true);
	}

	protected Object getSession(String key) {
		return getSession().getAttribute(key);
	}

	/**
	 * 返回当前url
	 */
	public String getUrl() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String nextPage = request.getScheme() + "://";
		nextPage += request.getHeader("host");
		nextPage += request.getRequestURI();
		if (request.getQueryString() != null) {
			nextPage += "?" + request.getQueryString();
		}
		return nextPage;

	}

	/**
	 * 获取登录用户session信息
	 */
	protected Map getUserInfo() {
		Map map = new HashMap();
		HttpServletRequest request = getRequest();
		if (request.getSession().getAttribute("userInfo") != null) {
			map = (Map) request.getSession().getAttribute("userInfo");
		}
		return map;
	}

	/**
	 * 直接向客户端返回Content字符串，不用通过View页面渲染.
	 */
	protected void sendText(String content) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(content);
			writer.flush();
		} catch (IOException e) {
		}
	}
	
	/**
	 * 直接向客户端返回Content字符串，不用通过View页面渲染.
	 */
	protected void sendText2(String content) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/json;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(content);
			writer.flush();
		} catch (IOException e) {
		}
	}

	/**
	 * 跳转到一个url地址.
	 */
	protected void sendUrl(String url) throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.sendRedirect(url);
	}

}
