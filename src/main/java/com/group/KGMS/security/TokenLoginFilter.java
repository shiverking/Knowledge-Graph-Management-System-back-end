package com.group.KGMS.security;

import com.group.KGMS.entity.R;
import com.group.KGMS.entity.User;
import com.group.KGMS.entity.UserInfo;
import com.group.KGMS.utils.JwtUtil;
import com.group.KGMS.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private RedisTemplate redisTemplate;

    public TokenLoginFilter(AuthenticationManager authenticationManager,RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
        // 设置登录路径匹配/user/login
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/user/login","POST"));
    }

    /**
     * 身份验证
     * @param req
     * @param res
     * @return org.springframework.security.core.Authentication
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        String username = this.obtainUsername(req);
        username = username != null ? username : "";
        username = username.trim();
        String password = this.obtainPassword(req);
        password = password != null ? password : "";
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
    /**
     * 验证成功
     * @param req
     * @param res
     * @param chain
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        User user = (User) auth.getPrincipal();
        UserInfo userInfo = new UserInfo(user.getUsername(),user.getHeadurl());
        String token = JwtUtil.getToken(user.getUsername());
        // 将token信息存入redis缓存
        redisTemplate.opsForValue().set(user.getUsername(), user.getPermissions());
        Map<String, Object> map = new HashMap<>();
        map.put("token",token);
        map.put("userInfo",userInfo);
        //响应工具类
        ResponseUtil.write(res, R.ok().data(map));
//        ResponseUtil.write(res, R.ok().data("token", token));
    }

    /**
     * 登录失败
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,AuthenticationException e) throws IOException {
        ResponseUtil.write(response, R.error());
    }

}
