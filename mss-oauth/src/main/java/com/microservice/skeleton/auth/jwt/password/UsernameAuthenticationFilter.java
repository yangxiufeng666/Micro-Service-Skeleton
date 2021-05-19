package com.microservice.skeleton.auth.jwt.password;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>认证</p>
 * @author Mr.Yangxiufeng
 * @date 2020-10-27
 * @time 19:21
 */
@Slf4j
public class UsernameAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 过期时间2小时
     */
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 2;

    private AuthenticationManager authenticationManager;

    public UsernameAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //TODO 验证码
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
