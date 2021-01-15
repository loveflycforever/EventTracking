package com.apoem.mmxx.eventtracking.infrastructure.common.handler;

import com.apoem.mmxx.eventtracking.exception.LogSupport;
import com.apoem.mmxx.eventtracking.infrastructure.common.holder.CurrentRequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: Xccc </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/24 10:12 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Slf4j
@ControllerAdvice
public class RequestBodyHandler extends RequestBodyAdviceAdapter {

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info(LogSupport.info(CurrentRequestHolder.getSerialNumber(), body));
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
