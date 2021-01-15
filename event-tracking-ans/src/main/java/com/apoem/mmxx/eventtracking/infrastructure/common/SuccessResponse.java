package com.apoem.mmxx.eventtracking.infrastructure.common;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: SuccessResponse </p>
 * <p>Description: 通用模板返回值 </p>
 * <p>Date: 2020/7/27 15:17 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public final class SuccessResponse {

    public static <DTO, VO> MsRestResponse<DTO, VO> data(DTO dto, VO vo) {
        MsRestResponse<DTO, VO> result = new MsRestResponse<>();
        result.setResult(ReturnResult.SUCCESS.getResultCode());
        result.setMsg(ReturnResult.SUCCESS.getResultMessage());
        return result.setParam(dto).setData(vo);
    }
}
