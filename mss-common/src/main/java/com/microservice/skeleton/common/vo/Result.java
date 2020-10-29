package com.microservice.skeleton.common.vo;

import java.util.Map;


public class Result<T> {

    private static final String CODE = "code";
    private static final String MSG = "msg";

    private Integer code=200;
    private String msg="操作成功";
    private String description;
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

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Result() {
    }

    public static Result failure(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result ok(String msg) {
        Result result = new Result();
        return result;
    }

    public static Result ok(Map<String, Object> map) {
        Result result = new Result();
        return result;
    }

    public static Result ok() {
        return new Result();
    }


}
