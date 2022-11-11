package com.group.KGMS.mapper;

import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_plan;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PlanMapper {
    T_plan findById(int id);
    List<T_plan> findAllPlan();
    List<T_plan> search(RuleForm ruleForm);
    int save(T_plan plan);
    int update(T_plan plan);
    int delete(int id);
    int maxid();
}
