package com.microservice.skeleton.auth.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mr.Yangxiufeng on 2017/12/27.
 * Time:10:34
 * ProjectName:Mirco-Service-Skeleton
 */
@Entity
@Table(name = "rc_privilege")
public class RcPrivilegeEntity implements Serializable{
    private static final long serialVersionUID = 7945786697073389306L;
    private Integer roleId;
    private String menuId;
    private Date createTime;

    @Id
    @Column(name = "role_id")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "menu_id")
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RcPrivilegeEntity that = (RcPrivilegeEntity) o;

        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;
        if (menuId != null ? !menuId.equals(that.menuId) : that.menuId != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId != null ? roleId.hashCode() : 0;
        result = 31 * result + (menuId != null ? menuId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
