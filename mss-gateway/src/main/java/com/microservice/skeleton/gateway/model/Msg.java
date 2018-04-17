package com.microservice.skeleton.gateway.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by Mr.Yangxiufeng on 2017/12/26.
 * Time:13:41
 * ProjectName:Mirco-Service-Skeleton
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Msg<T> implements Serializable{
    private static final long serialVersionUID = -1177183613782210351L;
    private Integer code;
    private String msg;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
