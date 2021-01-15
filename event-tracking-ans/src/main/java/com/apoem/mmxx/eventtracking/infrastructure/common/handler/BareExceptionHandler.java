package com.apoem.mmxx.eventtracking.infrastructure.common.handler;

import com.apoem.mmxx.eventtracking.BaseConstants;
import com.apoem.mmxx.eventtracking.exception.LogSupport;
import com.apoem.mmxx.eventtracking.exception.BaseException;
import com.apoem.mmxx.eventtracking.exception.Suppose;
import com.apoem.mmxx.eventtracking.infrastructure.common.CommonResponse;
import com.apoem.mmxx.eventtracking.infrastructure.common.MsRestResponse;
import com.apoem.mmxx.eventtracking.infrastructure.common.holder.CurrentRequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BareExceptionHandler </p>
 * <p>Description: 异常处理器，处理暴露的、已知的异常 </p>
 * <p>Date: 2020/7/15 17:14 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@ControllerAdvice
@Slf4j
public class BareExceptionHandler {

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public MsRestResponse<Object, Object> handleBaseException(BaseException e) {
        String message = e.getMessage();
        if (Suppose.PARAMETER_VALIDATION.equals(e.getSupposeEnum())) {
            List<String> list = LogSupport.wrapperParameterExceptionResult(e);
            message = StringUtils.joinWith(BaseConstants.COMMA_SPACE, list.toArray());
            log.warn(LogSupport.message(CurrentRequestHolder.getSerialNumber(), Suppose.PARAMETER_VALIDATION, message));
        } else {
            log.warn(LogSupport.message(CurrentRequestHolder.getSerialNumber(), Suppose.BUSINESS_EXCEPTION, message));
        }
        return CommonResponse.failure().setMsg(message);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public MsRestResponse<Object, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> list = wrapperBindingResult(e.getBindingResult());
        String join = StringUtils.joinWith(BaseConstants.COMMA_SPACE, list.toArray());
        log.warn(LogSupport.message(CurrentRequestHolder.getSerialNumber(), Suppose.PARAMETER_VALIDATION, join));
        return CommonResponse.failure().setMsg(join);
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public MsRestResponse<Object, Object> handleBindException(BindException e) {
        List<String> list = wrapperBindingResult(e.getBindingResult());
        String join = StringUtils.joinWith(BaseConstants.COMMA_SPACE, list.toArray());
        log.warn(LogSupport.message(CurrentRequestHolder.getSerialNumber(), Suppose.PARAMETER_VALIDATION, join));
        return CommonResponse.failure().setMsg(join);
    }

    private List<String> wrapperBindingResult(Errors bindingResult) {
        List<String> messages = new ArrayList<>();
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            messages.add("Wrong parameter has [" + allErrors.size() + "]");
            StringBuilder stringBuilder;
            for (ObjectError objectError : allErrors) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Parameter [").append(objectError.getDefaultMessage()).append("] must be [").append(objectError.getCode()).append("]");
                if(objectError instanceof FieldError) {
                    FieldError fieldError = (FieldError) objectError;
                    stringBuilder.append(", so [").append(fieldError.getField()).append("] should not be [").append(fieldError.getRejectedValue()).append("]");
                }
                messages.add(stringBuilder.toString());
            }
        }
        return messages;
    }

    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            HttpMediaTypeNotAcceptableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })
    @ResponseBody
    public MsRestResponse<Object, Object> handleServletException(Exception e) {
        log.warn(LogSupport.message(CurrentRequestHolder.getSerialNumber(), Suppose.SERVLET_EXCEPTION, e.getMessage()), e);
        return CommonResponse.failure().setMsg(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public MsRestResponse<Object, Object> handleException(Exception e) {
        log.error(LogSupport.message(CurrentRequestHolder.getSerialNumber(), Suppose.SERVICE_EXCEPTION, e.getMessage()), e);
        return CommonResponse.failure().setMsg(e.getMessage());
    }
}
