package com.group.KGMS.mapper;

import com.group.KGMS.entity.Entity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntityMapper {
    int insertNewEntity(Entity entity);
    List<Entity>  selectAllEntity();
    Long ifEntityExists(@Param("name")String name);
    //操作triples_ids表
    int insertNewCorrelationForEachTriple(@Param("triple_id")Long triple_id,@Param("head_id")Long head_id,@Param("tail_id")Long tail_id,@Param("relation_id")Long relation_id);
    Map<String,Object> selectSpecificCorrelation(@Param("triple_id")Long triple_id);
}
