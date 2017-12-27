package com.microservice.skeleton.auth.service.impl;

import com.microservice.skeleton.auth.entity.RcUserEntity;
import com.microservice.skeleton.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mr.Yangxiufeng on 2017/12/27.
 * Time:16:37
 * ProjectName:Mirco-Service-Skeleton
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RcUserEntity userEntity = userService.findByUsername(username);
        if (userEntity == null){
            throw new UsernameNotFoundException("用户:" + username + ",不存在!");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        User user = new User(userEntity.getUsername(),userEntity.getPassword(),grantedAuthorities);
        return user;
    }
}
