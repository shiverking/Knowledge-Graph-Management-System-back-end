package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_plan;

public interface PlanService {
    T_plan findById(int id);
    PageInfo<T_plan> findAllPlan(Integer page, Integer size);
    PageInfo<T_plan> search(RuleForm ruleForm);
    int save(T_plan plan);
    int update(T_plan plan);
    int delete(int id);
    int maxid();
}
