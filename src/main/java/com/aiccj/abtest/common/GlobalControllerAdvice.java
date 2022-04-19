package com.aiccj.abtest.common;

import com.aiccj.abtest.common.exception.BusinessMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@Slf4j
@ControllerAdvice("io.roxe.user.controller")
public class GlobalControllerAdvice implements ResponseBodyAdvice<Object> {

	/**
	 * 处理其他异常
	 */
	@ExceptionHandler(value = Throwable.class)
	@ResponseBody
	public Resp<Void> exceptionHandler(HttpServletRequest req, HttpServletResponse resp, Throwable e) {
		log.error("未知异常！原因是:", e);
		return Resp.createFailResp(BusinessMsg.SYS_ERROR);
	}


	@Override
	public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object o,
								  MethodParameter methodParameter,
								  MediaType mediaType,
								  Class<? extends HttpMessageConverter<?>> aClass,
								  ServerHttpRequest serverHttpRequest,
								  ServerHttpResponse serverHttpResponse) {

		Resp<Object> resp;
		if (o instanceof Resp){
			resp = (Resp<Object>) o;
			return resp;
		} else{
			return o;
		}
	}
}
