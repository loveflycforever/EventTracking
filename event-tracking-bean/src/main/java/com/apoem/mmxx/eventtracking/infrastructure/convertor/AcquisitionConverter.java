package com.apoem.mmxx.eventtracking.infrastructure.convertor;

import com.apoem.mmxx.eventtracking.BeanCopierUtils;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.Acquisition;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.BusinessInfo;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.ConsumerInfo;
import com.apoem.mmxx.eventtracking.infrastructure.enums.EndpointEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.BusinessAcquisitionOdsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.ConsumerAcquisitionOdsEntity;
import com.apoem.mmxx.eventtracking.interfaces.assembler.BusinessAcquisitionCmd;
import com.apoem.mmxx.eventtracking.interfaces.assembler.ConsumerAcquisitionCmd;
import com.apoem.mmxx.eventtracking.interfaces.dto.acq.Params;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AcquisitionConverter </p>
 * <p>Description: 业务请求实体转换器 </p>
 * <p>Date: 2020/7/16 9:37 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public final class AcquisitionConverter {

    private AcquisitionConverter() {
    }

    public static BusinessAcquisitionOdsEntity serializeBusiness(Acquisition<BusinessInfo> acquisition) {
        BusinessInfo customInfo = acquisition.getCustomInfo();
        return BusinessAcquisitionOdsEntity.builder()
                .app(customInfo.getApp())
                .appVersion(customInfo.getAppVersion())
                .platform(customInfo.getPlatform())
                .modelType(customInfo.getModelType())
                .osVersion(customInfo.getOsVersion())
                .deviceId(customInfo.getDeviceId())
                .channel(customInfo.getChannel())
                .city(customInfo.getCity())
                .provider(customInfo.getProvider())
                .roleType(customInfo.getRoleType())
                .loginAccount(customInfo.getLoginAccount())
                .networkType(customInfo.getNetworkType())
                .screen(customInfo.getScreen())
                .coordinate(customInfo.getCoordinate())
                .paramId(customInfo.getParamId())
                .paramKey(customInfo.getParamKey())
                .paramOpTime(customInfo.getParamOpTime())
                .paramSessionId(customInfo.getParamSessionId())
                .paramPageId(customInfo.getParamPageId())
                .paramPageName(customInfo.getParamPageName())
                .paramAdId(customInfo.getParamAdId())
                .paramAdDesc(customInfo.getParamAdDesc())
                .paramRequestType(customInfo.getParamRequestType())
                .paramRequestBody(customInfo.getParamRequestBody())
                .paramResponse(customInfo.getParamResponse())
                .paramTelNo(customInfo.getParamTelNo())
                .paramObjectId(customInfo.getParamObjectId())
                .paramEventObj(customInfo.getParamEventObj())
                .paramFriendId(customInfo.getParamFriendId())
                .paramStartTime(customInfo.getParamStartTime())
                .paramEndTime(customInfo.getParamEndTime())
                .paramPagination(customInfo.getParamPagination())
                .paramTargetPageId(customInfo.getParamTargetPageId())
                .paramCodeChannel(customInfo.getParamCodeChannel())
                .paramAccountName(customInfo.getParamAccountName())
                .paramSearchKey(customInfo.getParamSearchKey())
                .paramSearchType(customInfo.getParamSearchType())
                .paramContextId(customInfo.getParamContextId())
                .paramContent(customInfo.getParamContent())
                .paramBusinessData(customInfo.getParamBusinessData())
                .paramCommonValue(customInfo.getParamCommonValue())
                .paramPosterCode(customInfo.getParamPosterCode())
                .build();
    }

    public static ConsumerAcquisitionOdsEntity serializeConsumer(Acquisition<ConsumerInfo> acquisition) {
        ConsumerInfo customInfo = acquisition.getCustomInfo();

        return ConsumerAcquisitionOdsEntity.builder()
                .city(customInfo.getCity())
                .opTime(customInfo.getOpTime())
                .appId(customInfo.getAppId())
                .appVersion(customInfo.getAppVersion())
                .openId(customInfo.getOpenId())
                .uniqueId(customInfo.getUniqueId())
                .brand(customInfo.getBrand())
                .model(customInfo.getModel())
                .pixelRatio(customInfo.getPixelRatio())
                .screenWidth(customInfo.getScreenWidth())
                .screenHeight(customInfo.getScreenHeight())
                .windowWidth(customInfo.getWindowWidth())
                .windowHeight(customInfo.getWindowHeight())
                .statusBarHeight(customInfo.getStatusBarHeight())
                .wxVersion(customInfo.getWxVersion())
                .system(customInfo.getSystem())
                .platform(customInfo.getPlatform())
                .sdkVersion(customInfo.getSdkVersion())
                .avenue(customInfo.getAvenue())
                .pageName(customInfo.getPageName())
                .methodName(customInfo.getMethodName())
                .eventType(customInfo.getEventType())
                .startTime(customInfo.getStartTime())
                .endTime(customInfo.getEndTime())
                .content(customInfo.getContent())
                .build();
    }

    public static Acquisition<ConsumerInfo> deserialize(ConsumerAcquisitionCmd cmd) {

        ConsumerInfo customInfo = new ConsumerInfo();

        BeanCopierUtils.copy(cmd, customInfo);

        Acquisition<ConsumerInfo> acquisition = new Acquisition<>();
        acquisition.setEndpoint(EndpointEnum.CONSUMER);
        acquisition.setCustomInfo(customInfo);
        acquisition.setContentInfo(cmd.getContent());
        return acquisition;
    }

    public static List<Acquisition<BusinessInfo>> deserialize(BusinessAcquisitionCmd cmd) {
        List<Params> params = cmd.getParams();

        List<Acquisition<BusinessInfo>> info = new ArrayList<>();

        for (Params param : params) {
            BusinessInfo customInfo = new BusinessInfo();

            BeanCopierUtils.copy(cmd, customInfo);

            customInfo.setParamId(param.getId());
            customInfo.setParamKey(param.getKey());
            customInfo.setParamOpTime(param.getOpTime());
            customInfo.setParamSessionId(param.getSessionId());
            customInfo.setParamPageId(param.getPageId());
            customInfo.setParamPageName(param.getPageName());
            customInfo.setParamAdId(param.getAdId());
            customInfo.setParamAdDesc(param.getAdDesc());
            customInfo.setParamRequestType(param.getRequestType());
            customInfo.setParamRequestBody(param.getRequestBody());
            customInfo.setParamResponse(param.getResponse());
            customInfo.setParamTelNo(param.getTelNo());
            customInfo.setParamObjectId(param.getObjectId());
            customInfo.setParamEventObj(param.getEventObj());
            customInfo.setParamFriendId(param.getFriendId());
            customInfo.setParamStartTime(param.getStartTime());
            customInfo.setParamEndTime(param.getEndTime());
            customInfo.setParamPagination(param.getPagination());
            customInfo.setParamTargetPageId(param.getTargetPageId());
            customInfo.setParamCodeChannel(param.getCodeChannel());
            customInfo.setParamAccountName(param.getAccountName());
            customInfo.setParamSearchKey(param.getSearchKey());
            customInfo.setParamSearchType(param.getSearchType());
            customInfo.setParamContextId(param.getContextId());
            customInfo.setParamPosterCode(param.getPosterCode());
            customInfo.setParamContent(param.getContent());
            customInfo.setParamBusinessData(param.getBusinessData());
            customInfo.setParamCommonValue(param.getCommonValue());

            Acquisition<BusinessInfo> acquisition = new Acquisition<>();
            acquisition.setEndpoint(EndpointEnum.BUSINESS);
            acquisition.setCustomInfo(customInfo);
            acquisition.setContentInfo(param.getContent());

            info.add(acquisition);
        }

        return info;
    }

}
