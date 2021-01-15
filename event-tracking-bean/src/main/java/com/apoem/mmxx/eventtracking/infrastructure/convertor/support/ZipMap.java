package com.apoem.mmxx.eventtracking.infrastructure.convertor.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BaseStatsRow </p>
 * <p>Description: 集合易用工具类 </p>
 * <p>Date: 2020/8/10 18:56 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@AllArgsConstructor
public class ZipMap<K, L1E, L2E> {
    private List<L1E> list1;
    private List<L2E> list2;

    private Function<L1E, K> function1;
    private Function<L2E, K> function2;

    public List<Pair<L1E, L2E>> getList() {
        Map<K, Pair<L1E, L2E>> map = new HashMap<>();

        if (CollectionUtils.isNotEmpty(list1)) {
            for (L1E l1E : list1) {
                K apply = function1.apply(l1E);
                map.put(apply, Pair.of(l1E, null));
            }
        }


        if (CollectionUtils.isNotEmpty(list2)) {
            for (L2E l2E : list2) {
                K apply = function2.apply(l2E);
                map.computeIfPresent(apply, (k, v) -> Pair.of(v.getLeft(), l2E));
                map.computeIfAbsent(apply, k -> Pair.of(null, l2E));
            }
        }

        return new ArrayList<>(map.values());
    }
}