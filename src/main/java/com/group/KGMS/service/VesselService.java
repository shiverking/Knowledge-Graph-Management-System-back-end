package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_person;
import com.group.KGMS.entity.T_vessel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VesselService {
    List<T_vessel> findByTaskid(int task_id);
    T_vessel findById(int id);
    List<T_vessel> findAll();
    PageInfo<T_vessel> findAllVessel(Integer page, Integer size);
    PageInfo<T_vessel> search(RuleForm ruleForm);
    int save(T_vessel artillery);
    int update(T_vessel artillery);
    int delete(int id);
    int deleteTaskid(int task_id);
    int saveTaskid( int task_id,  int id);
}
