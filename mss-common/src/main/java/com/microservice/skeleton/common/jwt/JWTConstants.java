package com.microservice.skeleton.common.jwt;

/**
 * @author Mr.Yangxiufeng
 * @date 2020-10-29
 * @time 9:11
 */
public class JWTConstants {
    public static final byte[] SECRET = "52d907a4b404af790cf2cf488acc4836".getBytes();
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}
