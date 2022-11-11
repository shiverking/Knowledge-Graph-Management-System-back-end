package com.group.KGMS.controller;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.T_vessel;
import com.group.KGMS.entity.RuleForm;

import com.group.KGMS.service.VesselService;
import org.springframework.beans.factory.annotation.Autowired;

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
//    @Autowired
//    private VesselRepository vesselRepository;
    @Autowired
    private VesselService vesselService;

    @GetMapping("/findAll")
    public List<T_vessel> findall(){
        return vesselService.findAll();
    }


    @GetMapping("/findAll/{page}/{size}")
    public PageInfo<T_vessel> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return vesselService.findAllVessel(page,size);
    }

    @GetMapping("/search")
    public PageInfo<T_vessel> search(RuleForm ruleForm){
        return vesselService.search(ruleForm);
    }
    @PostMapping("/save")
    public String save(@RequestBody T_vessel vessel){
        int result = vesselService.save(vessel);
        if(result ==1){
            return "success";
        }else{
            return "error";
        }
    }

    @GetMapping("/findById/{id}")
    public T_vessel findById(@PathVariable("id") Integer id){
        return vesselService.findById(id);
    }

    @PutMapping("/update")
    public String update(@RequestBody T_vessel vessel){
        int result = vesselService.update(vessel);
        if(result == 1){
            return "success";
        }else{
            return "error";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        vesselService.delete(id);
    }

    @GetMapping("/findByTaskid/{id}")
    public List<T_vessel> searchbytaskid(@PathVariable("id") Integer id){
        return vesselService.findByTaskid(id);

    }



//    @GetMapping("/deletetask/{id}")
//    public void deletetask(@PathVariable("id") Integer id) {
//        T_vessel vessel =vesselRepository.findById(id).get();
//        vessel.setTask_id(null);
//        vesselRepository.save(vessel);
//    }
//
//
//    @GetMapping("/settaskid/{id}/{task_id}")
//    public void settaskid(@PathVariable("id") Integer id,@PathVariable("task_id") Integer task_id){
//        T_vessel vessel = vesselRepository.findById(id).get();
//        vessel.setTask_id(task_id);
//        vesselRepository.save(vessel);
//
//    }
}
