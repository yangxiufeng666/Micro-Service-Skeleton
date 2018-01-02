package com.microservice.skeleton.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Mr.Yangxiufeng on 2017/12/27.
 * Time:17:02
 * ProjectName:Mirco-Service-Skeleton
 */
@Controller
public class HelloController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "order";
    }
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public void doLogin(String username,String password){
        System.out.println(username);
    }
}
