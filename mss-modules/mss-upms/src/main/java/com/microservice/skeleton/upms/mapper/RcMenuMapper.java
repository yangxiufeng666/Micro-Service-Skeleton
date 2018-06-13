package com.microservice.skeleton.upms.mapper;

import com.microservice.skeleton.upms.entity.RcMenu;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface RcMenuMapper extends Mapper<RcMenu> {
    @Select(value = "select menu.* from rc_menu menu,rc_privilege p where menu.id=p.menu_id and p.role_id=#{roleId}")
    List<RcMenu> getPermissionsByRoleId(Integer roleId);
}