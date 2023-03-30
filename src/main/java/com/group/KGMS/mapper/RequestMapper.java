package com.group.KGMS.mapper;

import com.group.KGMS.entity.RequestInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface RequestMapper {
    //查询最近更新时间
    Date getLatestTime();
    //查询最近五次更新记录
    List<RequestInfo> getLastest5Records();
    //插入一条新的record记录
    int insertNewRecord(RequestInfo info);
}
