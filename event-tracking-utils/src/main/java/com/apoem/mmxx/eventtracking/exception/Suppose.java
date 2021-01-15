package com.apoem.mmxx.eventtracking.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: Suppose </p>
 * <p>Description: 推测类枚举 </p>
 * <p>Date: 2020/8/11 11:24 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Getter
@AllArgsConstructor
public enum Suppose implements AbstractSuppose {

    /**
     * aop validation
     */
    AOP_VALIDATION(0, "Aop validation failed"),
    /**
     * parameter validation
     */
    PARAMETER_VALIDATION(0, "Parameter validation failed"),
    /**
     * parameter validation
     */
    BUSINESS_EXCEPTION(0, "Business exception"),
    /**
     * parameter validation
     */
    SERVLET_EXCEPTION(0, "Servlet exception"),
    /**
     * parameter validation
     */
    SERVICE_EXCEPTION(0, "Service exception"),
    /**
     * parameter validation
     */
    PRINT_INFO(0, "Print info"),
    /**
     * parameter validation
     */
    REQUEST_CITY_ERROR(0, "Request city error"),
    /**
     * parameter validation
     */
    REQUEST_COMMUNITY_INFO_ERROR(0, "Request community info error"),

    TASK_IS_RUNNING(0, "Task is running");

    /**
     * 返回码
     */
    private final int code;

    /**
     * 返回消息
     */
    private final String message;
}