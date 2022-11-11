package com.group.KGMS.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.*;
import com.group.KGMS.entity.RuleForm;
//import com.group.KGMS.repository.EducationRepository;
//import com.group.KGMS.repository.PersonRepository;
//import com.group.KGMS.repository.PlanRepository;
//import com.group.KGMS.repository.ResumeRepository;

import com.group.KGMS.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

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
@RequestMapping("/person")
public class PersonController {
//    @Autowired
//    private PersonRepository personRepository;
//    @Autowired
//    private PlanRepository planRepository;
//
//    private EducationRepository educationRepository;
//    private ResumeRepository resumeRepository;
    @Autowired
    private PersonService personService;
    @GetMapping("/findAll")
    public List<T_person> findall(){
        return personService.findAll();
    }

    @GetMapping("/searchedu/{id}")
    public List<T_education> searchedu(@PathVariable("id") Integer id){
        return personService.searchedu(id);

    }
    @GetMapping("/searchresu/{id}")
    public List<T_resume> searchresu(@PathVariable("id") Integer id){
        return personService.searchresu(id);

    }
    @GetMapping("/findAll/{page}/{size}")
    public PageInfo<T_person> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return personService.findAllPerson(page,size);
    }

    @GetMapping("/search")
    public PageInfo<T_person> search(RuleForm ruleForm){
        return personService.search(ruleForm);
    }
    @PostMapping("/save")
    public String save(@RequestBody Map<String,Object> map){
        Object per = map.get("person");
        ObjectMapper objectMapper = new ObjectMapper();
        T_person person = objectMapper.convertValue(per, T_person.class);
        List<T_education> edu =(List<T_education>) map.get("education");
        List<T_education> education = objectMapper.convertValue(edu, new TypeReference<List<T_education>>() {} );
        List<T_resume> res =(List<T_resume>) map.get("resume");
        List<T_resume> resume = objectMapper.convertValue(res, new TypeReference<List<T_resume>>() {});
        int result1 = personService.save(person);

        int max = personService.maxid();
        for(int i =0;i< education.size();i++){
            education.get(i).setPerson_id(max);
            personService.saveedu(education.get(i));
        }
        for(int i =0;i< resume.size();i++){
            resume.get(i).setPerson_id(max);
            personService.saveresume(resume.get(i));
        }

        if(result1 ==1 ){
            return "success";
        }else{
            return "error";
        }
    }

    @GetMapping("/findById/{id}")
    public T_person findById(@PathVariable("id") Integer id){
        return personService.findById(id);
    }

    @PutMapping("/update")
    public String update(@RequestBody Map<String,Object> map){
        Object per = map.get("person");
        ObjectMapper objectMapper = new ObjectMapper();
        T_person person = objectMapper.convertValue(per, T_person.class);
        List<T_education> edu =(List<T_education>) map.get("education");
        List<T_education> education = objectMapper.convertValue(edu, new TypeReference<List<T_education>>() {} );
        List<T_resume> res =(List<T_resume>) map.get("resume");
        List<T_resume> resume = objectMapper.convertValue(res, new TypeReference<List<T_resume>>() {});
        int id = person.getId();
        personService.deleteedu(id);
        personService.deleteresu(id);
        int result1 = personService.update(person);
        for(int i =0;i< education.size();i++){
            personService.saveedu(education.get(i));
        }
        for(int i =0;i< resume.size();i++){
            personService.saveresume(resume.get(i));
        }

        if(result1 ==1 ){
            return "success";
        }else{
            return "error";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        personService.delete(id);
    }


    @GetMapping("/findByPlanid/{id}")
    public List<T_person> search(@PathVariable("id") Integer id){
        return personService.findByPlanid(id);

    }

    @GetMapping("/findByTaskid/{id}")
    public List<T_person> searchbytaskid(@PathVariable("id") Integer id){
        return personService.findByTaskid(id);

    }


//    @GetMapping("/saveplanid/{id}")
//    public T_person saveplanid(@PathVariable("id") Integer id){
//        T_person person =personRepository.findById(id).get();
//        int max =planRepository.max()+1;
//        person.setPlan_id(max);
//        personRepository.save(person);
//        return person;
//    }
//
//    @GetMapping("/deleteplanid/{id}")
//    public T_person deleteplanid(@PathVariable("id") Integer id){
//        T_person person =personRepository.findById(id).get();
//        person.setPlan_id(null);
//        personRepository.save(person);
//        return person;
//    }
//    @GetMapping("/setplanid/{id}/{plan_id}/{old}")
//    public void setplanid(@PathVariable("id") Integer id,@PathVariable("plan_id") Integer plan_id,@PathVariable("old") Integer old){
//        T_person person1 =personRepository.findById(old).get();
//        person1.setPlan_id(null);
//        personRepository.save(person1);
//        T_person person =personRepository.findById(id).get();
//        person.setPlan_id(plan_id);
//        personRepository.save(person);
//
//    }
//    @GetMapping("/deletetask/{id}")
//    public void deletetask(@PathVariable("id") Integer id) {
//        T_person person =personRepository.findById(id).get();
//        person.setTask_id(null);
//        personRepository.save(person);
//    }
//
//    @GetMapping("/settaskid/{id}/{task_id}")
//    public void settaskid(@PathVariable("id") Integer id,@PathVariable("task_id") Integer task_id){
//            T_person person = personRepository.findById(id).get();
//            person.setTask_id(task_id);
//            if (person.getPlan_id()==0){
//                person.setPlan_id(null);
//            }
//            personRepository.save(person);
////        return person;
//
//    }
}
