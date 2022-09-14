package com.group.KGMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
public class UserInfo {
    //用户名
    private String username;
    //头像存储地址
    private String headurl;
}
