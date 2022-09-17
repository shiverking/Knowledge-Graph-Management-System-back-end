package com.group.KGMS.controller;


import com.group.KGMS.entity.T_task;
import com.group.KGMS.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    @GetMapping("/findById/{id}")
    public List<T_task> findById(@PathVariable("id") Integer id) {
        List<T_task> task= new ArrayList<>();

        task.add( taskRepository.findById(id).get());
        return task;
    }
    @GetMapping("/findByPlanid/{id}")
    public List<T_task> search(@PathVariable("id") Integer id){
        List<T_task> task = taskRepository.findAll(new Specification<T_task>() {
            @Override
            public Predicate toPredicate(Root<T_task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate aircraftpredicate = criteriaBuilder.equal(root.get("plan_id"),id);
                return aircraftpredicate;
            }
        });
        return task;

    }
    @PostMapping("/saves")
    public void save(@RequestBody List<T_task> book){
        T_task result =null;
        int max =planRepository.max()+1;
        for(int i =0;i< book.size();i++){
            book.get(i).setPlan_id(max);
            result = taskRepository.save(book.get(i));
        }

    }
    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        taskRepository.deleteById(id);
    }

    @PutMapping("/update")
    public String update(@RequestBody List<T_task> book){
        T_task result =null;
        for(int i =0;i< book.size();i++){
            result = taskRepository.save(book.get(i));
        }
        if(result != null){
            return "success";
        }else{
            return "error";
        }
    }

}
