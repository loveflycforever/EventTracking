package com.apoem.mmxx.eventtracking.infrastructure.convertor;

import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.AgentHouseRankingVo;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.HouseRankingEntity;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentHouseRankingVoConverter </p>
 * <p>Description:  </p>
 * <p>Date: 2020/8/24 17:07 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class AgentHouseRankingVoConverter {
    public static List<AgentHouseRankingVo> deserialize(List<HouseRankingEntity> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            return list.stream().map(AgentHouseRankingVoConverter::deserialize).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private static AgentHouseRankingVo deserialize(HouseRankingEntity entity) {
        AgentHouseRankingVo result = new AgentHouseRankingVo();

        Optional.ofNullable(entity).ifPresent(o -> {
            result.setId(o.getHouseId());
            result.setType(o.getHouseType());
            result.setName(o.getHouseName());

            result.setPvAmount(o.getPageView());
            result.setUvAmount(o.getUniqueVisitor());
            result.setColAmount(o.getCollected());
            result.setRpsAmount(o.getReposted());
        });

        return result;
    }
}
