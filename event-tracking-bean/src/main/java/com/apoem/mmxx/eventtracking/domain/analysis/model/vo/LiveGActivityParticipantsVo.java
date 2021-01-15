package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * <p>Project: Event Tracking </p>
 * <p>Name: LiveGActivityParticipantsVo </p>
 * <p>Description: LiveG展示实体 </p>
 * <p>Date: 2020/11/27 13:27 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
public class LiveGActivityParticipantsVo implements Serializable {

    /**
     * 唯一编号
     */
    @ApiModelProperty(value = "唯一编号", example = "191")
    private String id;

    /**
     * 账号申请UV量
     */
    @ApiModelProperty(value = "参与人数", example = "12")
    private Long participantsAmount;
}
