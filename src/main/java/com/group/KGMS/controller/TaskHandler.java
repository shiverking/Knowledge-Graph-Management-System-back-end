package com.group.KGMS.controller;


import com.group.KGMS.entity.T_task;

import com.group.KGMS.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskHandler {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private TaskpersonRepository taskpersonRepository;
    @Autowired
    private TaskweaponRepository taskweaponRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<T_task> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        PageRequest request = PageRequest.of(page,size);
        return taskRepository.findAll(request);
    }
    @GetMapping("/count")
    public List<Long> count(){
        List<Long> values = new ArrayList<>();
        Long plan = planRepository.count();
        Long task = taskRepository.count();
        Long taskperson = taskpersonRepository.count();
        Long taskweapon = taskweaponRepository.count();
        values.add(plan);
        values.add(task);
        values.add(taskperson);
        values.add(taskweapon);
        return values;
    }
}
