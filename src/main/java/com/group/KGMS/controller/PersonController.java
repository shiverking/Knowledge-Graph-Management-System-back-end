package com.group.KGMS.controller;


import com.group.KGMS.entity.*;
import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.repository.EducationRepository;
import com.group.KGMS.repository.PersonRepository;
import com.group.KGMS.repository.PlanRepository;
import com.group.KGMS.repository.ResumeRepository;
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
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PlanRepository planRepository;

    private EducationRepository educationRepository;
    private ResumeRepository resumeRepository;

    @GetMapping("/findAll")
    public List<T_person> findall(){
        return personRepository.findAll();
    }
    @GetMapping("/findAll/{page}/{size}")
    public Page<T_person> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return personRepository.findAll(request);
    }
    @GetMapping("/findById/{id}")
    public List<T_person> findById(@PathVariable("id") Integer id) {
        List<T_person> person= new ArrayList<>();

        person.add( personRepository.findById(id).get());
        return person;
    }
    @GetMapping("/search")
    public Page<T_person> search(RuleForm ruleForm){
        PageRequest request = PageRequest.of(ruleForm.getPage()-1, ruleForm.getSize());
        Page<T_person> aircraft = personRepository.findAll(new Specification<T_person>() {
            @Override
            public Predicate toPredicate(Root<T_person> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate personpredicate = null;
                if (ruleForm.getKey().equals("name_cn")){
                    personpredicate=criteriaBuilder.like(root.get("name_cn").as(String.class),"%"+ruleForm.getValue()+"%");
                }
                if (ruleForm.getKey().equals("name_en")){
                    personpredicate=criteriaBuilder.like(root.get("name_en").as(String.class),"%"+ruleForm.getValue()+"%");

                }
                if (ruleForm.getKey().equals("address")){
                    personpredicate=criteriaBuilder.like(root.get("address").as(String.class),"%"+ruleForm.getValue()+"%");
                }
                return personpredicate;
            }
        },request);
        return aircraft;

    }
    @GetMapping("/findByPlanid/{id}")
    public List<T_person> search(@PathVariable("id") Integer id){
        List<T_person> task = personRepository.findAll(new Specification<T_person>() {
            @Override
            public Predicate toPredicate(Root<T_person> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate aircraftpredicate1 = criteriaBuilder.equal(root.get("plan_id"),id);
                Predicate aircraftpredicate2 = criteriaBuilder.equal(root.get("task_id"),criteriaBuilder.nullLiteral(Integer.class));
                Predicate aircraftpredicate = criteriaBuilder.and(aircraftpredicate1,aircraftpredicate2);
                return aircraftpredicate;
            }
        });
        return task;

    }

    @GetMapping("/findByTaskid/{id}")
    public List<T_person> searchbytaskid(@PathVariable("id") Integer id){
        List<T_person> task = personRepository.findAll(new Specification<T_person>() {
            @Override
            public Predicate toPredicate(Root<T_person> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate aircraftpredicate = criteriaBuilder.equal(root.get("task_id"),id);

                return aircraftpredicate;
            }
        });
        return task;

    }
    @PostMapping("/save")
    public String save(@RequestBody T_person person){
        int max = planRepository.max()+1;
        person.setId(max);
        T_person result = personRepository.save(person);
        if(result != null){
            return "success";
        }else{
            return "error";
        }
    }
    @PutMapping("/update")
    public String update(@RequestBody T_person book){
        T_person result = personRepository.save(book);
        if(result != null){
            return "success";
        }else{
            return "error";
        }
    }
    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        personRepository.deleteById(id);
    }

    @GetMapping("/saveplanid/{id}")
    public T_person saveplanid(@PathVariable("id") Integer id){
        T_person person =personRepository.findById(id).get();
        int max =planRepository.max()+1;
        person.setPlan_id(max);
        personRepository.save(person);
        return person;
    }

    @GetMapping("/deleteplanid/{id}")
    public T_person deleteplanid(@PathVariable("id") Integer id){
        T_person person =personRepository.findById(id).get();
        person.setPlan_id(null);
        personRepository.save(person);
        return person;
    }
    @GetMapping("/setplanid/{id}/{plan_id}/{old}")
    public void setplanid(@PathVariable("id") Integer id,@PathVariable("plan_id") Integer plan_id,@PathVariable("old") Integer old){
        T_person person1 =personRepository.findById(old).get();
        person1.setPlan_id(null);
        personRepository.save(person1);
        T_person person =personRepository.findById(id).get();
        person.setPlan_id(plan_id);
        personRepository.save(person);

    }
    @GetMapping("/deletetask/{id}")
    public void deletetask(@PathVariable("id") Integer id) {
        T_person person =personRepository.findById(id).get();
        person.setTask_id(null);
        personRepository.save(person);
    }

    @GetMapping("/settaskid/{id}/{task_id}")
    public void settaskid(@PathVariable("id") Integer id,@PathVariable("task_id") Integer task_id){
            T_person person = personRepository.findById(id).get();
            person.setTask_id(task_id);
            if (person.getPlan_id()==0){
                person.setPlan_id(null);
            }
            personRepository.save(person);
//        return person;

    }
}
