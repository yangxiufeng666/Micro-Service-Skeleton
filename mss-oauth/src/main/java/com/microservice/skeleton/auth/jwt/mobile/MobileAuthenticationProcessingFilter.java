package com.microservice.skeleton.auth.jwt.mobile;

import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Mr.Yangxiufeng
 * @date 2021-05-19
 * @time 10:13
 */
public class MobileAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationManager authenticationManager;

    private final String mobileParameter = "mobile";
    private final String smsCodeParameter = "smsCode";

    private boolean postOnly = true;

    public MobileAuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/mobileLogin", "POST"));
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String mobile = obtainMobile(request);
        String smsCode = obtainSmsCode(request);

        if (mobile == null) {
            mobile = "";
        }

        if (smsCode == null) {
            smsCode = "";
        }

        mobile = mobile.trim();

        MobileAuthenticationToken authRequest = new MobileAuthenticationToken(mobile, smsCode);

        // Allow subclasses to set the "details" property
//        setDetails(request, authRequest);

        return authenticationManager.authenticate(authRequest);
    }

    @Nullable
    protected String obtainSmsCode(HttpServletRequest request) {
        return request.getParameter(smsCodeParameter);
    }

    @Nullable
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

}
