package com.microservice.skeleton.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mr.Yangxiufeng on 2017/12/29.
 * Time:9:23
 * ProjectName:Mirco-Service-Skeleton
 */
@RestController
public class UserController {

    @GetMapping(value = "getUser")
    public String getUser(){
        return "order";
    }

}
