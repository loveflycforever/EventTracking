package com.apoem.mmxx.eventtracking.infrastructure.common;

import com.apoem.mmxx.eventtracking.BaseConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: MsRestResponse<DTO, VO> </p>
 * <p>Description:
 * Refactor from
 *      <groupId>com.unknown</groupId>
 *      <artifactId>unknown-framework</artifactId>
 *      <version>1.0.0-SNAPSHOT</version>
 * com.unknown.rest.parameter.UnknownRestResponse
 * </p>
 * <p>Date: 2020/8/5 15:28 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@Accessors(chain = true)
public class MsRestResponse<DTO, VO> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态码", required = true, example = "10000")
    private String result;
    @ApiModelProperty(value = "提示", required = true, example = "操作成功！")
    private String msg = BaseConstants.BLANK;
    @ApiModelProperty(value = "结果集")
    private VO data;
    @ApiModelProperty(value = "多结果集Size", example = "1")
    private long total;
    @ApiModelProperty(value = "参数集")
    private DTO param;

    public MsRestResponse() {
    }

    public MsRestResponse<DTO, VO> success() {
        this.result = ReturnResult.SUCCESS.getResultCode();
        this.msg = ReturnResult.SUCCESS.getResultMessage();
        return this;
    }
}
