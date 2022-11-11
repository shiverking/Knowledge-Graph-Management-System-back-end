package com.group.KGMS.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.*;

import com.group.KGMS.service.PersonService;
import com.group.KGMS.service.PlanService;
import com.group.KGMS.service.TaskService;
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
import java.util.Map;

@RestController
@RequestMapping("/plan")
public class PlanController {
//    @Autowired
//    private PlanRepository planRepository;
//    @GetMapping("/findAll")
//    public List<T_plan> findAll() {
//        return planRepository.findAll();
//    }
    @Autowired
    private PlanService planService;
    @Autowired
    private PersonService personService;
    @Autowired
    private TaskService taskService;
    @GetMapping("/findAll/{page}/{size}")
    public PageInfo<T_plan> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return planService.findAllPlan(page,size);
    }

    @GetMapping("/search")
    public PageInfo<T_plan> search(RuleForm ruleForm){
        return planService.search(ruleForm);
    }
    @PostMapping("/save")
    public String save(@RequestBody Map<String, Object> map){
        Object pl = map.get("plan");
        ObjectMapper objectMapper = new ObjectMapper();
        T_plan plan = objectMapper.convertValue(pl, T_plan.class);
        List<T_task> ta =(List<T_task>) map.get("task");
        List<T_task> task = objectMapper.convertValue(ta, new TypeReference<List<T_task>>() {} );
        Object perid = map.get("person_id");
        int person_id = objectMapper.convertValue(perid, int.class);
        int result1 = planService.save(plan);
        int max = planService.maxid();
        personService.updateplan(max,person_id);
        for(int i =0;i< task.size();i++){
            task.get(i).setPlan_id(max);
            taskService.save(task.get(i));
        }

        if(result1 ==1 ){
            return "success";
        }else{
            return "error";
        }
    }

    @GetMapping("/findById/{id}")
    public T_plan findById(@PathVariable("id") Integer id){
        return planService.findById(id);
    }

    @PutMapping("/update")
    public String update(@RequestBody T_plan plan){
        int result = planService.update(plan);
        if(result == 1){
            return "success";
        }else{
            return "error";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        planService.delete(id);
    }
}
