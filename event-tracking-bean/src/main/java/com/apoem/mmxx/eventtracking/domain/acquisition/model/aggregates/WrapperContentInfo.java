package com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates;

import com.apoem.mmxx.eventtracking.BigDecimalUtils;
import com.apoem.mmxx.eventtracking.NumberUtils;
import com.apoem.mmxx.eventtracking.infrastructure.enums.HouseTypeEnum;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ExtraInfo </p>
 * <p>Description: 额外信息，记录页面详情 </p>
 * <p>Date: 2020/7/17 11:09 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
public class WrapperContentInfo {

    /**
     * 额外数据
     */
    private ContentInfo contentInfo;

    /**
     * 原始数据
     */
    private String city;
    private Date opTime;
    private String openId;

    public static boolean isAgent(WrapperContentInfo info) {
        return checkField(info.getContentInfo(), ContentInfo::getAgentId);
    }

    public static boolean isInformation(WrapperContentInfo info) {
        return checkField(info.getContentInfo(), ContentInfo::getInformationId)
                && checkField(info.getContentInfo(), ContentInfo::getAgentId);
    }

    public static boolean isHouse(WrapperContentInfo info) {
        return checkField(info.getContentInfo(), ContentInfo::getHouseId)
                && checkField(info.getContentInfo(), ContentInfo::getHouseType)
                && checkField(info.getContentInfo(), ContentInfo::getAgentId);
    }

    public static boolean isHouseTemplate(WrapperContentInfo info) {
        return checkField(info.getContentInfo(), ContentInfo::getPosterCode);
    }

    public static boolean isPoster(WrapperContentInfo info) {
        return checkField(info.getContentInfo(), ContentInfo::getPosterId)
                && checkField(info.getContentInfo(), ContentInfo::getAgentId);
    }

    public static boolean isGame(WrapperContentInfo info) {
        return checkField(info.getContentInfo(), ContentInfo::getGameId)
                && checkField(info.getContentInfo(), ContentInfo::getOccurred)
                && checkField(info.getContentInfo(), ContentInfo::getStoreId)
                && checkField(info.getContentInfo(), ContentInfo::getAgentId);
    }

    private static boolean checkField(ContentInfo info, Function<ContentInfo, Object> function) {
        return Objects.nonNull(function.apply(info));
    }

    public WrapperContentInfo initForTrail() {
        ContentInfo contentInfo = new ContentInfo();
        contentInfo.setAgentId(StringUtils.EMPTY);
        contentInfo.setHouseId(StringUtils.EMPTY);
        contentInfo.setHouseType(StringUtils.EMPTY);
        this.contentInfo = contentInfo;
        return this;
    }

    public String getHouseId() {
//        return StringUtils.trimToEmpty(this.contentInfo.getHouseId());
        // 业务新房公盘逻辑坑，新房房源拼接
        if (HouseTypeEnum.NWHS.maybe(getHouseType())) {
            return StringUtils.joinWith("_",
                    StringUtils.trimToEmpty(this.contentInfo.getHouseId()),
                    StringUtils.trimToEmpty(this.contentInfo.getAgentId()));
        }

        return StringUtils.trimToEmpty(this.contentInfo.getHouseId());
    }

    public String getHouseName() {
        return StringUtils.trimToEmpty(this.contentInfo.getHouseName());
    }

    public String getHouseType() {
        return StringUtils.trimToEmpty(this.contentInfo.getHouseType());
    }

    public BigDecimal getHouseArea() {
        return BigDecimalUtils.trimToZero(this.contentInfo.getHouseArea());
    }

    public BigDecimal getHouseTotalPrice() {
        return BigDecimalUtils.trimToZero(this.contentInfo.getHouseTotalPrice());
    }

    public BigDecimal getHouseAveragePrice() {
        return BigDecimalUtils.trimToZero(this.contentInfo.getHouseAveragePrice());
    }

    public BigDecimal getHouseAreaLower() {
        return BigDecimalUtils.trimToZero(this.contentInfo.getHouseAreaLower());
    }

    public BigDecimal getHouseAreaUpper() {
        return BigDecimalUtils.trimToZero(this.contentInfo.getHouseAreaUpper());
    }

    public Integer getHouseLivingRoom() {
        return NumberUtils.trimToZero(this.contentInfo.getHouseLivingRoom());
    }

    public Integer getHouseBedroom() {
        return NumberUtils.trimToZero(this.contentInfo.getHouseBedroom());
    }

    public Integer getHouseBathroom() {
        return NumberUtils.trimToZero(this.contentInfo.getHouseBathroom());
    }

    public String getCommunityId() {
        return StringUtils.trimToEmpty(this.contentInfo.getCommunityId());
    }

    public String getCommunityName() {
        return StringUtils.trimToEmpty(this.contentInfo.getCommunityName());
    }

    public String getPlateId() {
        return StringUtils.trimToEmpty(this.contentInfo.getPlateId());
    }

    public String getPlateName() {
        return StringUtils.trimToEmpty(this.contentInfo.getPlateName());
    }

    public String getAgentId() {
        return StringUtils.trimToEmpty(this.contentInfo.getAgentId());
    }

    public String getStoreId() {
        return StringUtils.trimToEmpty(this.contentInfo.getStoreId());
    }

    public String getStoreName() {
        return StringUtils.trimToEmpty(this.contentInfo.getStoreName());
    }

    public String getCompanyId() {
        return StringUtils.trimToEmpty(this.contentInfo.getCompanyId());
    }

    public String getCompanyName() {
        return StringUtils.trimToEmpty(this.contentInfo.getCompanyName());
    }

    public String getBlockId() {
        return StringUtils.trimToEmpty(this.contentInfo.getBlockId());
    }

    public String getBlockName() {
        return StringUtils.trimToEmpty(this.contentInfo.getBlockName());
    }

    public String getInputType() {
        return StringUtils.trimToEmpty(this.contentInfo.getInputType());
    }

    public String getInformationId() {
        return StringUtils.trimToEmpty(this.contentInfo.getInformationId());
    }

    public String getPosterCode() {
        return StringUtils.trimToEmpty(this.contentInfo.getPosterCode());
    }

    public String getPosterId() {
        return StringUtils.trimToEmpty(this.contentInfo.getPosterId());
    }

    public String getGameId(){
        return StringUtils.trimToEmpty(this.contentInfo.getGameId());
    }

    public String getOccurred(){
        return StringUtils.trimToEmpty(this.contentInfo.getOccurred());
    }

    public String getLocalStatus() {
        return StringUtils.trimToEmpty(this.contentInfo.getLocalStatus());
    }

    public String getTest(){
        String test = StringUtils.trimToEmpty(this.contentInfo.getTest());
        if("1".equalsIgnoreCase(test) || "YES".equalsIgnoreCase(test)) {
            return "YES";
        }
        return "NO";
    }
}
