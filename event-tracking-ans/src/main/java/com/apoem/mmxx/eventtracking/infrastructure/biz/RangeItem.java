package com.apoem.mmxx.eventtracking.infrastructure.biz;

import java.util.List;
import java.util.Date;


public class RangeItem {

    private String type;
    private List<List<Integer>> list;
    private List<String> cnList;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public List<List<Integer>> getList() {
        return list;
    }

    public void setList(List<List<Integer>> list) {
        this.list = list;
    }

    public void setCnList(List<String> cnList) {
        this.cnList = cnList;
    }

    public List<String> getCnList() {
        return cnList;
    }

}