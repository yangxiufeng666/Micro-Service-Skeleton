package com.microservice.skeleton.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Mr.Yangxiufeng
 * Date: 2018-05-16
 * Time: 11:04
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    private static final String CODE = "code";
    private static final String MSG = "msg";
    private static final long serialVersionUID = 2633283546876721434L;

    private Integer code=200;
    private String msg="操作成功";
    private String description;
    private T data;

    private HashMap<String,Object> exend;

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

    @JsonIgnore
    public HashMap<String, Object> getExend() {
        return exend;
    }

    public void setExend(HashMap<String, Object> exend) {
        this.exend = exend;
    }

    public Result() {
        exend = new HashMap<>();
    }

    public static Result failure(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result ok(String msg) {
        Result result = new Result();
        result.put("msg", msg);
        return result;
    }

    public static Result ok(Map<String, Object> map) {
        Result result = new Result();
        result.exend.putAll(map);
        return result;
    }

    public static Result ok() {
        return new Result();
    }

    public Result put(String key, Object value) {
        exend.put(key, value);
        return this;
    }
}
