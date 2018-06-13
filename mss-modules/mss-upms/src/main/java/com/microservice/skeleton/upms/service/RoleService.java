package com.microservice.skeleton.upms.service;

import com.microservice.skeleton.upms.entity.RcRole;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Mr.Yangxiufeng
 * Date: 2018-05-10
 * Time: 20:26
 */
public interface RoleService {
    List<RcRole> getRoleByUserId(Integer userId);
}
