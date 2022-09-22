package com.group.KGMS.controller;

import com.group.KGMS.entity.T_education;
import com.group.KGMS.repository.EducationRepository;
import com.group.KGMS.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/education")
public class EducationController {
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private PersonRepository personRepository;
    @GetMapping("/findAll/{page}/{size}")
    public Page<T_education> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return educationRepository.findAll(request);
    }


    @GetMapping("/findById/{id}")
    public List<T_education> search(@PathVariable("id") Integer id){
        List<T_education> education = educationRepository.findAll(new Specification<T_education>() {
            @Override
            public Predicate toPredicate(Root<T_education> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate aircraftpredicate = criteriaBuilder.equal(root.get("person_id"),id);

                return aircraftpredicate;
            }
        });
        return education;

    }
    @PostMapping("/saves")
    public void save(@RequestBody List<T_education> book){
        T_education result =null;
        int max =personRepository.max()+1;
        for(int i =0;i< book.size();i++){
            book.get(i).setPerson_id(max);
            result = educationRepository.save(book.get(i));
        }

        }
    @PutMapping("/update")
    public String update(@RequestBody List<T_education> book){
        T_education result =null;
        for(int i =0;i< book.size();i++){
            result = educationRepository.save(book.get(i));
        }
        if(result != null){
            return "success";
        }else{
            return "error";
        }
    }


    @GetMapping("/max")
    public int max(){
        return educationRepository.max();

    }

    @DeleteMapping("/deleteById/{id}")

    public void deleteById(@PathVariable("id") Integer id){

            educationRepository.deleteById(id);
    }


}