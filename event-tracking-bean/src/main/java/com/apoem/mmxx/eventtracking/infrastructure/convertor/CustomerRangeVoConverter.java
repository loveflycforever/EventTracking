package com.apoem.mmxx.eventtracking.infrastructure.convertor;

import com.apoem.mmxx.eventtracking.NumberUtils;
import com.apoem.mmxx.eventtracking.infrastructure.enums.RangeGlanceTimeLegendEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.RangeGlanceFieldEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.RangeTypeEnum;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.CustomerRangeGlanceVo;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.support.IRangeDao2;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.CustomerHouseRankingEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.RangeEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.support.HourView;
import org.apache.commons.collections4.CollectionUtils;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CustomerRangeVoConverter </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/1 12:54 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class CustomerRangeVoConverter {

    public static CustomerRangeGlanceVo deserializeFake(String city, String scope, String field, List<CustomerHouseRankingEntity> range, IRangeDao2 rangeDao2) {


//        RangeDao2 bean = SpringContextHolder.getBean(RangeDao2.class);
        CustomerRangeGlanceVo result = new CustomerRangeGlanceVo();
        List<CustomerRangeGlanceVo.VisitStatsNode> rows = new ArrayList<>();

        if (RangeGlanceFieldEnum.PRICE.getName().equals(field)) {
            // 二手房、新房 用 二手房 范围

            RangeTypeEnum rangeTypeEnum = RangeTypeEnum.getTotalPriceRange(scope);

            List<RangeEntity> byCityAndRangeTypeName = rangeDao2.findByCityAndRangeTypeName(city, rangeTypeEnum);

            if (CollectionUtils.isNotEmpty(byCityAndRangeTypeName)) {
                rows = byCityAndRangeTypeName.stream().map(o -> {
                    Long collect = range.stream().filter(r -> {
                        long l = r.getHouseTotalPrice() / 100;
                        return l >= o.getLower() && l < o.getUpper();
                    }).mapToLong(CustomerHouseRankingEntity::getPageView).sum();

                    CustomerRangeGlanceVo.VisitStatsNode node = new CustomerRangeGlanceVo.VisitStatsNode();
                    node.setName(o.getLabel());
                    node.setAmount(collect);
                    node.setOrder(o.getOrderNumber());
                    return node;
                }).collect(Collectors.toList());
            }
        }

        if (RangeGlanceFieldEnum.AREA.getName().equals(field)) {
            // 二手房、新房 用 二手房 范围
            RangeTypeEnum rangeTypeEnum = RangeTypeEnum.getAreaRange(scope);

            List<RangeEntity> byCityAndRangeTypeName = rangeDao2.findByCityAndRangeTypeName(city, rangeTypeEnum);

            if (CollectionUtils.isNotEmpty(byCityAndRangeTypeName)) {
                rows = byCityAndRangeTypeName.stream().map(o -> {
                    Long collect = range.stream().filter(r -> {
                        long l = r.getHouseArea() / 100;
                        return l >= o.getLower() && l < o.getUpper();
                    }).mapToLong(CustomerHouseRankingEntity::getPageView).sum();

                    CustomerRangeGlanceVo.VisitStatsNode node = new CustomerRangeGlanceVo.VisitStatsNode();
                    node.setName(o.getLabel());
                    node.setAmount(collect);
                    node.setOrder(o.getOrderNumber());
                    return node;
                }).collect(Collectors.toList());
            }
        }
        result.setTotalAmounts(rows.stream().mapToLong(CustomerRangeGlanceVo.VisitStatsNode::getAmount).sum());
        result.setRows(rows);
        return result;
    }

    public static CustomerRangeGlanceVo deserializeFake2(String field, List<HourView> entities) {

        List<CustomerRangeGlanceVo.VisitStatsNode> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(entities)) {
            Map<DayOfWeek, HourView> collect = entities.stream().collect(Collectors.groupingBy(o -> DayOfWeek.of(o.getDayOfWeek()), TreeMap::new,
                    Collectors.collectingAndThen(Collectors.toList(), l ->
                            /*
                             * "时间段
                             * 07:00-11:00（不含11:00）
                             * 11:00-15:00（不含15:00）
                             * 15:00-19:00（不含19:00）
                             * 19:00-23:00（不含23:00）
                             * 23:00-07:00（即23:00-24:00&00:00-07:00，不含24:00，07:00）"
                             */
                            l.stream().reduce(new HourView(), (o1, o2) -> {
                                o1.setH0(NumberUtils.add(
                                        o1.getH7(), o2.getH7(),
                                        o1.getH8(), o2.getH8(),
                                        o1.getH9(), o2.getH9(),
                                        o1.getH10(), o2.getH10()));
                                o1.setH1(NumberUtils.add(
                                        o1.getH11(), o2.getH11(),
                                        o1.getH12(), o2.getH12(),
                                        o1.getH13(), o2.getH13(),
                                        o1.getH14(), o2.getH14()));
                                o1.setH2(NumberUtils.add(
                                        o1.getH15() + o2.getH15(),
                                        o1.getH16(), o2.getH16(),
                                        o1.getH17(), o2.getH17(),
                                        o1.getH18(), o2.getH18()));
                                o1.setH3(NumberUtils.add(
                                        o1.getH19(), o2.getH19(),
                                        o1.getH20(), o2.getH20(),
                                        o1.getH21(), o2.getH21(),
                                        o1.getH22(), o2.getH22()));
                                o1.setH4(NumberUtils.add(
                                        o1.getH23(), o2.getH23(),
                                        o1.getH0(), o2.getH0(),
                                        o1.getH1(), o2.getH1(),
                                        o1.getH2(), o2.getH2(),
                                        o1.getH3(), o2.getH3(),
                                        o1.getH4(), o2.getH4(),
                                        o1.getH5(), o2.getH5(),
                                        o1.getH6(), o2.getH6()));
                                return o1;
                            }))));


            Arrays.stream(DayOfWeek.values()).forEach(o -> collect.putIfAbsent(o, new HourView(o.getValue())));
            collect.forEach((key, value) -> {
                list.add(convert(key, RangeGlanceTimeLegendEnum.W7, value.getH0()));
                list.add(convert(key, RangeGlanceTimeLegendEnum.W11, value.getH1()));
                list.add(convert(key, RangeGlanceTimeLegendEnum.W15, value.getH2()));
                list.add(convert(key, RangeGlanceTimeLegendEnum.W19, value.getH3()));
                list.add(convert(key, RangeGlanceTimeLegendEnum.W23, value.getH4()));
            });
        }

        CustomerRangeGlanceVo result = new CustomerRangeGlanceVo();
        result.setTotalAmounts(list.stream().mapToLong(CustomerRangeGlanceVo.VisitStatsNode::getAmount).sum());
        result.setRows(list);
        return result;
    }

    private static CustomerRangeGlanceVo.VisitStatsNode convert(DayOfWeek key, RangeGlanceTimeLegendEnum legendEnum, long hn) {
        String label = key.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + legendEnum.getLabel();
        CustomerRangeGlanceVo.VisitStatsNode node = new CustomerRangeGlanceVo.VisitStatsNode();
        node.setName(label);
        node.setAmount(hn);
        return node;
    }
}
