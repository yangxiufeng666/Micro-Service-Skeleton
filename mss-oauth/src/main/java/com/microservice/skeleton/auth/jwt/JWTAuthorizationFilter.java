package com.microservice.skeleton.auth.jwt;

import com.alibaba.fastjson.JSONObject;
import com.microservice.skeleton.auth.entity.AuthUser;
import com.microservice.skeleton.common.jwt.JWTConstants;
import com.microservice.skeleton.common.vo.Result;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**<p>授权</p>
 * @author Mr.Yangxiufeng
 * @date 2020-10-27
 * @time 19:23
 */
@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(JWTConstants.TOKEN_HEADER);
        if (StringUtils.isEmpty(token) || !token.startsWith(JWTConstants.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
        }
        try {
            Authentication authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            onSuccessfulAuthentication(request , response , authentication);
            chain.doFilter(request,response);
        } catch (Exception e) {
            e.printStackTrace();
            onUnsuccessfulAuthentication(request, response , new AccountExpiredException(e.getMessage()));
        }
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        log.info("Token 验证成功");
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.error("token校验失败");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Result result = Result.failure(HttpServletResponse.SC_UNAUTHORIZED , failed.getMessage());
        response.getWriter().write(JSONObject.toJSONString(result));
    }

    // 这里从token中获取用户信息并新建一个token
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) throws ParseException, JOSEException {
        String token = tokenHeader.replace(JWTConstants.TOKEN_PREFIX, "");
        SignedJWT jwt = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(JWTConstants.SECRET);
         //校验是否有效
        if (!jwt.verify(verifier)) {
            throw new AccountExpiredException("Token 无效");
        }
        //校验超时
        Date expirationTime = jwt.getJWTClaimsSet().getExpirationTime();
        if (new Date().after(expirationTime)) {
            throw new AccountExpiredException("Token 已过期");
        }
        //获取载体中的数据
        Object account = jwt.getJWTClaimsSet().getClaim("payload");
        if (account != null){
            AuthUser user = JSONObject.parseObject(account.toString(), AuthUser.class);
            return new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
        }
        return null;
    }
}
