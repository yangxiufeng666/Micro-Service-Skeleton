# Micro-Service-Skeleton
微服务开发基础框架

# 版本说明
目前使用的Nacos版本，如需Eureka作为注册中心的请采用tags v2.0(https://github.com/babylikebird/Micro-Service-Skeleton/tree/v2.0)


目前已经改版，进阶版：https://blog.csdn.net/w1054993544/article/details/109361170

## 一、需求
在2018年写的[基于OAUTH2.0统一认证授权的微服务基础架构](https://blog.csdn.net/w1054993544/article/details/78932614)只是基于OAUTH认证授权的入门级应用。本文基于实战目的，实现权限的动态控制。
现有如下需求：

 1. 基于用户-角色-权限控制
 2. 权限粒度控制到具体的请求URL
 3. 当用户的角色或者权限变动后，已获授权的用户需要重新登录授权

本文围绕上面三个基本需求进行实现。

## 二、工程说明
设计的框架已经中间件有：
 1. Nacos 1.3
 2. Spring Cloud Hoxton.SR8
 3. JWT  nimbus-jose-jwt
 4. Spring Cloud Gateway
 5. Spring security
 6. mybatis-plus
 7. Redis
 8. mysql

设计的主要工程有：

 1. gateway：网关，动态权限判断
 2. auth：认证中心
 3. upms：用户权限管理服务
 
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201029154424469.png#pic_center)
## 三、认证中心
### 3.1 WebSecurityConfig
登录认证授权等主要采用Spring security + JWT，那么得首先配置WebSecurityConfig，这里看到的redis配置主要是为了满足需求点3（当用户的角色或者权限变动后，已获授权的用户需要重新登录授权）

```java
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTAuthenticationFilter(authenticationManager() , redisTemplate),  UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthorizationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint());
    }


}
```
### 3.2 UserDetailsServiceImpl
通过用户名去查找用户及拥有的角色和权限
```java
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UpmsService upmsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Result<UserVo> userResult = upmsService.findByUsername(username);
        if (userResult.getCode() != StatusCode.SUCCESS_CODE) {
            throw new UsernameNotFoundException("用户:" + username + ",不存在!");
        }
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userResult.getData(),userVo);
        Result<List<RoleVo>> roleResult = upmsService.getRoleByUserId(userVo.getId());
        if (roleResult.getCode() == StatusCode.SUCCESS_CODE){
            List<RoleVo> roleVoList = roleResult.getData();
            for (RoleVo role:roleVoList){
                //角色必须是ROLE_开头，可以在数据库中设置
                SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+role.getValue());
                grantedAuthorities.add(grantedAuthority);
                //获取权限
                Result<List<MenuVo>> perResult  = upmsService.getRolePermission(role.getId());
                if (perResult.getCode() == StatusCode.SUCCESS_CODE){
                    List<MenuVo> permissionList = perResult.getData();
                    for (MenuVo menu:permissionList
                            ) {
                        if ( !StringUtils.isEmpty(menu.getUrl()) ){
                            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(menu.getUrl());
                            grantedAuthorities.add(authority);
                        }
                    }
                }
            }
        }
        AuthUser user = new AuthUser(userVo.getUsername(), userVo.getPassword(), grantedAuthorities);
        user.setId(userVo.getId());
        return user;
    }
}
```
### 3.3 JWTAuthenticationFilter
主要对用户进行认证工作，当登录时，获取用户名和密码，通过authenticationManager.authenticate，最终会调用UserDetailsServiceImpl来获取用户信息(在DaoAuthenticationProvider的retrieveUser中)，
然后在DaoAuthenticationProvider的additionalAuthenticationChecks中校验密码，这里不再对spring security的细节进行更多的赘述，读者可直接看它源码。

```java
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 过期时间2小时
     */
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 2;

    private AuthenticationManager authenticationManager;

    private StringRedisTemplate redisTemplate;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, StringRedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @SneakyThrows
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        AuthUser user = (AuthUser) authResult.getPrincipal();
        /**
         * 1、创建密钥
         */
        MACSigner macSigner = new MACSigner(JWTConstants.SECRET);
        /**
         * 2、payload
         */
        String payload = JSONObject.toJSONString(user);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("subject")
                .claim("payload", payload)
                .expirationTime(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .build();
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        /**
         * 创建签名的JWT
         */
        SignedJWT signedJWT = new SignedJWT(jwsHeader , claimsSet);
        signedJWT.sign(macSigner);
        /**
         * 生成token
         */
        String jwtToken = signedJWT.serialize();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        Result result = Result.ok().setData(jwtToken);
        //生成Key， 把权限放入到redis中
        String keyPrefix = "JWT" + user.getId() + ":";
        String keySuffix = Md5Utils.getMD5(jwtToken.getBytes());
        String key = keyPrefix + keySuffix;

        String authKey = key + ":Authorities";

        redisTemplate.opsForValue().set(key , jwtToken , EXPIRE_TIME , TimeUnit.MILLISECONDS);

        redisTemplate.opsForValue().set(authKey, JSONObject.toJSONString(user.getAuthorities()), EXPIRE_TIME , TimeUnit.SECONDS);

        response.getWriter().write(JSONObject.toJSONString(result));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.error("登录认证失败",failed);
        Result result = null;
        int status = 401;
        if (failed instanceof UsernameNotFoundException){
            result = Result.failure(404, "用户不存在");
        }else if (failed instanceof BadCredentialsException){
            result = Result.failure(401, "用户名密码错误");
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.getWriter().write(JSONObject.toJSONString(result));
    }
```

主要是生成JWT的token， 并且把权限信息放入redis。

## 四、 Gateway 网关
网关的主要作用是对JWT和具体的URL进行校验，校验不通过则返回错误信息。主要通过AuthFilter来实现，定义的AuthFilter如下：

```java
@Component
@Slf4j
public class AuthFilter implements GlobalFilter , Ordered {

    @Autowired
    private ExclusionUrl exclusionUrl;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String headerToken = request.getHeaders().getFirst(JWTConstants.TOKEN_HEADER);
        log.info("headerToken:{}", headerToken);
        //1、只要带上了token， 就需要判断Token是否有效
        if ( !StringUtils.isEmpty(headerToken) && !verifierToken(headerToken)){
            return getVoidMono(response, 401, "token无效");
        }
        String path = request.getURI().getPath();
        log.info("request path:{}", path);
        //2、判断是否是过滤的路径， 是的话就放行
        if ( isExclusionUrl(path) ){
            return chain.filter(exchange);
        }
        //3、判断请求的URL是否有权限
        boolean permission = hasPermission(headerToken , path);
        if (!permission){
            return getVoidMono(response, 403, "无访问权限");
        }
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> getVoidMono(ServerHttpResponse response, int i, String msg) {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);
        Result failed = Result.failure(i, msg);
        byte[] bits = JSON.toJSONString(failed).getBytes();
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        return response.writeWith(Mono.just(buffer));
    }

    private boolean isExclusionUrl(String path){
        List<String> exclusions = exclusionUrl.getUrl();
        if (exclusions.size() == 0){
            return false;
        }
        return exclusions.stream().anyMatch( action -> antPathMatcher.match(action , path));

    }

    private boolean verifierToken(String headerToken){
        try {
            SignedJWT jwt = getSignedJWT(headerToken);
            JWSVerifier verifier = new MACVerifier(JWTConstants.SECRET);
            //校验是否有效
            if (!jwt.verify(verifier)) {
                log.error("token不合法，检测不过关");
                return false;
            }
            //校验超时
            Date expirationTime = jwt.getJWTClaimsSet().getExpirationTime();
            if (new Date().after(expirationTime)) {
                log.error("token已经过期");
                return false;
            }
            //获取载体中的数据
            return true;
        } catch (ParseException | JOSEException e) {
            log.error("token校验出错",e);
        }
        return false;
    }
    private boolean hasPermission(String headerToken, String path){
        try {
            if (StringUtils.isEmpty(headerToken)){
                return false;
            }
            SignedJWT jwt = getSignedJWT(headerToken);
            Object payload = jwt.getJWTClaimsSet().getClaim("payload");
            UserVo user = JSON.parseObject(payload.toString(), UserVo.class);
            //生成Key， 把权限放入到redis中
            String keyPrefix = "JWT" + user.getId() + ":";
            String token = headerToken.replace(JWTConstants.TOKEN_PREFIX, "");
            String keySuffix = Md5Utils.getMD5(token.getBytes());
            String key = keyPrefix + keySuffix;
            String authKey = key + ":Authorities";

            String authStr = redisTemplate.opsForValue().get(authKey);
            if (StringUtils.isEmpty(authStr)){
                return false;
            }
            List<Authority> authorities = JSON.parseArray(authStr , Authority.class);
            return authorities.stream().anyMatch(authority -> antPathMatcher.match(authority.getAuthority(), path));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    private SignedJWT getSignedJWT(String headerToken) throws ParseException {
        String token = headerToken.replace(JWTConstants.TOKEN_PREFIX, "");
        log.info("token is {}", token);
        return SignedJWT.parse(token);
    }
}
```

## 五、运行效果
我们现在数据库中添加几个菜单（**这里为了简单直接在数据库添加**）
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201030090956767.png#pic_center)

添加角色：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201029162631289.png#pic_center)
给角色赋予权限
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201029162651252.png#pic_center)

给用户赋予角色：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201029162719119.png#pic_center)
现在用admin的用户登录等到token：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201029162850178.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3cxMDU0OTkzNTQ0,size_16,color_FFFFFF,t_70#pic_center)
用Token去访问order/list，看到这里返回order list， 权限认证成功。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201030091042380.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3cxMDU0OTkzNTQ0,size_16,color_FFFFFF,t_70#pic_center)


如访问user/findByUsername这会提示无权限，这样就实现到具体URL的鉴权了。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201030091130822.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3cxMDU0OTkzNTQ0,size_16,color_FFFFFF,t_70#pic_center)

## 六、权限变动重新授权
我们在JWTAuthenticationFilter中，把权限信息等写入到了redis中，key的规则如下

```java
        //生成Key， 把权限放入到redis中
        String keyPrefix = "JWT" + user.getId() + ":";
        String keySuffix = Md5Utils.getMD5(jwtToken.getBytes());
        String key = keyPrefix + keySuffix;

        String authKey = key + ":Authorities";
```
redis中看到如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201029164153972.png#pic_center)
只要后台权限变动的时候，根据key的规则清除redis数据即可， 然后在gateway中获取不到相应的权限， 那么会要求用户重新登录。

到这里我们已经实现了第一节提的3个需求， 更多的玩法，由各位自己发挥了。

[代码在这里哟](https://github.com/babylikebird/Micro-Service-Skeleton)
[重要事情再说一遍](https://github.com/babylikebird/Micro-Service-Skeleton)
