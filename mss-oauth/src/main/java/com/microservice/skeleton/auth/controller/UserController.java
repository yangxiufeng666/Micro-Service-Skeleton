package com.microservice.skeleton.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {
    @RequestMapping("/checkUser")
    public Principal user(Principal user) {
        return user;
    }
}
