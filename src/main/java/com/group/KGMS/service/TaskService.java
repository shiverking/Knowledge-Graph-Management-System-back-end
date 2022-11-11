package com.group.KGMS.service;

import com.group.KGMS.entity.T_task;

import java.util.List;

public interface TaskService {
    int save(T_task task);
    List<T_task> findByPlanid(int plan_id);
}
