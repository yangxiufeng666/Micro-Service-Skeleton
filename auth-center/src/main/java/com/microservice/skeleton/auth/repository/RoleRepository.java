package com.microservice.skeleton.auth.repository;

import com.microservice.skeleton.auth.entity.RcRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mr.Yangxiufeng on 2017/12/27.
 * Time:16:09
 * ProjectName:Mirco-Service-Skeleton
 */
@Repository
public interface RoleRepository extends JpaRepository<RcRoleEntity,Integer>{
}
