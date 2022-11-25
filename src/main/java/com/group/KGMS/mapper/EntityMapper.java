package com.group.KGMS.mapper;

import com.group.KGMS.entity.Entity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EntityMapper {
    int insertNewEntity(@Param("name")String name,@Param("category")String category);
    List<Entity>  selectAllEntity();
}
