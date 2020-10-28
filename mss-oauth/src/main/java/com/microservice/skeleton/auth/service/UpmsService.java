package com.microservice.skeleton.auth.service;

import com.microservice.skeleton.auth.service.impl.UpmsServiceFallbackFactory;
import com.microservice.skeleton.common.vo.MenuVo;
import com.microservice.skeleton.common.vo.Result;
import com.microservice.skeleton.common.vo.RoleVo;
import com.microservice.skeleton.common.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Mr.Yangxiufeng
 * @date 2020-10-28
 * @time 16:49
 */
@FeignClient(name = "mss-upms",fallbackFactory = UpmsServiceFallbackFactory.class)
public interface UpmsService {
    @GetMapping("user/findByUsername/{username}")
    Result<UserVo> findByUsername(@PathVariable("username") String username);

    @GetMapping("role/getRoleByUserId/{userId}")
    Result<List<RoleVo>> getRoleByUserId(@PathVariable("userId") Integer userId);

    @GetMapping("permission/getRolePermission/{roleId}")
    Result<List<MenuVo>> getRolePermission(@PathVariable("roleId") Integer roleId);
}
