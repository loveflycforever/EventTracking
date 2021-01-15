package com.apoem.mmxx.eventtracking.infrastructure.biz;


public class Range {

    private RangeItem sellPrice;
    private RangeItem rentPrice;
    private RangeItem newPrice;
    private RangeItem sellArea;
    private RangeItem rentArea;
    private RangeItem sellLayout;
    private RangeItem rentLayout;

    public RangeItem getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(RangeItem sellPrice) {
        this.sellPrice = sellPrice;
    }

    public RangeItem getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(RangeItem rentPrice) {
        this.rentPrice = rentPrice;
    }

    public RangeItem getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(RangeItem newPrice) {
        this.newPrice = newPrice;
    }

    public RangeItem getSellArea() {
        return sellArea;
    }

    public void setSellArea(RangeItem sellArea) {
        this.sellArea = sellArea;
    }

    public RangeItem getRentArea() {
        return rentArea;
    }

    public void setRentArea(RangeItem rentArea) {
        this.rentArea = rentArea;
    }

    public RangeItem getSellLayout() {
        return sellLayout;
    }

    public void setSellLayout(RangeItem sellLayout) {
        this.sellLayout = sellLayout;
    }

    public RangeItem getRentLayout() {
        return rentLayout;
    }

    public void setRentLayout(RangeItem rentLayout) {
        this.rentLayout = rentLayout;
    }
}