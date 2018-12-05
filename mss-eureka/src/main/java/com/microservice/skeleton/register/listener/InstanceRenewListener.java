package com.microservice.skeleton.register.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Mr.Yangxiufeng on 2017/12/9.
 * Time:13:48
 * ProjectName:Mirco-Service-Skeleton
 */
@Configuration
@Slf4j
public class InstanceRenewListener implements ApplicationListener<EurekaInstanceRenewedEvent> {
    @Override
    public void onApplicationEvent(EurekaInstanceRenewedEvent event) {
        log.info("心跳检测服务：{}" ,event.getInstanceInfo().getAppName());
    }
}
