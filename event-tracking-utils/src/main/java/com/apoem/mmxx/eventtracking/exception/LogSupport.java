package com.apoem.mmxx.eventtracking.exception;

import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: LogUtils </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/24 11:09 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public final class LogSupport {

    public static <T> String info(String serialNumber, T object) {
        return MessageFormatter
                .arrayFormat("[{}]══════title: [{}]══════content: [{}]", new String[] {serialNumber, Suppose.PRINT_INFO.getMessage(), Objects.toString(object)})
                .getMessage();
    }

    public static <T> String message(String serialNumber, Suppose suppose, T object) {
        return MessageFormatter
                .arrayFormat("[{}]══════title: [{}]══════content: [{}]", new String[] {serialNumber, suppose.getMessage(), Objects.toString(object)})
                .getMessage();
    }

    public static List<String> wrapperParameterExceptionResult(BaseException e) {
        List<String> messages = new ArrayList<>();
        messages.add("Wrong parameter has [1]");
        messages.add("Parameter [" + e.getMessage() + "] must be useful");
        return messages;
    }
}