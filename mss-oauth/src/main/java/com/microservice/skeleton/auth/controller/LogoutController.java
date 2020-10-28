package com.microservice.skeleton.auth.controller;

import com.microservice.skeleton.common.vo.Result;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LogoutController {


    @DeleteMapping(value = "/exit")
    public @ResponseBody
    Result revokeToken(String access_token){

        return Result.ok();
    }
}
