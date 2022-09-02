package com.group.KGMS.controller;

import com.group.KGMS.entity.T_aircraft;
import com.group.KGMS.form.RuleForm;
import com.group.KGMS.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@RestController
@RequestMapping("/aircraft")
public class AircraftHandler {
    @Autowired
    private AircraftRepository aircraftRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<T_aircraft> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        PageRequest request = PageRequest.of(page,size);
        return aircraftRepository.findAll(request);
    }
    @GetMapping("/search")
    public Page<T_aircraft> search(RuleForm ruleForm){
        PageRequest request = PageRequest.of(ruleForm.getPage()-1, ruleForm.getSize()-0);
        Page<T_aircraft> aircraft = aircraftRepository.findAll(new Specification<T_aircraft>() {
            @Override
            public Predicate toPredicate(Root<T_aircraft> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate aircraftpredicate = null;
                System.out.println("ruleForm.getKey()");
                if (ruleForm.getKey().equals("aircraft_name")){
                    aircraftpredicate=criteriaBuilder.like(root.get("aircraft_name").as(String.class),"%"+ruleForm.getValue()+"%");
                }
                if (ruleForm.getKey().equals("rd_company")){
                    aircraftpredicate=criteriaBuilder.like(root.get("rd_company").as(String.class),"%"+ruleForm.getValue()+"%");

                }
                if (ruleForm.getKey().equals("type")){
                    aircraftpredicate=criteriaBuilder.like(root.get("type").as(String.class),"%"+ruleForm.getValue()+"%");
                }
                return aircraftpredicate;
            }
        },request);
        return aircraft;

    }
    @PostMapping("/save")
    public String save(@RequestBody T_aircraft book){
        T_aircraft result = aircraftRepository.save(book);
        if(result != null){
            return "success";
        }else{
            return "error";
        }
    }

    @GetMapping("/findById/{id}")
    public T_aircraft findById(@PathVariable("id") Integer id){
        return aircraftRepository.findById(id).get();
    }

    @PutMapping("/update")
    public String update(@RequestBody T_aircraft book){
        T_aircraft result = aircraftRepository.save(book);
        if(result != null){
            return "success";
        }else{
            return "error";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        aircraftRepository.deleteById(id);
    }
}
