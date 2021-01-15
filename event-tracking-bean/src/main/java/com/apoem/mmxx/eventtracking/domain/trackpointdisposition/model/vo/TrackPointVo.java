package com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.vo;

import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.EventTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TrackPointVo </p>
 * <p>Description: 埋点视图 </p>
 * <p>Date: 2020/7/15 13:22 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class TrackPointVo implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", example = "1")
    private Long id;

    /**
     * 页面名称
     */
    @ApiModelProperty(value = "页面名称", example = "registerPage")
    private String pageName;

    /**
     * 页面中文名称
     */
    @ApiModelProperty(value = "页面中文名称", example = "注册页")
    private String pageNameCn;

    /**
     * 事件类型（打开页面=VIEW|页面点击=CLICK|页面长按=HOLD）
     */
    @ApiModelProperty(value = "事件类型（打开页面=VIEW|页面点击=CLICK）", example = "VIEW")
    private String eventType;
    @ApiModelProperty(value = "事件类型名称", example = "打开页面")
    private String eventTypeVal;

    /**
     * 方法名称（iOS方法名、Android方法名、小程序方法名）
     */
    @ApiModelProperty(value = "方法名称（iOS方法名、Android方法名、小程序方法名）", example = "com.xxx.methodName")
    private String methodName;

    /**
     * 方法中文名称
     */
    @ApiModelProperty(value = "方法中文名称", example = "小程序的一个方法名")
    private String methodNameCn;

    /**
     * 模块ID
     */
    @ApiModelProperty(value = "模块ID", example = "1")
    private Long moduleId;
    @ApiModelProperty(value = "模块名称", example = "RegisterModule")
    private String moduleName;
    @ApiModelProperty(value = "模块中文名称", example = "注册模块")
    private String moduleNameCn;

    /**
     * 操作类型，轨迹用（访问=VIS|收藏=COL|分享=RPS|空=EMPTY）
     */
    @ApiModelProperty(value = "操作类型（访问=VIS|收藏=COL|分享=RPS|空=EMPTY）", example = "VIS")
    private String actionType;
    @ApiModelProperty(value = "操作类型名称", example = "访问")
    private String actionTypeVal;

    /**
     * 操作定义（访问经纪人=VIS_AGT|访问房源=VIS_HUS|查看资讯VIS_IFO|
     * 使用IM=USE_IM|使用手机=USE_PHN|
     * 收藏经纪人COL_AGT|收藏房源COL_HUS|收藏资讯COL_IFO|
     * 转发资讯RPS_IFO|
     * 空=EMPTY）
     */
    @ApiModelProperty(value = "操作定义（访问经纪人=VIS_AGT|访问房源=VIS_HUS|查看资讯VIS_IFO" +
            "|使用IM=USE_IM|使用手机=USE_PHN" +
            "|收藏经纪人COL_AGT|收藏房源COL_HUS|收藏资讯COL_IFO" +
            "|转发资讯RPS_IFO|空=EMPTY）", example = "VIS_AGT")
    private String[] actionDefinition;
    @ApiModelProperty(value = "操作定义名称", example = "访问经纪人")
    private String[] actionDefinitionVal;

    /**
     * 渠道（iOS|Android|WMP）
     */
    @ApiModelProperty(value = "渠道（iOS|Android|WMP）", example = "WMP")
    private String avenue;
    @ApiModelProperty(value = "渠道名称", example = "微信小程序")
    private String avenueVal;

    /**
     * 终端（B端=Business|C端=Consumer）
     */
    @ApiModelProperty(value = "终端（B端=Business|C端=Consumer）", example = "Business")
    private String endpoint;
    @ApiModelProperty(value = "终端名称", example = "B端")
    private String endpointVal;

    private Integer mark;

    public static TrackPointVo empty() {
        TrackPointVo trackPointVo = new TrackPointVo();
        trackPointVo.setModuleId(-1L);
        trackPointVo.setActionType(StringUtils.EMPTY);
        trackPointVo.setActionDefinition(new String[]{});
        return trackPointVo;
    }

    public static boolean norCheckActionDef(TrackPointVo trackPointVo) {
        boolean result = false;
        String[] actionDefinition = trackPointVo.getActionDefinition();
        // 无需处理的埋点，没有定义维度
        if (actionDefinition != null && actionDefinition.length > 0) {
            // 无需处理的埋点，定义的维度为EMPTY
            for (String def : actionDefinition) {
                result = result || !ActionDefinitionEnum.EMPTY.maybe(def);
            }
        }
        return !result;
    }

    /**
     * 该埋点 轨迹上为访问、统计维度为房源、事件类型为访问 可定义为访问详情页
     * @param trackPointVo 点
     * @return 布尔值
     */
    public static boolean isVisitHouseDetailPage(TrackPointVo trackPointVo) {
        return Arrays.stream(trackPointVo.getActionDefinition()).anyMatch(ActionDefinitionEnum.VIS_HUS::maybe)
                && ActionTypeEnum.VIS.maybe(trackPointVo.getActionType())
                && EventTypeEnum.VIEW.maybe(trackPointVo.getEventType());
    }
}
