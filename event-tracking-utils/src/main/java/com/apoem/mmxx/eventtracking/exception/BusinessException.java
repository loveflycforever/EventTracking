package com.apoem.mmxx.eventtracking.exception;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BusinessException </p>
 * <p>Description: 业务异常 </p>
 * <p>Date: 2020/7/15 17:18 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class BusinessException extends BaseException {

    public BusinessException(ISuppose supposeEnum, String message) {
        super(supposeEnum, null, message, null);
    }

    public BusinessException(ISuppose supposeEnum, Object[] args, String message) {
        super(supposeEnum, args, message, null);
    }

    public BusinessException(ISuppose supposeEnum, Object[] args, String message, Throwable cause) {
        super(supposeEnum, args, message, cause);
    }

    public BusinessException(ISuppose supposeEnum, Throwable cause) {
        super(supposeEnum, null, cause.getMessage(), cause);
    }
}