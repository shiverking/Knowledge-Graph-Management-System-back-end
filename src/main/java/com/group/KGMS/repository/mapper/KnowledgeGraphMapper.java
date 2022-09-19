package com.group.KGMS.repository.mapper;
import com.group.KGMS.entity.Triple;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface KnowledgeGraphMapper {
    //获取用户列表（雇主和应聘者）
    List<Triple> getAllTriplesFromTable(@Param("tableName")String tableName);
}
