package com.apoem.mmxx.eventtracking.infrastructure.biz;

import java.util.List;


public class NewPrice {

    private List<List<Integer>> list;
    private List<String> cnList;
    public void setList(List<List<Integer>> list) {
         this.list = list;
     }
     public List<List<Integer>> getList() {
         return list;
     }

    public void setCnList(List<String> cnList) {
         this.cnList = cnList;
     }
     public List<String> getCnList() {
         return cnList;
     }

}