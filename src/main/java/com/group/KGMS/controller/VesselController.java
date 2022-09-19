package com.group.KGMS.controller;

import com.group.KGMS.entity.T_vessel;
import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.repository.VesselRepository;
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
@RequestMapping("/vessel")
public class VesselController {
    @Autowired
    private VesselRepository vesselRepository;


    @GetMapping("/findAll")
    public List<T_vessel> findall(){
        return vesselRepository.findAll();
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<T_vessel> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return vesselRepository.findAll(request);
    }

    @GetMapping("/search")
    public Page<T_vessel> search(RuleForm ruleForm) {
        PageRequest request = PageRequest.of(ruleForm.getPage() - 1, ruleForm.getSize() );
        Page<T_vessel> vessel = vesselRepository.findAll(new Specification<T_vessel>() {
            @Override
            public Predicate toPredicate(Root<T_vessel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate aircraftpredicate = null;
                System.out.println("ruleForm.getKey()");
                if (ruleForm.getKey().equals("name")) {
                    aircraftpredicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + ruleForm.getValue() + "%");
                }
                if (ruleForm.getKey().equals("manufacturer")) {
                    aircraftpredicate = criteriaBuilder.like(root.get("manufacturer").as(String.class), "%" + ruleForm.getValue() + "%");

                }
                if (ruleForm.getKey().equals("type")) {
                    aircraftpredicate = criteriaBuilder.like(root.get("type").as(String.class), "%" + ruleForm.getValue() + "%");
                }
                return aircraftpredicate;
            }
        }, request);
        return vessel;
    }
    @GetMapping("/findByTaskid/{id}")
    public List<T_vessel> searchbytaskid(@PathVariable("id") Integer id){
        List<T_vessel> vessel = vesselRepository.findAll(new Specification<T_vessel>() {
            @Override
            public Predicate toPredicate(Root<T_vessel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate aircraftpredicate = criteriaBuilder.equal(root.get("task_id"),id);

                return aircraftpredicate;
            }
        });
        return vessel;

    }

    @PostMapping("/save")
    public String save(@RequestBody T_vessel book) {
        T_vessel result = vesselRepository.save(book);
        if (result != null) {
            return "success";
        } else {
            return "error";
        }
    }

    @GetMapping("/findById/{id}")
    public T_vessel findById(@PathVariable("id") Integer id) {
        return vesselRepository.findById(id).get();
    }

    @PutMapping("/update")
    public String update(@RequestBody T_vessel book) {
        T_vessel result = vesselRepository.save(book);
        if (result != null) {
            return "success";
        } else {
            return "error";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id) {
        vesselRepository.deleteById(id);
    }

    @GetMapping("/deletetask/{id}")
    public void deletetask(@PathVariable("id") Integer id) {
        T_vessel vessel =vesselRepository.findById(id).get();
        vessel.setTask_id(null);
        vesselRepository.save(vessel);
    }


    @GetMapping("/settaskid/{id}/{task_id}")
    public void settaskid(@PathVariable("id") Integer id,@PathVariable("task_id") Integer task_id){
        T_vessel vessel = vesselRepository.findById(id).get();
        vessel.setTask_id(task_id);
        vesselRepository.save(vessel);

    }
}
