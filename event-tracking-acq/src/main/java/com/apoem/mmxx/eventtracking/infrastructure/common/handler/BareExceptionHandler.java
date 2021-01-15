package com.apoem.mmxx.eventtracking.infrastructure.common.handler;

import com.apoem.mmxx.eventtracking.BaseConstants;
import com.apoem.mmxx.eventtracking.exception.BaseException;
import com.apoem.mmxx.eventtracking.exception.BusinessException;
import com.apoem.mmxx.eventtracking.exception.Suppose;
import com.apoem.mmxx.eventtracking.infrastructure.common.CommonResponse;
import com.apoem.mmxx.eventtracking.infrastructure.common.MsRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
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

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    private MsRestResponse<Object, Object> handleBusinessException(BusinessException e) {
        String message = e.getMessage();
        if (Suppose.PARAMETER_VALIDATION.equals(e.getSupposeEnum())) {
            List<String> list = wrapperParameterExceptionResult(e);
            message = StringUtils.joinWith(", ", list.toArray());
            log.warn(wrapperExceptionMessage(Thread.currentThread().getStackTrace()[1].getMethodName(), list));
        } else {
            log.warn(message);
        }
        return CommonResponse.failure().setMsg(message);
    }

    public List<String> wrapperParameterExceptionResult(BusinessException e) {
        List<String> messages = new ArrayList<>();
        messages.add("Wrong parameter has [1]");
        messages.add("Parameter [" + e.getMessage() + "] must be useful");
        return messages;
    }

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public MsRestResponse<Object, Object> handleBaseException(BaseException e) {
        log.error(e.getMessage(), e);
        return CommonResponse.failure().setMsg(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public MsRestResponse<Object, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> list = wrapperBindingResult(e.getBindingResult());
        String join = StringUtils.joinWith(", ", list.toArray());
        log.warn(wrapperExceptionMessage(Thread.currentThread().getStackTrace()[1].getMethodName(), list));
        return CommonResponse.failure().setMsg(join);
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public MsRestResponse<Object, Object> handleBindException(BindException e) {
        List<String> list = wrapperBindingResult(e.getBindingResult());
        String join = StringUtils.joinWith(", ", list.toArray());
        log.warn(wrapperExceptionMessage(Thread.currentThread().getStackTrace()[1].getMethodName(), list));
        return CommonResponse.failure().setMsg(join);
    }

    private List<String> wrapperBindingResult(BindingResult bindingResult) {
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

    private String wrapperExceptionMessage(String title, List<String> messages) {
        String result = BaseConstants.BLANK;
        if(CollectionUtils.isNotEmpty(messages)) {
            StringBuilder stringBuilder = new StringBuilder().append("\n");
            stringBuilder.append("╔════════════════════════ ").append(title).append(" ════════════════════════╗").append("\n");
            for (String message : messages) {
                stringBuilder.append("║ ").append(message).append("\n");
            }
            stringBuilder.append("╚═════════════════════════").append(StringUtils.repeat("═", title.length())).append("═════════════════════════╝");
            result = stringBuilder.toString();
        }
        return result;
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
        log.error(e.getMessage(), e);
        return CommonResponse.failure().setMsg(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public MsRestResponse<Object, Object> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return CommonResponse.failure().setMsg(e.getMessage());
    }
}
