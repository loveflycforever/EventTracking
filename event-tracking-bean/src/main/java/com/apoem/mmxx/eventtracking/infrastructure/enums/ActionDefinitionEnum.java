package com.apoem.mmxx.eventtracking.infrastructure.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ActionDefinitionDispatch </p>
 * <p>Description: 操作定义分发 </p>
 * <p>Date: 2020/7/29 11:14 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Getter
public enum ActionDefinitionEnum {

    // ......
    EMPTY("EMPTY", null, null, ""),

    CUS_USE("CUS_USE", null, null, "客户使用"),

    VIS_AGT("VIS_AGT", CUS_USE, ActionTypeEnum.VIS, "访问经纪人"),

    VIS_CRD("VIS_CRD", VIS_AGT, ActionTypeEnum.VIS, "访问名片"),

    VIS_HUS("VIS_HUS", VIS_AGT, ActionTypeEnum.VIS, "访问房源"),
    COL_HUS("COL_HUS", null, ActionTypeEnum.COL, "收藏房源"),
    RPS_HUS("RPS_HUS", null, ActionTypeEnum.RPS, "转发房源"),
    IMC_HUS("IMC_HUS", null, null, "即时通讯"),
    PNC_HUS("PNC_HUS", null, null, "电话联系"),

    VIS_IFO("VIS_IFO", VIS_AGT, ActionTypeEnum.VIS, "查看资讯"),
    COL_IFO("COL_IFO", null, ActionTypeEnum.COL, "收藏资讯"),
    RPS_IFO("RPS_IFO", null, ActionTypeEnum.RPS, "转发资讯"),

    SRD_HUS("SRD_HUS", null, null, "通过分享的房源模板"),

    // 后缀H5的分类统计uv时使用openId
    CUS_USE_H5("CUS_USE_H5", null, null, "客户使用H5"),
    VIS_AGT_H5("VIS_AGT_H5", CUS_USE_H5, ActionTypeEnum.VIS, "访问经纪人H5"),
    VIS_CRD_H5("VIS_CRD_H5", VIS_AGT_H5, ActionTypeEnum.VIS, "访问名片H5"),
    VIS_HUS_H5("VIS_HUS_H5", VIS_AGT_H5, ActionTypeEnum.VIS, "访问房源H5"),
    VIS_IFO_H5("VIS_IFO_H5", VIS_AGT_H5, ActionTypeEnum.VIS, "查看资讯H5"),
    VIS_POS_H5("VIS_POS_H5", VIS_AGT_H5, ActionTypeEnum.VIS, "访问海报H5"),
    SRD_HUS_H5("SRD_HUS_H5", VIS_AGT_H5, ActionTypeEnum.VIS, "通过分享的房源模板H5"),
    VIS_GAME("VIS_GAME", VIS_AGT_H5, ActionTypeEnum.VIS, "访问活动"),
    RPS_GAME_APP("RPS_GAME_APP", VIS_AGT_H5, ActionTypeEnum.VIS, "APP分享活动"),
    RPS_GAME_H5("RPS_GAME_H5", VIS_AGT_H5, ActionTypeEnum.VIS, "非APP分享活动"),

    AGT_USE("AGT_USE", null, null, "经纪人使用"),

    VIS_ADV("VIS_ADV", null, null, "查看广告"),
    VIS_COR("VIS_COR", null, null, "查看课程");

    private final String name;
    private final ActionDefinitionEnum klass;
    private final ActionTypeEnum actionType;
    private final String desc;

    ActionDefinitionEnum(String name, ActionDefinitionEnum klass, ActionTypeEnum actionType, String desc) {
        this.desc = desc;
        this.name = name;
        this.actionType = actionType;
        this.klass = klass;
    }

    public static String getDesc(String name) {
        return Arrays.stream(ActionDefinitionEnum.values())
                .filter(o -> o.getName().equals(name))
                .findFirst()
                .orElse(EMPTY).getDesc();
    }

    public static boolean isConfig(String name) {
        return Arrays.stream(ActionDefinitionEnum.values())
                .anyMatch(o -> o.getName().equals(name));
    }

    public static ActionDefinitionEnum find(String name) {
        return Arrays.stream(ActionDefinitionEnum.values())
                .filter(o -> o.getName().equalsIgnoreCase(name)).findFirst().orElse(EMPTY);
    }

    public boolean maybe(String name) {
        return StringUtils.equalsIgnoreCase(this.getName(), name);
    }

    public static String string() {
        return Arrays.stream(ActionDefinitionEnum.values()).map(o -> o.getDesc() + "=" + o.getName()).collect(Collectors.joining("|"));
    }
}
