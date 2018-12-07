package com.microservice.skeleton.upms.service;

import com.microservice.skeleton.upms.entity.RcMenu;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Mr.Yangxiufeng
 * Date: 2018-06-13
 * Time: 10:12
 */
public interface PermissionService {
    List<RcMenu> getPermissionsByRoleId(Integer roleId);
}
