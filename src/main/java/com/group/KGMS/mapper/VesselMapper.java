package com.group.KGMS.mapper;

import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_vessel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VesselMapper {
    T_vessel findById(int id);

    List<T_vessel> findByTaskid(int task_id);

    List<T_vessel> findAllVessel();

    List<T_vessel> search(RuleForm ruleForm);

    int save(T_vessel vessel);

    int update(T_vessel vessel);

    int delete(int id);

    int deleteTaskid(int task_id);

    int saveTaskid(@Param("task_id") int task_id, @Param("id") int id);
}
