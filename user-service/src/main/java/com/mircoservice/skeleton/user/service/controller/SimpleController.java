package com.mircoservice.skeleton.user.service.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
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
    @HystrixCommand(fallbackMethod = "helloFallback",commandKey = "getWord",groupKey = "SimpleController",threadPoolKey = "SimplePool")
    @CacheResult(cacheKeyMethod = "getKey")
    public String getWord(){
        return restTemplate.getForEntity("http://order-service/hello",String.class).getBody();
    }
    private String helloFallback(){
        return "error";
    }
    private String getKey(){
        return "word";
    }
}
