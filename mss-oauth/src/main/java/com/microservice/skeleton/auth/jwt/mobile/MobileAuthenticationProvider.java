package com.microservice.skeleton.auth.jwt.mobile;

import com.microservice.skeleton.auth.entity.AuthUser;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

/**
 * @author Mr.Yangxiufeng
 * @date 2021-05-19
 * @time 10:19
 */
public class MobileAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String mobile = authentication.getName();
        if (StringUtils.isEmpty(mobile)){
            throw new UsernameNotFoundException("手机号不能为空");
        }
        //TODO 校验手机号、

        //TODO 校验验证码

        //TODO 查询用户
        AuthUser user = new AuthUser(mobile,mobile,null);
        return new MobileAuthenticationToken(user,"dedede");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (MobileAuthenticationToken.class
                .isAssignableFrom(authentication));
    }
}
