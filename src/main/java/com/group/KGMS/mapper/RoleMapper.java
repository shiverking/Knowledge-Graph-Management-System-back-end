package com.group.KGMS.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RoleMapper {
    //添加一条用户和role的关联关系
    int addUserAndRole(@Param("uid") int uid, @Param("rid") int rid);
}