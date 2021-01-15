package com.apoem.mmxx.eventtracking.exception;

import lombok.Getter;

import java.util.function.Function;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: Junction </p>
 * <p>Description: Function 易用类 </p>
 * <p>Date: 2020/9/4 8:16 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Getter
public class Junction<In, Out> implements Function<In, Out> {

    private final In in;
    private Out out;

    private final Function<In, Out> function;

    public Junction(Function<In, Out> function, In in) {
        this.in = in;
        this.function = function;
    }

    public Out apply() {
        this.out = function.apply(this.in);
        return this.out;
    }


    @Override
    public Out apply(In in) {
        this.out = function.apply(in);
        return this.out;
    }
}
