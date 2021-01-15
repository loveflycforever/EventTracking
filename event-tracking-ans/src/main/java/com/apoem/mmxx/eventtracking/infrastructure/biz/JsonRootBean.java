package com.apoem.mmxx.eventtracking.infrastructure.biz;

import java.util.List;

/**
 * Please check `classpath:bizCity.json` for more details.
 */
public class JsonRootBean {

    private int result;
    private String msg;
    private List<Data> data;

    public void setResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

}