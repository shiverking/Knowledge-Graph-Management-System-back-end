package com.group.KGMS.security;

import com.group.KGMS.utils.JwtUtil;
import org.omg.CORBA.UserException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(AuthenticationManager authManager, RedisTemplate redisTemplate) {
        super(authManager);
        this.redisTemplate = redisTemplate;
    }

    /**
     * 用户请求时，会携带token信息，经过该拦截器时会调用doFilterInternal()这个方法，将从redis获取权限列表，并封装已认证的UsernamePasswordAuthenticationToken，并将这个类放到SecurityContextHolder.getContext()
     *
     * @param req
     * @param res
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // token置于header里
        String token = request.getHeader("token");
        if (token != null) {
            // 解析token获得用户名
            String username = JwtUtil.getUserFromToken(token);
            // 根据用户名获得对应的权限列表
            Set<String> permissionValueList = (Set<String>) redisTemplate.opsForValue().get(username);
            // 将权限列表转成 Collection<GrantedAuthority>数据类型，最后封装到UsernamePasswordAuthenticationToken用户权限信息中
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            for (String permissionValue : permissionValueList) {
                if (StringUtils.isEmpty(permissionValue)) continue;
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
                authorities.add(authority);
            }
            if (username!=null) {
                return new UsernamePasswordAuthenticationToken(username, token, authorities);
            }
            return null;
        }
        return null;
    }
}
