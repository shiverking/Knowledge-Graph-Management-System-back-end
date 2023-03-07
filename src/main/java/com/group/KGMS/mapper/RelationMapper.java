package com.group.KGMS.mapper;

import com.group.KGMS.entity.Relation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RelationMapper {
    int insertNewRelation(Relation relation);

    List<Relation> getAllRelation();

    Long ifRelationExists(@Param("name") String name);

    //模糊查找关系集合,该关系集合中的每一项均含有限制'restrict'
    List<String> selectRelationIntangibly(@Param("restrict") String restrict);
}
