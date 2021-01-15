package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.BeanCopierUtils;
import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.AgentVisitTrendEntity;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AgentTrendCrossUtils </p>
 * <p>Description:  </p>
 * <p>Date: 2020/12/3 10:01 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public final class AgentTrendCrossUtils {

    public static List<AgentVisitTrendEntity> exe(LocalDateTime localDateTime, List<AgentVisitTrendEntity> crossData, List<AgentVisitTrendEntity> mrConvertedData) {
        List<AgentVisitTrendEntity> result = new ArrayList<>();


        Date current = new Date();
        String dateDay = DateUtils.literalYyyyMmDd(localDateTime);

        Set<ImmutableTriple<String, String, String>> complementaryKeys1 = complementary(crossData, mrConvertedData);

        Map<ImmutableTriple<String, String, String>, AgentVisitTrendEntity> prevDataMap = crossData.stream().collect(Collectors.toMap(AgentTrendCrossUtils::getKey, o -> o, (key1, key2) -> key2));
        Map<ImmutableTriple<String, String, String>, AgentVisitTrendEntity> thisDataMap = mrConvertedData.stream().collect(Collectors.toMap(AgentTrendCrossUtils::getKey, o -> o, (key1, key2) -> key2));

        complementaryKeys1.forEach(key -> {
            AgentVisitTrendEntity source = prevDataMap.get(key);
            AgentVisitTrendEntity target = new AgentVisitTrendEntity();
            BeanCopierUtils.copy(source, target);
            target.setInitial(1);
            target.setDateDay(Integer.parseInt(dateDay));

            target.setPrevPageView(source.getPageView());
            target.setPrevUniqueVisitor(source.getUniqueVisitor());
            target.setPrevPageViewRiseRate(source.getPageViewRiseRate());
            target.setPrevUniqueVisitorRiseRate(source.getUniqueVisitorRiseRate());
            target.setPrevTotalPageView(source.getTotalPageView());
            target.setPrevTotalUniqueVisitor(source.getTotalUniqueVisitor());

            target.setPageView(0L);
            target.setUniqueVisitor(0L);
            target.setPageViewRiseRate(BigDecimal.ZERO);
            target.setUniqueVisitorRiseRate(BigDecimal.ZERO);

            target.setCreateTime(current);
            target.setUpdateTime(current);

            result.add(target);
        });

        Set<ImmutableTriple<String, String, String>> complementaryKeys2 = complementary(mrConvertedData, crossData);

        complementaryKeys2.forEach(key -> {
            AgentVisitTrendEntity source = thisDataMap.get(key);
            AgentVisitTrendEntity target = thisDataMap.get(key);
            target.setInitial(0);
            target.setDateDay(Integer.parseInt(dateDay));

            target.setPrevPageView(0L);
            target.setPrevUniqueVisitor(0L);
            target.setPrevPageViewRiseRate(BigDecimal.ZERO);
            target.setPrevUniqueVisitorRiseRate(BigDecimal.ZERO);
            target.setPrevTotalPageView(0L);
            target.setPrevTotalUniqueVisitor(0L);

            target.setPageViewRiseRate(BigDecimal.ONE);
            target.setUniqueVisitorRiseRate(BigDecimal.ONE);

            target.setTotalPageView(source.getPageView());
            target.setTotalUniqueVisitor(source.getUniqueVisitor());

            target.setCreateTime(current);
            target.setUpdateTime(current);
            target.setMark(0);

            result.add(target);
        });

        Set<ImmutableTriple<String, String, String>> intersectionKeys = intersection(crossData, mrConvertedData);

        intersectionKeys.forEach(key -> {
            AgentVisitTrendEntity source = prevDataMap.get(key);
            AgentVisitTrendEntity target = thisDataMap.get(key);
            target.setInitial(1);
            target.setDateDay(Integer.parseInt(dateDay));

            target.setPrevPageView(source.getPageView());
            target.setPrevUniqueVisitor(source.getUniqueVisitor());
            target.setPrevPageViewRiseRate(source.getPageViewRiseRate());
            target.setPrevUniqueVisitorRiseRate(source.getUniqueVisitorRiseRate());
            target.setPrevTotalPageView(source.getTotalPageView());
            target.setPrevTotalUniqueVisitor(source.getTotalUniqueVisitor());

            BigDecimal thisPageView = new BigDecimal(target.getPageView());
            BigDecimal prevPageView = new BigDecimal(source.getPageView());
            BigDecimal thisUniqueVisitor = new BigDecimal(target.getUniqueVisitor());
            BigDecimal prevUniqueVisitor = new BigDecimal(source.getUniqueVisitor());
            target.setPageViewRiseRate(ObjectUtils.notEqual(prevPageView, BigDecimal.ZERO) ? thisPageView.subtract(prevPageView).divide(prevPageView, 4, RoundingMode.HALF_UP) : ObjectUtils.notEqual(thisPageView, 0) ? BigDecimal.ONE : BigDecimal.ZERO);
            target.setUniqueVisitorRiseRate(ObjectUtils.notEqual(prevUniqueVisitor, BigDecimal.ZERO) ? thisUniqueVisitor.subtract(prevUniqueVisitor).divide(prevUniqueVisitor, 4, RoundingMode.HALF_UP) : ObjectUtils.notEqual(thisUniqueVisitor, 0) ? BigDecimal.ONE : BigDecimal.ZERO);

            target.setTotalPageView(source.getTotalPageView() + target.getPageView());
            target.setTotalUniqueVisitor(source.getTotalUniqueVisitor() + target.getUniqueVisitor());

            target.setCreateTime(current);
            target.setUpdateTime(current);
            target.setMark(0);

            result.add(target);
        });
        return result;
    }

    private static  Set<ImmutableTriple<String, String, String>> intersection(List<AgentVisitTrendEntity> list1, List<AgentVisitTrendEntity> list2) {
        Map<ImmutableTriple<String, String, String>, AgentVisitTrendEntity> map1 = list1.stream().collect(Collectors.toMap(AgentTrendCrossUtils::getKey, o -> o, (key1, key2) -> key2));
        Map<ImmutableTriple<String, String, String>, AgentVisitTrendEntity> map2 = list2.stream().collect(Collectors.toMap(AgentTrendCrossUtils::getKey, o -> o, (key1, key2) -> key2));

        Set<ImmutableTriple<String, String, String>> set1 = map1.keySet();
        set1.retainAll(map2.keySet());
        return set1;
    }

    private static ImmutableTriple<String, String, String> getKey(AgentVisitTrendEntity o) {
        return ImmutableTriple.of(o.getCity(), o.getAgentId(), o.getHouseType());
    }

    private static  Set<ImmutableTriple<String, String, String>> complementary(List<AgentVisitTrendEntity> list1, List<AgentVisitTrendEntity> list2) {
        Map<ImmutableTriple<String, String, String>, AgentVisitTrendEntity> map1 = list1.stream().collect(Collectors.toMap(AgentTrendCrossUtils::getKey, o -> o, (key1, key2) -> key2));
        Map<ImmutableTriple<String, String, String>, AgentVisitTrendEntity> map2 = list2.stream().collect(Collectors.toMap(AgentTrendCrossUtils::getKey, o -> o, (key1, key2) -> key2));

        Set<ImmutableTriple<String, String, String>> set1 = map1.keySet();
        set1.removeAll(map2.keySet());
        return set1;
    }
}
