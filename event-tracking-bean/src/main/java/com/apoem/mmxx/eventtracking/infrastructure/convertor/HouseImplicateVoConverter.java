package com.apoem.mmxx.eventtracking.infrastructure.convertor;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.HouseTrendVo;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerHouseRankingEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.HouseRankingEntity;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: HouseImplicateVoConverter </p>
 * <p>Description:  </p>
 * <p>Date: 2020/8/27 10:27 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class HouseImplicateVoConverter {

    public static HouseTrendVo deserialize(HouseRankingEntity entity) {
        HouseTrendVo result = new HouseTrendVo();

        Optional.ofNullable(entity).ifPresent(o -> {
            result.setId(o.getHouseId());
            result.setHouseId(o.getHouseId());
            result.setHouseType(o.getHouseType());
            result.setPvAmount(o.getPageView());
            result.setUvAmount(o.getUniqueVisitor());
            result.setColAmount(o.getCollected());
            result.setRpsAmount(o.getReposted());
        });
        return result;
    }

    public static List<HouseTrendVo> deserialize(List<CustomerHouseRankingEntity> entities) {
        if (CollectionUtils.isNotEmpty(entities)) {
            return entities.stream().map(HouseImplicateVoConverter::deserialize).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private static HouseTrendVo deserialize(CustomerHouseRankingEntity entity) {
        HouseTrendVo result = new HouseTrendVo();

        Optional.ofNullable(entity).ifPresent(o -> {
            result.setDate(DateUtils.dateString(o.getLastOpTime()));
            result.setId(o.getCustomerId());
            result.setHouseId(o.getHouseId());
            result.setHouseType(o.getHouseType());
            result.setPvAmount(o.getPageView());
            result.setUvAmount(o.getUniqueVisitor());
            result.setColAmount(o.getCollected());
            result.setRpsAmount(o.getReposted());
        });
        return result;
    }
}
