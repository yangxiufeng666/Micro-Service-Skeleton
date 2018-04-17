package com.microservice.skeleton.auth.service;

import com.microservice.skeleton.auth.entity.RcMenuEntity;

import java.util.List;

/**
 * Created by Mr.Yangxiufeng on 2017/12/29.
 * Time:12:37
 * ProjectName:Mirco-Service-Skeleton
 */
public interface PermissionService {
    List<RcMenuEntity> getPermissionsByRoleId(Integer roleId);
}
