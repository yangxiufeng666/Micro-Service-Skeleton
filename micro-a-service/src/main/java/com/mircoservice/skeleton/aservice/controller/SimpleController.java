package com.mircoservice.skeleton.aservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Mr.Yangxiufeng on 2017/12/9.
 * Time:15:58
 * ProjectName:Mirco-Service-Skeleton
 */
@RestController
public class SimpleController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "getWord")
    public String getWord(){
        return restTemplate.getForEntity("http://hello-service/hello",String.class).getBody();
    }

}
