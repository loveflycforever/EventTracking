package com.apoem.mmxx.eventtracking.infrastructure.po.query;

import com.apoem.mmxx.eventtracking.infrastructure.enums.AvenueEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.EndpointEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.EventTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.BusinessAcquisitionOdsEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.ConsumerAcquisitionOdsEntity;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BusinessAcqTrackPointQuery </p>
 * <p>Description: 实体：埋点实体 </p>
 * <p>Date: 2020/7/14 12:33 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class AcqTrackPointQuery {
    private String endpoint;
    private String pageName;
    private String methodName;
    private String eventType;
    private String avenue;

    public AcqTrackPointQuery(BusinessAcquisitionOdsEntity entity) {
        this.endpoint = EndpointEnum.BUSINESS.getName();
        this.pageName = StringUtils.trimToEmpty(entity.getParamPageId());
        this.methodName = StringUtils.trimToEmpty(entity.getParamObjectId());
        this.eventType = getEventType(entity);
        this.avenue = getAvenue(entity);
    }

    public AcqTrackPointQuery(ConsumerAcquisitionOdsEntity entity) {
        this.endpoint = EndpointEnum.CONSUMER.getName();
        this.pageName = StringUtils.trimToEmpty(entity.getPageName());
        this.methodName = StringUtils.trimToEmpty(entity.getMethodName());
        this.eventType = StringUtils.trimToEmpty(EventTypeEnum.valueX(entity.getEventType()));
        this.avenue = StringUtils.trimToEmpty(entity.getAvenue());
    }

    private String getAvenue(BusinessAcquisitionOdsEntity entity) {
        if(AvenueEnum.ANDROID.getTransValue().equalsIgnoreCase(entity.getPlatform())) {
            return AvenueEnum.ANDROID.getName();
        }

        if(AvenueEnum.IOS.getTransValue().equalsIgnoreCase(entity.getPlatform())) {
            return AvenueEnum.IOS.getName();
        }

        return StringUtils.trimToEmpty(entity.getPlatform());
    }

    private String getEventType(BusinessAcquisitionOdsEntity entity) {
        if(EventTypeEnum.CLICK.getTransValue().equalsIgnoreCase(entity.getParamKey())) {
            return EventTypeEnum.CLICK.getName();
        }

        if(EventTypeEnum.VIEW.getTransValue().equalsIgnoreCase(entity.getParamKey())) {
            return EventTypeEnum.VIEW.getName();
        }

        return StringUtils.trimToEmpty(entity.getParamKey()).toUpperCase();
    }
}