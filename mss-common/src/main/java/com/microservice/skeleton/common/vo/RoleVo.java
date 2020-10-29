package com.microservice.skeleton.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class RoleVo implements Serializable {
    private static final long serialVersionUID = 2179037393108205286L;
    private Integer id;

    private String name;

    private String value;

    private String tips;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}
