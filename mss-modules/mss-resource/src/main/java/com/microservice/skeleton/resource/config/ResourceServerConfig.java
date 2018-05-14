package com.microservice.skeleton.resource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created by Mr.Yangxiufeng on 2017/12/29.
 * Time:9:41
 * ProjectName:Mirco-Service-Skeleton
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/v2/api-docs").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }
}
