package com.microservice.skeleton.auth.jwt.handler;

import com.alibaba.fastjson.JSONObject;
import com.microservice.skeleton.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Mr.Yangxiufeng
 * @date 2021-05-19
 * @time 14:40
 */
@Slf4j
public class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("登录认证失败",exception);
        Result result = null;
        int status = 401;
        if (exception instanceof UsernameNotFoundException){
            result = Result.failure(404, "用户不存在");
        }else if (exception instanceof BadCredentialsException){
            result = Result.failure(401, "用户名密码错误");
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}
