package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.entity.Entity;
import com.group.KGMS.entity.Triple;

import java.util.List;

public interface EntityService {
    int insertNewEntity(List<CandidateTriple> candidateTripleList);
    //分页获取所有实体
    PageInfo<Entity> getAllEntityByPage(Integer pageNum, Integer limitNum);
}
