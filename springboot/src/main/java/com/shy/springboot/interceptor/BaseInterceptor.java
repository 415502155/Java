package com.shy.springboot.interceptor;

import java.io.OutputStream;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.shy.springboot.dao.UserDao;
import com.shy.springboot.entity.User;
import com.shy.springboot.utils.Constant;
import com.shy.springboot.utils.JsonUtils;
import com.shy.springboot.utils.ReturnMsg;
import com.shy.springboot.utils.ReturnResult;
import com.shy.springboot.utils.StaticVariable;

import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 拦截
 *
 */
@Component
@Slf4j
//@PropertySource({"classpath:config.properties"})
public class BaseInterceptor implements HandlerInterceptor {
	
	//@Value("${TOKEN_EXPIR_TIME}")
	//private String TOKEN_EXPIR_TIME;
	
	@Resource
	private UserDao userDao;
	
	 /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object object) throws Exception {
    	//默认通过请求参数
    	boolean flag = true;
    	String uri = request.getRequestURI();
        log.info("用户访问地址: {}, 来路地址: {}", uri);
        String token = request.getParameter("token");
        if (StringUtils.isBlank(token)) {
        	log.info("请求参数 ：token 不能为空");
        	flag = false;
        	this.returnErrorResponse(response, ReturnResult.error(ReturnMsg.UPLOAD_FILE_EX3.getCode(), ReturnMsg.UPLOAD_FILE_EX3.getMsg()));
        	return flag;
        }
        String[] split = token.split("_");
        log.info("split :" + split.length);
        if (split.length < Constant.ONE) {
        	log.info("请求参数 ：token 格式不正确");
        	flag = false;
        	this.returnErrorResponse(response, ReturnResult.error(ReturnMsg.UPLOAD_FILE_EX3.getCode(), ReturnMsg.UPLOAD_FILE_EX3.getMsg()));
        	return flag;
        }
        //校验token是否正确
        String userId = String.valueOf(split[0]);
        log.info("用户ID ：{ userId :" + userId + " }");
        if (StringUtils.isBlank(userId)) {
        	log.info("请求参数 ：token 格式不正确");
        	flag = false;
        	this.returnErrorResponse(response, ReturnResult.error(ReturnMsg.UPLOAD_FILE_EX3.getCode(), ReturnMsg.UPLOAD_FILE_EX3.getMsg()));
        	return flag;
        } else {
        	//① 根据数据库中的token对比 
        	//Map<String, Object> userInfo = userService.getUserInfoByUserId(Integer.parseInt(userId));
        	//Map userInfo = userService.getUserInfo(userId);
        	User user = userDao.getById(Integer.parseInt(userId));
        	log.info("用户信息 ：" + JSONObject.toJSONString(user));
        	if (user == null) {
        		//log.info("请求参数 ：redis中不存在用户信息，请重新登陆; ");
        		flag = false;
        		this.returnErrorResponse(response, ReturnResult.error(ReturnMsg.UPLOAD_FILE_EX3.getCode(), ReturnMsg.UPLOAD_FILE_EX3.getMsg()));
        		return flag;
        	} else {
        		String userToken = user.getToken();
        		log.info("根据用户ID获取 ： { userToken :" + userToken + " }");
        		if (token.equals(userToken)) {
        			//没配置redis 暂时不做登陆失效
        			/***
        			 * 每次请求都会重置用户登录信息的过期时间
        			 */
        			//userService.expire("user_id", Long.parseLong(TOKEN_EXPIR_TIME));
        			log.info("请求参数 ：校验完成; 通过");
        		} else {
        			log.info("请求参数 ：token不正确");
        			log.info("token :" + token + " userToken :" + userToken);
        			flag = false;
            		this.returnErrorResponse(response, ReturnResult.error(ReturnMsg.UPLOAD_FILE_EX3.getCode(), ReturnMsg.UPLOAD_FILE_EX3.getMsg()));
            		return flag;
        		}
        	}
        }
        
        return flag;
    }

    /**
             * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object object, ModelAndView mv)
            throws Exception {
        // 可以修改返回的数据
    }

    /**
 	 * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行
           * （主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object object, Exception ex)
            throws Exception {

    }

    private void returnErrorResponse(HttpServletResponse response, ReturnResult result) throws Exception {
        OutputStream out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
