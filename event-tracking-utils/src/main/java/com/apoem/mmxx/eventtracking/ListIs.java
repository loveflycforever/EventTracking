package com.apoem.mmxx.eventtracking;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: Lists </p>
 * <p>Description:  </p>
 * <p>Date: 2020/8/3 18:22 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class ListIs {
    public ListIs() {
    }

    public static <K> List<K> nonNull(Collection<K> c) {
        if (Objects.isNull(c) || c.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(c);
    }

    public static <K> List<K> zip(List<K> j, List<K> k, BiFunction<K, K, K> zipFunction) {
        if (Objects.isNull(j) || j.isEmpty()) {
            return k;
        }

        if (Objects.isNull(k) || k.isEmpty()) {
            return j;
        }

        int min = Math.min(j.size(), k.size());
        List<K> result = new ArrayList<>(min);
        for (int i = 0; i < min; i++) {
            result.add(zipFunction.apply(j.get(i), k.get(i)));
        }
        return result;
    }

    public static <K> List<Pair<Integer, K>> index(K[] j) {
        if (Objects.isNull(j) || j.length <= 0) {
            return new ArrayList<>();
        }
        int size = j.length;
        List<Pair<Integer, K>> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(Pair.of(i, j[i]));
        }
        return result;
    }

    public static <K> boolean only(Collection<K> collection, K key) {
        if(collection == null || collection.size() == 0 || key == null) {
            return false;
        }

        boolean notOnly = false;
        for (K k1 : collection) {
            notOnly = notOnly || !k1.equals(key);
        }
        return !notOnly;
    }
}
