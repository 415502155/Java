package sng.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerExceptionResolverComposite;

import sng.pojo.Result;
import sng.util.JsonUtil;

public class ExceptionHandler extends HandlerExceptionResolverComposite {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		// TODO Auto-generated method stub
		PrintWriter writer;
		try {
			writer = response.getWriter();
			Result<String> result = new Result<String>();

			String type = ex.getClass().getName();
			switch (type) {
				case "cn.edugate.base.util.HttpRequest$HttpRequestException":
					result.setCode(9099);
					result.setMessage("网络异常，请检查网络连接");
					break;
				case "cn.edugate.base.exception.EsbException":
					EsbException ext = (EsbException) ex;
					result.setCode(ext.getCode());
					result.setMessage(ex.getMessage());
					break;

				default:
					result.setCode(9098);
					result.setMessage("错误异常，请稍后重试");
					break;
			}

			String json = JsonUtil.toJson(result);
			writer.write(json);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
