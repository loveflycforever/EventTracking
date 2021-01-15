package com.apoem.mmxx.eventtracking.domain.analysis.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: InvolvedHouseVo </p>
 * <p>Description: 客源关联的房源轨迹VO </p>
 * <p>Date: 2020/8/10 17:38 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class CustomerHouseTrailVo implements Serializable {

    /**
     * 最后浏览
     */
    @ApiModelProperty(value = "行数据")
    private HouseTrailRowVo lastOperation;

    /**
     * 行数据
     */
    @ApiModelProperty(value = "行数据")
    private List<HouseTrailRowVo> rows;

    @Data
    public static class HouseTrailRowVo implements Serializable {

        /**
         * 客源
         */
        private String customerId;

        /**
         * 时间
         */
        @ApiModelProperty(value = "时间（yyyy-MM-dd HH:mm:ss.SSS）", example = "2020-09-14 12:13:14.000")
        private String date;

        /**
         * 浏览
         * 第xx次浏览
         */
        @ApiModelProperty(value = "浏览次数", example = "3")
        private String number;

        /**
         * 浏览、分享、收藏
         */
        @ApiModelProperty(value = "范围（浏览-VIS、收藏-COL、分享-RPS）", example = "VIS")
        private String scope;

        /**
         * 定义
         */
        @ApiModelProperty(value = "定义", example = "VIS_HUS")
        private String def;

//        /**
//         * 来源（小程序MP|网页H5）
//         */
//        @ApiModelProperty(value = "来源（小程序MP|网页H5）", example = "MP")
//        private String location;

        /**
         * 浏览时长
         */
        @ApiModelProperty(value = "浏览时长", example = "53")
        private String duration;

        /**
         * 提交时的原文
         */
        @ApiModelProperty(value = "提交时的原文")
        private String content;

        /**
         * nextKey
         */
        @ApiModelProperty(value = "nextKey", example = "2012")
        private String nextKey;
    }
}
