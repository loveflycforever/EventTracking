package com.apoem.mmxx.eventtracking.application.event;

import com.apoem.mmxx.eventtracking.infrastructure.enums.AvenueEnum;
import com.google.gson.Gson;
import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.*;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.vo.TrackPointVo;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.BusinessAcquisitionOdsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.ConsumerAcquisitionOdsEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: EtlUtils </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/28 8:51 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public final class EtlUtils {

    public static String[] checkExtraInfo(TrackPointVo trackPointVo, BusinessAcquisitionOdsEntity entity, ParamContentInfo info) {
        List<String> def = new ArrayList<>();
        String[] acs = trackPointVo.getActionDefinition();
        for (String ac : acs) {
            boolean b;
            ActionDefinitionEnum definition = ActionDefinitionEnum.find(ac);
            switch (definition) {
                // B 端
                case AGT_USE:
                    b = isB(info.getLoginAccount());
                    break;
                case VIS_ADV:
                    b = isB(info.getAdvId());
                    break;
                case VIS_COR:
                    b = isB(info.getCourseId());
                    break;
                case EMPTY:
                    b = false;
                    break;
                default:
                    b = true;
                    break;
            }

            if(b) {
                def.add(definition.getName());
            }
        }
        return def.toArray(new String[]{});
    }

    private static boolean isB(String value) {
        return StringUtils.isNotBlank(value);
    }

    public static String[] checkExtraInfo(TrackPointVo trackPointVo, WrapperContentInfo info) {
        List<String> def = new ArrayList<>();

        String[] acs = trackPointVo.getActionDefinition();
        for (String ac : acs) {
            ActionDefinitionEnum definition = ActionDefinitionEnum.find(ac);

            boolean b;
            switch (definition) {
                // C端
                case VIS_AGT:
                case VIS_CRD:
                case VIS_AGT_H5:
                case VIS_CRD_H5:
                    b = WrapperContentInfo.isAgent(info);
                    break;
                case VIS_HUS:
                case COL_HUS:
                case RPS_HUS:
                case IMC_HUS:
                case PNC_HUS:
                case VIS_HUS_H5:
                    b = WrapperContentInfo.isHouse(info);
                    break;
                case VIS_IFO:
                case COL_IFO:
                case RPS_IFO:
                case VIS_IFO_H5:
                    b = WrapperContentInfo.isInformation(info);
                    break;
                case SRD_HUS:
                    b = WrapperContentInfo.isHouseTemplate(info);
                    break;
                case VIS_POS_H5:
                    b = WrapperContentInfo.isPoster(info);
                    break;
                case VIS_GAME:
                case RPS_GAME_H5:
                    b = WrapperContentInfo.isGame(info);
                    break;
                case EMPTY:
                    b = false;
                    break;
                default:
                    b = true;
                    break;
            }
            if (b) {
                def.add(definition.getName());
            }
        }
        return def.toArray(new String[]{});
    }

    public static Optional<WrapperContentInfo> getExtraInfo(ConsumerAcquisitionOdsEntity entity) {
        String content = entity.getContent();
        ContentInfo contentInfo;
        try {
            contentInfo = new Gson().fromJson(content, ContentInfo.class);
        } catch (Exception ignore) {
            return Optional.empty();
        }

        if (contentInfo == null) {
            return Optional.empty();
        }

        WrapperContentInfo result = new WrapperContentInfo();
        result.setContentInfo(contentInfo);
        result.setCity(StringUtils.trimToEmpty(entity.getCity()));
        result.setOpTime(DateUtils.trimToZero(entity.getOpTime()));
        result.setOpenId(StringUtils.trimToEmpty(entity.getOpenId()));

        return Optional.of(result);
    }

    public static ParamContentInfo getExtraInfo(BusinessAcquisitionOdsEntity entity) {
        ParamContentInfo contentInfo = new ParamContentInfo();
        contentInfo.setAdvId(StringUtils.trimToEmpty(entity.getParamAdId()));
        contentInfo.setCourseId(StringUtils.trimToEmpty(entity.getParamContextId()));
        contentInfo.setLoginAccount(StringUtils.trimToEmpty(entity.getLoginAccount()));
        contentInfo.setPosterCode(StringUtils.trimToEmpty(entity.getParamKey()));

        String paramCommonValue = entity.getParamCommonValue();
        String paramBusinessData = entity.getParamBusinessData();
        String paramContent = entity.getParamContent();

        CommonValueInfo commonValueInfo = null;
        BusinessDataInfo businessDataInfo = null;

        if(AvenueEnum.ANDROID.getTransValue().equalsIgnoreCase(entity.getPlatform())) {
            try {
                commonValueInfo = new Gson().fromJson(paramCommonValue, CommonValueInfo.class);
            } catch (Exception ignore) {}
            try {
                businessDataInfo = new Gson().fromJson(paramBusinessData, BusinessDataInfo.class);
            } catch (Exception ignore) {}
        }

        if(AvenueEnum.IOS.getTransValue().equalsIgnoreCase(entity.getPlatform())) {
            IosParamContentInfo iosParamContentInfo = null;
            try {
                iosParamContentInfo = new Gson().fromJson(paramContent, IosParamContentInfo.class);
            } catch (Exception ignore) {}
            iosParamContentInfo = Optional.ofNullable(iosParamContentInfo).orElse(new IosParamContentInfo().init());
            businessDataInfo = iosParamContentInfo.getBusinessDataInfo();
            commonValueInfo = iosParamContentInfo.getCommonValueInfo();
        }

        businessDataInfo = Optional.ofNullable(businessDataInfo).orElse(new BusinessDataInfo().init());
        commonValueInfo = Optional.ofNullable(commonValueInfo).orElse(new CommonValueInfo().init());

        contentInfo.setType(StringUtils.trimToEmpty(businessDataInfo.getType()));
        contentInfo.setActId(StringUtils.trimToEmpty(businessDataInfo.getActId()));
        contentInfo.setTest(StringUtils.trimToEmpty(commonValueInfo.getTest()));
        contentInfo.setStoreId(StringUtils.trimToEmpty(commonValueInfo.getStoreId()));
        contentInfo.setCompanyId(StringUtils.trimToEmpty(commonValueInfo.getCompanyId()));
        return contentInfo;
    }
}
