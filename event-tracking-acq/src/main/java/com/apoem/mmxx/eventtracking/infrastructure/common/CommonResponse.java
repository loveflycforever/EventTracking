package com.apoem.mmxx.eventtracking.infrastructure.common;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CommRestResponse </p>
 * <p>Description: 通用模板返回值 </p>
 * <p>Date: 2020/7/27 15:17 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public final class CommonResponse {

    public static MsRestResponse<Object, Object> success() {
        MsRestResponse<Object, Object> result = new MsRestResponse<>();
        result.setResult(ReturnResult.SUCCESS.getResultCode());
        result.setMsg(ReturnResult.SUCCESS.getResultMessage());
        return result;
    }

    public static MsRestResponse<Object, Object> failure() {
        MsRestResponse<Object, Object> result = new MsRestResponse<>();
        result.setResult(ReturnResult.FAIL.getResultCode());
        result.setMsg(ReturnResult.FAIL.getResultMessage());
        return result;
    }
}
