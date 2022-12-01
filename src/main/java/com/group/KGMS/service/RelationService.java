package com.group.KGMS.service;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.Relation;
import com.group.KGMS.entity.Triple;
import java.util.List;

public interface RelationService {
    //判断是否存在实体
    Long ifRelationExists(String name);
    //插入新实体
    Long insertNewRelation(Relation relation);
    //分页获取所有实体
    PageInfo<Relation> getAllRelationByPage(Integer pageNum, Integer limitNum);
}
