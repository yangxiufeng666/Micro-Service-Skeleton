package com.microservice.skeleton.auth.service.impl;

import com.microservice.skeleton.auth.service.UpmsService;
import com.microservice.skeleton.common.vo.MenuVo;
import com.microservice.skeleton.common.vo.Result;
import com.microservice.skeleton.common.vo.RoleVo;
import com.microservice.skeleton.common.vo.UserVo;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Mr.Yangxiufeng
 * @date 2020-10-28
 * @time 16:53
 */
@Slf4j
@Component
public class UpmsServiceFallbackFactory implements FallbackFactory<UpmsService> {
    @Override
    public UpmsService create(Throwable throwable) {
        log.error("调用UPMS失败", throwable);
        return new UpmsService() {
            @Override
            public Result<UserVo> findByUsername(String username) {
                return Result.failure(201,"调用失败");
            }

            @Override
            public Result<List<RoleVo>> getRoleByUserId(Integer userId) {
                return Result.failure(201,"调用失败");
            }

            @Override
            public Result<List<MenuVo>> getRolePermission(Integer roleId) {
                return Result.failure(201,"调用失败");
            }
        };
    }
}
