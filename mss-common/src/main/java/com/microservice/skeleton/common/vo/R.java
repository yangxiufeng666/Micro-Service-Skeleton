package com.microservice.skeleton.common.vo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Mr.Yangxiufeng
 * Date: 2018-05-16
 * Time: 11:04
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R {
    private Integer code;
    private String msg;
    private String description;
    private Object data;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public R() {
    }

    private R(Builder builder) {
        this.code = builder.code;
        this.msg = builder.msg;
        this.description = builder.description;
        this.data = builder.data;
    }

    @Data
    public static class  Builder{
        private Integer code;
        private String msg;
        private String description;
        private Object data;

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }
        public R build(){
            return new R(this);
        }
    }

}
