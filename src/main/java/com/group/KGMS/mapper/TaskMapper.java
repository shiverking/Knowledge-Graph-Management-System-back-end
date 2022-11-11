package com.group.KGMS.mapper;

import com.group.KGMS.entity.T_task;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TaskMapper {
    int save(T_task task);
    List<T_task> findByPlanid(int plan_id);
}
