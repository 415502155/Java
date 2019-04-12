package com.shihy.springboot.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/***
 * @Title: springstarter
 * @author shy
 * @Description 统一异常处理类
 * @data 2019年4月1日 下午2:25:09
 *
 */
//@ControllerAdvice注解是用来配置控制器通知的
//@ControllerAdvice的annotations属性值为RestController.class，
//也就是只有添加了@RestController注解的控制器才会进入全局异常处理
@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class RestExceptionHandler {
	//来配置需要拦截的异常类型，默认是全局类型
	@ExceptionHandler
	//配置遇到该异常后返回数据时的StatusCode的值
	@ResponseStatus//(code = HttpStatus.OK, reason = "")
	public ApiResult runTimeExceptionHandler(Exception exception) {
		return ApiResultGenerator.errorApiResult(exception.getMessage(), exception);
	}
}
