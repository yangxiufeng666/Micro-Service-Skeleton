package com.mircoservice.skeleton.aservice.filter;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by Mr.Yangxiufeng on 2017/12/25.
 * Time:14:20
 * ProjectName:Mirco-Service-Skeleton
 */
@Component
@WebFilter(filterName = "HystrixRequestContextServletFilter",urlPatterns = "/*",asyncSupported = true)
public class HystrixRequestContextServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
