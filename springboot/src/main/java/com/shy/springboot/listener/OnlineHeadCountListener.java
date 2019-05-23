package com.shy.springboot.listener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/***
 * 
 * @author sjwy-0001
 * @deprecated 统计在线人数	
 */
@WebListener
public class OnlineHeadCountListener implements HttpSessionListener {
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		ServletContext context = se.getSession().getServletContext();
		if (context.getAttribute("count") == null) {
			context.setAttribute("count", 0);
			context.setAttribute("maxCount", 0);
		} else {
			int count = (Integer) context.getAttribute("count");
			int maxCount = (Integer) context.getAttribute("maxCount");
			count++;
			context.setAttribute("count", count);//在綫人數
			if (count > maxCount) {
				maxCount = count;
				context.setAttribute("maxCount", maxCount);//在綫的最大人數
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				context.setAttribute("date", df.format(new Date()));
			}			
		}
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		ServletContext context = se.getSession().getServletContext();
		if (context.getAttribute("count") == null) {
			context.setAttribute("count", 0);
		} else {
			int count = (Integer) context.getAttribute("count");
			if (count < 1) {
				count = 1;
			}
			context.setAttribute("count", count - 1);
		}
	}
}
