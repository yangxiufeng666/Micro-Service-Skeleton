package com.microservice.skeleton.upms.mapper;

import com.microservice.skeleton.upms.entity.RcRole;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface RcRoleMapper extends Mapper<RcRole> {
    @Select(value = "select role.* from rc_role role,rc_user_role ur where role.id=ur.role_id and ur.user_id=#{userId}")
    List<RcRole> getRoleByUserId(Integer userId);
}