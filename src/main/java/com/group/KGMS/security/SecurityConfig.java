package com.group.KGMS.security;

import com.group.KGMS.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.group.KGMS.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    private LogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                // 未授权处理
                .authenticationEntryPoint(new UnauthorizedEntryPoint())
                .and().csrf().disable()
                // 需要授权的请求
                .authorizeRequests()
                .antMatchers("/user/login","/user/register").anonymous()
                .anyRequest().authenticated()
                // 退出登录的请求路径和对应的处理器
                .and().logout().logoutUrl("/user/logout")
                .addLogoutHandler(new LogoutSuccessHandler(redisTemplate)).and()
                // 登录过滤器
                .addFilter(new TokenLoginFilter(authenticationManager(), redisTemplate))
                // 接口认证过滤器
                .addFilter(new TokenAuthenticationFilter(authenticationManager(), redisTemplate)).httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 配置用户及其对应的角色
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService);
//    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService(){
//        return (UserDetailsService) new MyUserDetails();
//    }

}
