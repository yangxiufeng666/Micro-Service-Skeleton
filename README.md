# Micro-Service-Skeleton
微服务开发基础框架

# 版本说明
Master 版本注册中心和配置中心后续都会采用Nacos ,目前使用的Nacos版本为0.8。如需Eureka作为注册中心的请采用tags v2.0(https://github.com/babylikebird/Micro-Service-Skeleton/tree/v2.0)

## 工程说明

 1. 注册中心: Nacos
 2. mss-gateway: 网关
 3. mss-oauth：认证中心
 4. mss-upms：用户权限
 5. mss-monitor：监控

## 2.展示
### 2.1获取access_token
1.
![这里写图片描述](http://img.blog.csdn.net/20180104152228530?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdzEwNTQ5OTM1NDQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
2.
![这里写图片描述](http://img.blog.csdn.net/20180104152257619?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdzEwNTQ5OTM1NDQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
3.
![这里写图片描述](http://img.blog.csdn.net/20180104152324593?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdzEwNTQ5OTM1NDQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
### 2.2刷新token
![这里写图片描述](http://img.blog.csdn.net/20180104152502267?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdzEwNTQ5OTM1NDQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

### 2.3用access_token获取资源
![这里写图片描述](http://img.blog.csdn.net/20180104152710161?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdzEwNTQ5OTM1NDQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

![这里写图片描述](http://img.blog.csdn.net/20180104152732088?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdzEwNTQ5OTM1NDQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## 3.结束
到这里Spring Cloud OAUTH2.0统一认证的骨架就完成了，具体项目拿过来修改就可以满足项目的需求了。
