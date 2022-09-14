package com.group.KGMS.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.Signature;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static String SIGNATURE = "token!@#$%^7890";

    /**
     * 生成token
     * @param map //传入payload
     * @return 返回token
     */
    public static String getToken(String username){
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("username",username);
        Calendar instance = Calendar.getInstance();
        //设置token 60分钟后过期
        instance.add(Calendar.MINUTE,60);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(SIGNATURE)).toString();
    }

    /**
     * 验证token
     * @param token
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }
    /**
     * 获取token中payload
     * @param token
     * @return
     */
    public static DecodedJWT decodeToken(String token){
        return JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }
    //根据token字符串得到用户信息
    public static String getUserFromToken(String token) {
        return decodeToken(token).getClaim("username").asString();
    }
    public static void removeToken(String token) {
        //JWT token 无需删除，客户端扔掉即可。
    }

}