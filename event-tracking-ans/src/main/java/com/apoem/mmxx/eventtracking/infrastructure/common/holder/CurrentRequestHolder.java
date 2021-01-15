package com.apoem.mmxx.eventtracking.infrastructure.common.holder;

import com.apoem.mmxx.eventtracking.serialnumber.MsSerialNumberHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CurrentRequestHolder </p>
 * <p>Description: SpringMVC 请求持有处理 </p>
 * <p>Date: 2020/7/21 8:54 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public final class CurrentRequestHolder {

    private CurrentRequestHolder() {}

    public static String getParameter(String name) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assertRequestContext(requestAttributes);
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        return request.getParameter(name);
    }

    public static String getHeader(String name) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assertRequestContext(requestAttributes);
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        return request.getHeader(name);
    }

    public static String getSerialNumber() {
        return MsSerialNumberHolder.getMsSerialNumber();
    }

    private static void assertRequestContext(ServletRequestAttributes requestAttributes) {
        if (Objects.isNull(requestAttributes)) {
            throw new RuntimeException("RequestContextHolder属性为null，请检查当前线程是否持有RequestContextHolder!");
        }
    }
}
