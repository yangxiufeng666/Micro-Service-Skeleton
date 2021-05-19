package com.microservice.skeleton.auth.jwt.handler;

import com.alibaba.fastjson.JSONObject;
import com.microservice.skeleton.auth.entity.AuthUser;
import com.microservice.skeleton.common.jwt.JWTConstants;
import com.microservice.skeleton.common.util.Md5Utils;
import com.microservice.skeleton.common.vo.Result;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Yangxiufeng
 * @date 2021-05-19
 * @time 14:39
 */
public class JWTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 过期时间2小时
     */
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 2;

    private StringRedisTemplate redisTemplate;

    public JWTAuthenticationSuccessHandler(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthUser user = (AuthUser) authentication.getPrincipal();
        /**
         * 1、创建密钥
         */
        MACSigner macSigner = new MACSigner(JWTConstants.SECRET);
        /**
         * 2、payload
         */
        String payload = JSONObject.toJSONString(user);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("subject")
                .claim("payload", payload)
                .expirationTime(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .build();
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        /**
         * 3、创建签名的JWT
         */
        SignedJWT signedJWT = new SignedJWT(jwsHeader , claimsSet);
        signedJWT.sign(macSigner);
        /**
         * 4、生成token
         */
        String jwtToken = signedJWT.serialize();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        Result result = Result.ok().setData(jwtToken);
        //END

        //生成Key， 把权限放入到redis中
        String keyPrefix = "JWT" + user.getId() + ":";
        String keySuffix = Md5Utils.getMD5(jwtToken.getBytes());
        String key = keyPrefix + keySuffix;

        String authKey = key + ":Authorities";

        redisTemplate.opsForValue().set(key , jwtToken , EXPIRE_TIME , TimeUnit.MILLISECONDS);

        redisTemplate.opsForValue().set(authKey, JSONObject.toJSONString(user.getAuthorities()), EXPIRE_TIME , TimeUnit.SECONDS);

        response.getWriter().write(JSONObject.toJSONString(result));
    }
}
