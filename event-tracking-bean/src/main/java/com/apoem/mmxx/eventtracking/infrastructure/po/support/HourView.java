package com.apoem.mmxx.eventtracking.infrastructure.po.support;

import lombok.Data;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: VisitTimesRecord </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/11 10:17 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
public class HourView {

    public HourView() {
    }

    public HourView(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public HourView(int dayOfWeek, int hour) {
        this.dayOfWeek = dayOfWeek;
        // ......
        switch (hour) {
            case 0:
                h0 = 1;
                break;
            case 1:
                h1 = 1;
                break;
            case 2:
                h2 = 1;
                break;
            case 3:
                h3 = 1;
                break;
            case 4:
                h4 = 1;
                break;
            case 5:
                h5 = 1;
                break;
            case 6:
                h6 = 1;
                break;
            case 7:
                h7 = 1;
                break;
            case 8:
                h8 = 1;
                break;
            case 9:
                h9 = 1;
                break;
            case 10:
                h10 = 1;
                break;
            case 11:
                h11 = 1;
                break;
            case 12:
                h12 = 1;
                break;
            case 13:
                h13 = 1;
                break;
            case 14:
                h14 = 1;
                break;
            case 15:
                h15 = 1;
                break;
            case 16:
                h16 = 1;
                break;
            case 17:
                h17 = 1;
                break;
            case 18:
                h18 = 1;
                break;
            case 19:
                h19 = 1;
                break;
            case 20:
                h20 = 1;
                break;
            case 21:
                h21 = 1;
                break;
            case 22:
                h22 = 1;
                break;
            case 23:
                h23 = 1;
                break;
            default:
                break;
        }
    }

    private int dayOfWeek;
    private long h0;
    private long h1;
    private long h2;
    private long h3;
    private long h4;
    private long h5;
    private long h6;
    private long h7;
    private long h8;
    private long h9;
    private long h10;
    private long h11;
    private long h12;
    private long h13;
    private long h14;
    private long h15;
    private long h16;
    private long h17;
    private long h18;
    private long h19;
    private long h20;
    private long h21;
    private long h22;
    private long h23;
}
