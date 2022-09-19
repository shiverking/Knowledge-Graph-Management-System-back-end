package com.group.KGMS.security;

import com.group.KGMS.entity.R;
import com.group.KGMS.utils.JwtUtil;
import com.group.KGMS.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

//登出处理器
public class LogoutSuccessHandler implements LogoutHandler {
    private RedisTemplate redisTemplate;

    public LogoutSuccessHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("Authorization");
        System.out.println(token);
        if (token != null)
        //清空当前用户缓存中的权限数据
        {
            JwtUtil.removeToken(token);
            String userName = JwtUtil.getUserFromToken(token);
            redisTemplate.delete(userName);
        }
        ResponseUtil.write(response, R.ok());
    }
}
