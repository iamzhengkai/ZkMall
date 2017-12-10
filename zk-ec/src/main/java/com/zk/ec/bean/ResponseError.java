package com.zk.ec.bean;

/**
 * Created by Administrator on 2017/12/10.
 */

public class ResponseError {
    private Long code;
    private String error;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
