package com.microservice.skeleton.auth.service.impl;

import com.microservice.skeleton.auth.entity.AuthUser;
import com.microservice.skeleton.auth.service.UpmsService;
import com.microservice.skeleton.common.util.StatusCode;
import com.microservice.skeleton.common.vo.MenuVo;
import com.microservice.skeleton.common.vo.Result;
import com.microservice.skeleton.common.vo.RoleVo;
import com.microservice.skeleton.common.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UpmsService upmsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Result<UserVo> userResult = upmsService.findByUsername(username);
        if (userResult.getCode() != StatusCode.SUCCESS_CODE) {
            throw new UsernameNotFoundException("用户:" + username + ",不存在!");
        }
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userResult.getData(),userVo);
        Result<List<RoleVo>> roleResult = upmsService.getRoleByUserId(userVo.getId());
        if (roleResult.getCode() == StatusCode.SUCCESS_CODE){
            List<RoleVo> roleVoList = roleResult.getData();
            for (RoleVo role:roleVoList){
                //角色必须是ROLE_开头，可以在数据库中设置
                SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+role.getValue());
                grantedAuthorities.add(grantedAuthority);
                //获取权限
                Result<List<MenuVo>> perResult  = upmsService.getRolePermission(role.getId());
                if (perResult.getCode() == StatusCode.SUCCESS_CODE){
                    List<MenuVo> permissionList = perResult.getData();
                    for (MenuVo menu:permissionList
                            ) {
                        if ( !StringUtils.isEmpty(menu.getUrl()) ){
                            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(menu.getUrl());
                            grantedAuthorities.add(authority);
                        }
                    }
                }
            }
        }
        AuthUser user = new AuthUser(userVo.getUsername(), userVo.getPassword(), grantedAuthorities);
        user.setId(userVo.getId());
        return user;
    }
}
