package com.aiccj.abtest.common;

import com.aiccj.abtest.common.exception.BusinessEnumIfc;
import com.aiccj.abtest.common.exception.BusinessException;
import com.aiccj.abtest.common.exception.BusinessMsg;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
public final class WebHandlerExceptionResolver implements HandlerExceptionResolver, Ordered,
    InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private SerializerFeature[] serializerFeatures;

    private final Map<String, BusinessEnumIfc> errorCodeMapping = new HashMap<>();

    @Override
    public int getOrder() {
        return -1;
    }

    public WebHandlerExceptionResolver(SerializerFeature[] serializerFeatures) {
        this.serializerFeatures = serializerFeatures;
        if (this.serializerFeatures == null) {
            this.serializerFeatures = new SerializerFeature[0];
        }
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse, Object handler,
                                         Exception e) {
        boolean loged = false;
        try {
            if (e instanceof BusinessException) {
                logger.info("出现业务异常: \n{}", ExceptionPrinter.exConvertToStr(e, 1));
                BusinessEnumIfc businessEnumIfc = ((BusinessException) e).getBusinessEnumIfc();
                if (businessEnumIfc == null) {
                    logger.error("出现业务异常，但是 BusinessEnumIfc 为空！");
                    writeResult(httpServletResponse, Resp.createFailResp());
                    return new ModelAndView();
                }
                writeResult(httpServletResponse, Resp.createFailResp(businessEnumIfc));
                return new ModelAndView();
            }
            if (e instanceof MethodArgumentNotValidException) {
                MethodArgumentNotValidException methodArgumentNotValidException =
                    (MethodArgumentNotValidException) e;
                BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
                FieldError fieldError = bindingResult.getFieldError();
                String defaultMessage = fieldError.getDefaultMessage();
                BusinessEnumIfc businessEnumIfc = errorCodeMapping.get(defaultMessage);
                if (businessEnumIfc == null) {
                    logger.error("参数验证失败-未收录: \n{}", ExceptionPrinter.exConvertToStr(e, 3));
                    businessEnumIfc = BusinessMsg.SYS_ERROR;
                } else {
                    logger.error("参数验证失败: {} - {}", businessEnumIfc.getBusinessRespCode(),
                        businessEnumIfc.getRespMsg());
                }
                writeResult(httpServletResponse, Resp.createFailResp(businessEnumIfc));
                return new ModelAndView();
            }
            logger.error("收到了全局异常！\n{}", ExceptionPrinter.exConvertToStr(e, 2));
            loged = true;

            StringBuffer requestURL = httpServletRequest.getRequestURL();
            String fullUrl = StringUtils.isEmpty(httpServletRequest.getQueryString()) ?
                requestURL.toString() :
                requestURL.append("?").append(httpServletRequest.getQueryString()).toString();
            logger.error("全局异常请求地址信息: {}", fullUrl);
            Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
            StringBuffer paramsBuffer = new StringBuffer();
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                paramsBuffer.append(name).append("=").append(httpServletRequest.getParameter(name)).append("&");
            }
            if (paramsBuffer.length() > 0) {
                String formStr = paramsBuffer.deleteCharAt(paramsBuffer.length() - 1).toString();
                logger.error("全局异常请求表单信息: {}", formStr);
            }
            writeResult(httpServletResponse, Resp.createFailResp());
        } catch (Exception exception) {
            logger.error("处理全局异常时出现错误！", exception);
            if (!loged) {
                logger.error("原异常信息: \n{}", ExceptionPrinter.exConvertToStr(e, 2));
            }

        }
        return new ModelAndView();
    }

    private void writeResult(HttpServletResponse httpServletResponse, Resp resp) throws Exception {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setHeader("Content-Type", "application/json");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        String resultStr = JSON.toJSONString(resp, serializerFeatures);
        outputStream.write(resultStr.getBytes("UTF-8"));
        outputStream.flush();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        synchronized (errorCodeMapping) {
            errorCodeMapping.clear();
            for (BusinessMsg businessMsg : BusinessMsg.values()) {
                errorCodeMapping.put(businessMsg.getBusinessRespCode(), businessMsg);
            }
        }
    }
}
