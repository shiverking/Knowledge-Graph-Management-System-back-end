package com.group.KGMS.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author ：闫崇傲
 * @description：用户类定义
 * @date
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements UserDetails {
    //用户id
    private int id;
    //用户名
    @NotEmpty(message = "用户名不能为空")
    private String username;
    //密码
    private String password;
    //头像存储地址
    private String headurl;
    //角色列表
    private List<Role> roles;
    //账号是否可用
    private Boolean enabled;
    //账号是是否锁定
    private Boolean locked;
    private List<String> permissions;
    //账户是否过期
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }
    //账户是否被锁定
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !locked;
    }
    //证书是否过期
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //账户是否能使用
    @Override
    @JsonIgnore
    public boolean isEnabled() {return true;//return enabled;
    }
    //返回用户的角色列表
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}
