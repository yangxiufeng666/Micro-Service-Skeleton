package com.microservice.skeleton.gateway.filter;

import com.netflix.zuul.ZuulFilter;

/**
 * Created by Mr.Yangxiufeng on 2017/12/25.
 * Time:15:39
 * ProjectName:Mirco-Service-Skeleton
 */
public class AccessFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() {
        return null;
    }
}
