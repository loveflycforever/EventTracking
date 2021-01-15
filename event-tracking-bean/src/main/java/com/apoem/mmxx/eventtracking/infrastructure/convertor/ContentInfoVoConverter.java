package com.apoem.mmxx.eventtracking.infrastructure.convertor;

import com.apoem.mmxx.eventtracking.infrastructure.enums.RangeTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.HouseTypeEnum;
import com.apoem.mmxx.eventtracking.NumberUtils;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.WrapperContentInfo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.ContentInfoVo;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.support.IRangeDao2;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.RangeEntity;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ContentInfoVoConverter </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/23 10:59 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class ContentInfoVoConverter {

    public static ContentInfoVo deserialize(WrapperContentInfo contentInfo, IRangeDao2 rangeDao2) {

//        RangeDao2 bean = SpringContextHolder.getBean(RangeDao2.class);

        String city = contentInfo.getCity();
        Date opTime = contentInfo.getOpTime();

        // 总价：万元 --- 百元
        // 均价：元
        // 面积：平米 --- 平分米

        // TODO: 2020/9/15 取值逻辑待定
        BigDecimal houseArea = (contentInfo.getHouseArea());
        BigDecimal houseAveragePrice = (contentInfo.getHouseAveragePrice());
        BigDecimal houseTotalPrice = (contentInfo.getHouseTotalPrice());
        BigDecimal houseAreaLower = (contentInfo.getHouseAreaLower());
        BigDecimal houseAreaUpper = (contentInfo.getHouseAreaUpper());

        int houseBedroom = NumberUtils.trimToZero(contentInfo.getHouseBedroom());

        if (HouseTypeEnum.NWHS.maybe(contentInfo.getHouseType())) {
            // 新房面积取均值
            houseArea = (houseAreaLower.add(houseAreaUpper)).divide(new BigDecimal(2), 2, ROUND_HALF_UP);
            // 总价万元
            houseTotalPrice = (houseAveragePrice.multiply(houseArea)).divide(new BigDecimal(10000), 2, ROUND_HALF_UP);
        }

        if (HouseTypeEnum.RTHS.maybe(contentInfo.getHouseType())) {
            // 租房 总价 = 均价
            houseTotalPrice = houseAveragePrice;
        }

        // TODO: 2020/9/11 等业务接口
        List<RangeEntity> totalPriceRangeList = new ArrayList<>();
        List<RangeEntity> avgPriceRangeList = new ArrayList<>();
        List<RangeEntity> areaRangeList = new ArrayList<>();
        List<RangeEntity> layoutRangeList = new ArrayList<>();
        if (Objects.nonNull(contentInfo.getHouseType())) {
            totalPriceRangeList = rangeDao2.findByCityAndRangeTypeName(city, RangeTypeEnum.getTotalPriceRange(contentInfo.getHouseType()));
            avgPriceRangeList = rangeDao2.findByCityAndRangeTypeName(city, RangeTypeEnum.getAvgPriceRange(contentInfo.getHouseType()));
            areaRangeList = rangeDao2.findByCityAndRangeTypeName(city, RangeTypeEnum.getAreaRange(contentInfo.getHouseType()));
            layoutRangeList = rangeDao2.findByCityAndRangeTypeName(city, RangeTypeEnum.getLayoutRange(contentInfo.getHouseType()));
        }

        LocalDateTime localDateTime = LocalDateTime.ofInstant(opTime.toInstant(), ZoneId.systemDefault());
        int hour = localDateTime.getHour();
        int dayOfWeekValue = localDateTime.getDayOfWeek().getValue();

        RangeEntity range2 = findRange(areaRangeList, houseArea);
        String areaRange = range2.getLabel();
        Integer areaRangeOrder = range2.getOrderNumber();

        RangeEntity range3 = findRange(totalPriceRangeList, houseTotalPrice);
        String totalPriceRange = range3.getLabel();
        Integer totalPriceRangeOrder = range3.getOrderNumber();

        RangeEntity range4 = findRange(avgPriceRangeList, houseAveragePrice);
        String avgPriceRange = range4.getLabel();
        Integer avgPriceRangeOrder = range4.getOrderNumber();

        RangeEntity range5 = findRange1(layoutRangeList, new BigDecimal(houseBedroom));
        String layoutRange = range5.getLabel();
        Integer layoutRangeOrder = range5.getOrderNumber();

        return ContentInfoVo.builder()
                .dayOfWeek(dayOfWeekValue)
                .hourOfDay(hour)
                .resultHouseArea(houseArea)
                .resultHouseAveragePrice(houseAveragePrice)
                .resultHouseTotalPrice(houseTotalPrice)
                .resultHouseAreaLower(houseAreaLower)
                .resultHouseAreaUpper(houseAreaUpper)
                .resultHouseBedroom(houseBedroom)
                .resultAreaRange(areaRange)
                .resultAreaRangeOrder(areaRangeOrder)
                .resultTotalPriceRange(totalPriceRange)
                .resultTotalPriceRangeOrder(totalPriceRangeOrder)
                .resultAvgPriceRange(avgPriceRange)
                .resultAvgPriceRangeOrder(avgPriceRangeOrder)
                .resultLayoutRange(layoutRange)
                .resultLayoutRangeOrder(layoutRangeOrder)
                .build();
    }

    // 闭闭区间
    public static RangeEntity findRange1(List<RangeEntity> rangeList, BigDecimal value) {
        Optional<RangeEntity> max = Optional.empty();
        if (CollectionUtils.isNotEmpty(rangeList)) {
            max = rangeList.stream().filter(o -> {
                BigDecimal lowerBd = new BigDecimal(o.getLower());
                return lowerBd.compareTo(value) <= 0;
            }).max(Comparator.comparingInt(RangeEntity::getLower));
        }
        return max.orElse(new RangeEntity().init());
    }

    // 开闭区间
    public static RangeEntity findRange(List<RangeEntity> rangeList, BigDecimal value) {
        Optional<RangeEntity> max = Optional.empty();
        if (CollectionUtils.isNotEmpty(rangeList)) {
            max = rangeList.stream().filter(o -> {
                BigDecimal lowerBd = new BigDecimal(o.getLower());
                return lowerBd.compareTo(value) < 0;
            }).max(Comparator.comparingInt(RangeEntity::getLower));
        }
        return max.orElse(new RangeEntity().init());
    }
}
