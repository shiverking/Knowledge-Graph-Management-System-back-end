package com.group.KGMS.mapper;

import com.group.KGMS.entity.Relation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface RelationMapper {
    int insertNewRelation(@Param("name")String name);
    List<Relation> getAllRelation();
}
