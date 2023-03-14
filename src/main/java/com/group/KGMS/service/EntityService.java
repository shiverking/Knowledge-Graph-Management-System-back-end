package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.entity.Entity;
import com.group.KGMS.entity.Triple;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EntityService {
    //判断实体是否存在
    Long ifEntityExists(String name);

    //插入新实体
    Long insertNewEntity(Entity entity);

    //分页获取所有实体
    PageInfo<Entity> getAllEntityByPage(Integer pageNum, Integer limitNum);

    //插入新关联
    int insertNewCorrelationForEachTriple(Long triple_id, Long head_id, Long tail_id, Long relation_id);

    //获取已有的关系
    Map<String, Object> selectSpecificCorrelation(Long triple_id);

    //模糊查询所有可能的实体
    List<String> fuzzyQueryOfEntities(String restrict);
}
