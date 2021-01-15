package com.apoem.mmxx.eventtracking.infrastructure.convertor;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.AgentCommunityRankingEntity;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.AgentCommunityRankingVo;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentCommunityRankingVoConverter </p>
 * <p>Description:  </p>
 * <p>Date: 2020/8/24 17:07 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class AgentCommunityRankingVoConverter {
    public static List<AgentCommunityRankingVo> deserialize(List<AgentCommunityRankingEntity> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            return list.stream().map(AgentCommunityRankingVoConverter::deserialize).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private static AgentCommunityRankingVo deserialize(AgentCommunityRankingEntity entity) {
        AgentCommunityRankingVo result = new AgentCommunityRankingVo();

        Optional.ofNullable(entity).ifPresent(o -> {
            result.setId(o.getCommunityId());
            result.setType(o.getHouseType());
            result.setName(o.getCommunityName());
            result.setDate(DateUtils.dateString(new Date()));

            result.setPvAmount(o.getPageView());
            result.setUvAmount(o.getUniqueVisitor());
            result.setColAmount(o.getCollected());
            result.setRpsAmount(o.getReposted());

            result.setTotalPvAmount(o.getTotalPageView());
            result.setTotalUvAmount(o.getTotalUniqueVisitor());
            result.setTotalColAmount(o.getTotalCollected());
            result.setTotalRpsAmount(o.getTotalReposted());
        });

        return result;
    }
}
