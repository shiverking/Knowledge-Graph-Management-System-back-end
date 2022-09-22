package com.group.KGMS.controller;


import com.group.KGMS.entity.T_plan;
import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.repository.PlanRepository;
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
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    private PlanRepository planRepository;
    @GetMapping("/findAll")
    public List<T_plan> findAll() {
        return planRepository.findAll();
    }
    @GetMapping("/findAll/{page}/{size}")
    public Page<T_plan> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        PageRequest request = PageRequest.of(page,size);
        return planRepository.findAll(request);
    }
    @GetMapping("/findById/{id}")
    public List<T_plan> findById(@PathVariable("id") Integer id) {
        List<T_plan> plan= new ArrayList<>();

        plan.add( planRepository.findById(id).get());
        return plan;
    }
    @GetMapping("/search")
    public Page<T_plan> search(RuleForm ruleForm){
        PageRequest request = PageRequest.of(ruleForm.getPage()-1, ruleForm.getSize());
        Page<T_plan> aircraft = planRepository.findAll(new Specification<T_plan>() {
            @Override
            public Predicate toPredicate(Root<T_plan> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate aircraftpredicate = null;
                if (ruleForm.getKey().equals("plan_name")){
                    aircraftpredicate=criteriaBuilder.like(root.get("plan_name").as(String.class),"%"+ruleForm.getValue()+"%");
                }
                if (ruleForm.getKey().equals("plan_status")){
                    aircraftpredicate=criteriaBuilder.like(root.get("plan_status").as(String.class),"%"+ruleForm.getValue()+"%");

                }
                return aircraftpredicate;
            }
        },request);
        return aircraft;

    }
    @PostMapping("/save")
    public String save(@RequestBody T_plan book){
        int max = planRepository.max()+1;
        book.setId(max);
        T_plan result = planRepository.save(book);
        if(result != null){
            return "success";
        }else{
            return "error";
        }
    }
    @PutMapping("/update")
    public String update(@RequestBody T_plan book){
        T_plan result = planRepository.save(book);
        if(result != null){
            return "success";
        }else{
            return "error";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        planRepository.deleteById(id);
    }
}
