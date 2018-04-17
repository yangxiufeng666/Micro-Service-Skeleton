package com.microservice.skeleton.auth.model;

import java.io.Serializable;

/**
 * Created by Mr.Yangxiufeng on 2018/1/2.
 * Time:9:28
 * ProjectName:Mirco-Service-Skeleton
 */
public class Msg implements Serializable{
    private static final long serialVersionUID = 7514826298158585250L;
    public static final int SUCCESS=200;
    public static final int FAILED=201;
    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
