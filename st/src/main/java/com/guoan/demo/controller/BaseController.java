package com.guoan.demo.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseController {
	protected static Log logger = LogFactory.getLog(BaseController.class);
	protected ObjectMapper mapper = new ObjectMapper();
	/**
	 * 打印json格式
	 * 
	 * @param object
	 * @param response
	 */
	public void printJson(Object object, HttpServletResponse response) {
		OutputStream out = null;
		try {
			StringWriter stringWriter = new StringWriter();
			mapper.writeValue(stringWriter, object);
			response.setContentType("text/html;charset=utf-8");
			out = response.getOutputStream();
			out.write(stringWriter.toString().getBytes("UTF-8"));
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (null != out) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}
}
