package com.microservice.skeleton.auth.controller;

import com.microservice.skeleton.common.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class UserController {
    @RequestMapping("/getUser")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping("checkToken")
    public Result checkToken(HttpServletRequest request){
        return Result.ok("token有效");
    }
}
