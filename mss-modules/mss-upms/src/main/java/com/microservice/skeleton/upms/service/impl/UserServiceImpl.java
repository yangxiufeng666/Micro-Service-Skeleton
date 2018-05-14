package com.microservice.skeleton.upms.service.impl;

import com.microservice.skeleton.upms.entity.RcUser;
import com.microservice.skeleton.upms.mapper.RcUserMapper;
import com.microservice.skeleton.upms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Mr.Yangxiufeng
 * Date: 2018-05-10
 * Time: 19:57
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RcUserMapper userMapper;

    @Override
    public RcUser findByUsername(String username) {
        Example example = Example.builder(RcUser.class)
                .where(Sqls.custom().andEqualTo("username",username))
                .build();
        return userMapper.selectOneByExample(example);
    }
}
