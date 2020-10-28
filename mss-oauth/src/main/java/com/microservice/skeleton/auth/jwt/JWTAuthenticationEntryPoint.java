package com.microservice.skeleton.auth.jwt;

import com.alibaba.fastjson.JSONObject;
import com.microservice.skeleton.common.vo.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Mr.Yangxiufeng
 * @date 2020-10-28
 * @time 15:46
 */
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        String reason = authException.getMessage();
        response.getWriter().write(JSONObject.toJSONString(Result.failure(HttpServletResponse.SC_FORBIDDEN, reason)));
    }
}
