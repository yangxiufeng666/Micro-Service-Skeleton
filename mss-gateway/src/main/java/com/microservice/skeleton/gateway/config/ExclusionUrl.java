package com.microservice.skeleton.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Mr.Yangxiufeng
 * @date 2020-10-29
 * @time 10:21
 */
@Component
@ConfigurationProperties(prefix = "exclusion")
public class ExclusionUrl {

    private List<String> url;

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }
}
