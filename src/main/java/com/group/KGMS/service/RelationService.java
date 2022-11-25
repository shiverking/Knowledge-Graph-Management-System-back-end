package com.group.KGMS.service;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.Relation;
import com.group.KGMS.entity.Triple;
import java.util.List;

public interface RelationService {
    int insertNewRelation(List<Triple> triples);
    //分页获取所有实体
    PageInfo<Relation> getAllRelationByPage(Integer pageNum, Integer limitNum);
}
