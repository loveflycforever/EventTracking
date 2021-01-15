package com.apoem.mmxx.eventtracking.infrastructure.convertor;

import com.apoem.mmxx.eventtracking.BaseConstants;
import com.apoem.mmxx.eventtracking.NumberUtils;
import com.apoem.mmxx.eventtracking.infrastructure.enums.HouseTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerBriefEntity;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.CustomerBriefVo;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CustomerBriefVoConverter </p>
 * <p>Description:  </p>
 * <p>Date: 2020/8/31 13:59 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class CustomerBriefVoConverter {
    public static CustomerBriefVo deserialize(List<CustomerBriefEntity> entities) {
        CustomerBriefVo result = new CustomerBriefVo();

        if (CollectionUtils.isNotEmpty(entities)) {

            HashMap<String, CustomerBriefEntity> map = entities.stream()
                    .collect(Collectors.toMap(CustomerBriefEntity::getHouseType, o -> o, (o1, o2) -> o1, HashMap::new));

            CustomerBriefEntity newHouse = map.get(HouseTypeEnum.NWHS.getName());
            CustomerBriefEntity rentedHouse = map.get(HouseTypeEnum.RTHS.getName());
            CustomerBriefEntity secondHandHouse = map.get(HouseTypeEnum.SHHS.getName());

            Optional.ofNullable(newHouse).ifPresent(o -> result.setNewHouse(deserialize(o)));
            Optional.ofNullable(rentedHouse).ifPresent(o -> result.setRentedHouse(deserialize(o)));
            Optional.ofNullable(secondHandHouse).ifPresent(o -> result.setSecondHandHouse(deserialize(o)));
        }

        return result;
    }

    private static CustomerBriefVo.VisitBriefView deserialize(CustomerBriefEntity entity) {
        CustomerBriefVo.VisitBriefView result = new CustomerBriefVo.VisitBriefView();
        Optional.ofNullable(entity).ifPresent(o -> {

            result.setHouseId(o.getHouseId());
            result.setHouseName(o.getHouseName());

            // TODO: 2020/9/15
            StringBuilder format = new StringBuilder();
            format.append(String.format("%s室", NumberUtils.trimToZero(o.getHouseBedroom())));

            if(NumberUtils.compareBiggerThanZero(o.getHouseLivingRoom())) {
                format.append(String.format("%s厅", o.getHouseLivingRoom()));
            }

            if(NumberUtils.compareBiggerThanZero(o.getHouseBathroom())) {
                format.append(String.format("%s卫", o.getHouseBathroom()));
            }
            result.setLayout(format.toString());

            // 对所有浏览的面积取中位数，取中位数±10%显示
            BigDecimal area = new BigDecimal(o.getHouseArea());
            result.setArea(area.divide(BaseConstants.BIG_DECIMAL_100, 0, RoundingMode.HALF_UP));
            result.setAreaLowerLimit(area.multiply(new BigDecimal("0.9")).divide(BaseConstants.BIG_DECIMAL_100, 0, RoundingMode.HALF_UP));
            result.setAreaUpperLimit(area.multiply(new BigDecimal("1.1")).divide(BaseConstants.BIG_DECIMAL_100, 0, RoundingMode.HALF_UP));
            // 对所有浏览的价格取中位数，取中位数±10%显示
            result.setPrice(new BigDecimal(o.getHouseTotalPrice()).divide(BaseConstants.BIG_DECIMAL_100, 0, RoundingMode.HALF_UP));
            result.setPriceLowerLimit(new BigDecimal(o.getHouseTotalPrice()).multiply(new BigDecimal("0.9")).divide(BaseConstants.BIG_DECIMAL_100, 0, RoundingMode.HALF_UP));
            result.setPriceUpperLimit(new BigDecimal(o.getHouseTotalPrice()).multiply(new BigDecimal("1.1")).divide(BaseConstants.BIG_DECIMAL_100, 0, RoundingMode.HALF_UP));

            result.setCommunityId(o.getCommunityId());
            result.setCommunityName(o.getCommunityName());
            result.setHouseBedroom(o.getHouseBedroom());
            result.setHouseLivingRoom(o.getHouseLivingRoom());
            result.setHouseBathRoom(o.getHouseBathroom());
        });
        return result;
    }
}
