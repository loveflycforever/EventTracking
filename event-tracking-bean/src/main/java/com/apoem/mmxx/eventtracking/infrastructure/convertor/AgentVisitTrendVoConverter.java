package com.apoem.mmxx.eventtracking.infrastructure.convertor;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.AgentVisitTrendEntity;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.AgentVisitTrendVo;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentVisitTrendVoConverter </p>
 * <p>Description: 经纪人访问趋势转换器 </p>
 * <p>Date: 2020/8/24 9:32 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public final class AgentVisitTrendVoConverter {

    public static List<AgentVisitTrendVo> deserialize(List<AgentVisitTrendEntity> list, LocalDateTime localDateTime) {

        if (CollectionUtils.isNotEmpty(list)) {
            Integer minDateDay = list.stream().min(Comparator.comparing(AgentVisitTrendEntity::getDateDay))
                    .map(AgentVisitTrendEntity::getDateDay)
                    .orElse(DateUtils.numericalYyyyMmDd(localDateTime));
            return list.stream()
                    .sorted(Comparator.comparing(AgentVisitTrendEntity::getDateDay).reversed())
                    .filter(o -> o.getDateDay() <= DateUtils.numericalYyyyMmDd(localDateTime))
                    .map(AgentVisitTrendVoConverter::deserialize)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private static AgentVisitTrendVo deserialize(AgentVisitTrendEntity entity) {
        AgentVisitTrendVo result = new AgentVisitTrendVo();

        Optional.ofNullable(entity).ifPresent(o -> {
            result.setDate(DateUtils.localDateTimeString(o.getDateDay()));
            result.setPvAmount(o.getPageView());
            result.setUvAmount(o.getUniqueVisitor());
            result.setPvRiseRate(o.getPageViewRiseRate());
            result.setUvRiseRate(o.getUniqueVisitorRiseRate());
            result.setPvTotal(o.getTotalPageView());
            result.setUvTotal(o.getTotalUniqueVisitor());
        });
        return result;
    }
}
