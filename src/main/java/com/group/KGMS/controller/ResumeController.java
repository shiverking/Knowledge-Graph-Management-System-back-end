package com.group.KGMS.controller;

import com.group.KGMS.entity.T_resume;
import com.group.KGMS.repository.PersonRepository;
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
import java.util.List;

@RestController
@RequestMapping("/resume")
public class ResumeController {
    @Autowired
    private ResumeRepository resumeRepository;
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<T_resume> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        PageRequest request = PageRequest.of(page,size);
        return resumeRepository.findAll(request);
    }
    @GetMapping("/findById/{id}")
    public List<T_resume> search(@PathVariable("id") Integer id){
        List<T_resume> resume = resumeRepository.findAll(new Specification<T_resume>() {
            @Override
            public Predicate toPredicate(Root<T_resume> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate aircraftpredicate = criteriaBuilder.equal(root.get("person_id"),id);
                return aircraftpredicate;
            }
        });
        return resume;

    }
    @PostMapping("/saves")
    public String save(@RequestBody List<T_resume> book){
        T_resume result =null;
        int max =personRepository.max()+1;
        for(int i =0;i< book.size();i++){
            book.get(i).setPerson_id(max);
            result = resumeRepository.save(book.get(i));
        }
        if(result != null){
            return "success";
        }else{
            return "error";
        }
    }
    @PutMapping("/update")
    public String update(@RequestBody List<T_resume> book){
        T_resume result =null;
        for(int i =0;i< book.size();i++){
            result = resumeRepository.save(book.get(i));
        }
        if(result != null){
            return "success";
        }else{
            return "error";
        }
    }
    @DeleteMapping("/deleteById/{id}")

    public void deleteById(@PathVariable("id") Integer id){

        resumeRepository.deleteById(id);
    }

}
