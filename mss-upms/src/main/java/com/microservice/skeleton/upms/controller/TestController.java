package com.microservice.skeleton.upms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr.Yangxiufeng
 * @date 2020-10-29
 * @time 16:32
 */
@RestController
@RequestMapping("order")
public class TestController {

    @GetMapping("list")
    public String orderList(){
        return "order list";
    }
    @GetMapping("detail")
    public String orderDetail(){
        return "order detail";
    }
}
