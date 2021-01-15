package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: PursuitCommunityVo </p>
 * <p>Description: 关注小区VO </p>
 * <p>Date: 2020/8/10 15:04 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class CustomerCommunityVo {

    /**
     * 总数量
     */
    @ApiModelProperty(value = "总数量", example = "26")
    private Long houseTotal;

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据")
    private List<PursuitCommunityRowVo> rows;

    @Data
    public static class PursuitCommunityRowVo {

        /**
         * 名称
         */
        @ApiModelProperty(value = "名称", example = "汤臣一品")
        private String communityId;

        /**
         * 名称
         */
        @ApiModelProperty(value = "名称", example = "汤臣一品")
        private String communityName;

        /**
         * 房源数量
         */
        @ApiModelProperty(value = "房源数量", example = "12")
        private Long houseAmount;
    }
}
