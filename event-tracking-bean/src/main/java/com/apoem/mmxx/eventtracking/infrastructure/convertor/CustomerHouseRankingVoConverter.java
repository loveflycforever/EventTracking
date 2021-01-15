package com.apoem.mmxx.eventtracking.infrastructure.convertor;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.CustomerCommunityRankingVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.CustomerHouseRankingVo;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerHouseRankingEntity;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
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
public class CustomerHouseRankingVoConverter {

    public static List<CustomerCommunityRankingVo> deserialize(CustomerHouseRankingEntity total,
                                                               List<CustomerHouseRankingEntity> community,
                                                               List<List<CustomerHouseRankingEntity>> houses) {
        Long totalPvAmount = total.getPageView();

        List<CustomerCommunityRankingVo> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(community)) {

            for (int i = 0; i < community.size(); i++) {
                CustomerHouseRankingEntity customerHouseRankingEntity = community.get(i);
                CustomerCommunityRankingVo customerCommunityRankingVo = deserialize(totalPvAmount, customerHouseRankingEntity);
                List<CustomerHouseRankingVo> houseRankingVos = CustomerHouseRankingVoConverter.deserialize(houses.get(i));
                customerCommunityRankingVo.setRows(houseRankingVos);
                result.add(customerCommunityRankingVo);
            }
        }
        return result;
    }

    private static CustomerCommunityRankingVo deserialize(Long total, CustomerHouseRankingEntity entity) {
        CustomerCommunityRankingVo result = new CustomerCommunityRankingVo();

        Optional.ofNullable(entity).ifPresent(o -> {
            result.setId(o.getCommunityId());
            result.setType(o.getHouseType());
            result.setName(o.getCommunityName());
            result.setDate(DateUtils.dateString(new Date()));

            result.setPvAmount(o.getPageView());
            result.setTotalPvAmount(total);
        });
        return result;
    }


    public static List<CustomerHouseRankingVo> deserialize(List<CustomerHouseRankingEntity> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            return list.stream().map(CustomerHouseRankingVoConverter::deserialize).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private static CustomerHouseRankingVo deserialize(CustomerHouseRankingEntity entity) {
        CustomerHouseRankingVo result = new CustomerHouseRankingVo();

        Optional.ofNullable(entity).ifPresent(o -> {
            result.setId(o.getHouseId());
            result.setType(o.getHouseType());
            result.setName(o.getHouseName());

            result.setPvAmount(o.getPageView());
        });

        return result;
    }
}
