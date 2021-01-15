package com.apoem.mmxx.eventtracking.exception;

import lombok.*;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BaseException </p>
 * <p>Description: 异常定义 </p>
 * <p>Date: 2020/8/11 16:50 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class BaseException extends RuntimeException {

    /**
     * 推测类枚举
     */
    private ISuppose supposeEnum;

    /**
     * 参数
     */
    private Object[] args;

    /**
     * 消息
     */
    private String message;

    /**
     * 原因
     */
    private Throwable cause;

}
