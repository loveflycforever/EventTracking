package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import com.google.common.math.LongMath;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.math.RoundingMode;
import java.util.AbstractList;

import static com.google.common.base.Preconditions.checkElementIndex;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: LongPair </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/4 11:31 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class LongIdPair extends Pair<Long, Long> {

    private final Pair<Long, Long> pair;

    public LongIdPair(Pair<Long, Long> pair) {
        this.pair = Pair.of(pair.getLeft(), pair.getRight());
    }

    public Long getMinId() {
        return pair.getLeft();
    }

    public Long getMaxId() {
        return pair.getRight();
    }

    @Override
    public Long getLeft() {
        return pair.getLeft();
    }

    @Override
    public Long getRight() {
        return pair.getRight();
    }

    @Override
    public Long setValue(Long value) {
        return pair.setValue(value);
    }

    public boolean isAvailable() {
        return pair.getLeft() > 0 && pair.getLeft().compareTo(pair.getRight()) <= 0;
    }

    public long range() {
        return pair.getRight() - pair.getLeft();
    }

    public Partition partition(long space) {
        return new Partition(pair.getLeft(), pair.getRight(), space);
    }

    @Getter
    public static class Partition extends AbstractList<Partition> {
        final long beginId;
        final long endId;
        final long space;

        Partition(long beginId, long endId, long space) {
            this.beginId = beginId;
            this.endId = endId;
            this.space = space;
        }

        @Override
        public Partition get(int index) {
            checkElementIndex(index, size());
            long start = beginId + index * space;
            long end = Math.min(start + space - 1, this.endId);
            return new Partition(start, end, space);
        }

        @Override
        public int size() {
            // 理论上不会超过Integer.MAX吧 QAQ
            return (int) LongMath.divide(endId - beginId, space, RoundingMode.CEILING);
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }
}
