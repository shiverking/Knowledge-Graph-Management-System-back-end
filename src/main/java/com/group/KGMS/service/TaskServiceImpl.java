package com.group.KGMS.service;

import com.group.KGMS.entity.T_task;
import com.group.KGMS.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskMapper taskMapper;

    @Override
    public int save(T_task task) {
        return taskMapper.save(task);
    }

    @Override
    public List<T_task> findByPlanid(int plan_id) {
        return taskMapper.findByPlanid(plan_id);
    }
}
