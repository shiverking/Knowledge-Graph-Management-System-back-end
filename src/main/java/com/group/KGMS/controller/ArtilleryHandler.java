package com.group.KGMS.controller;

import com.group.KGMS.entity.T_artillery;
import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.repository.AircraftRepository;
import com.group.KGMS.repository.ArtilleryRepository;
import com.group.KGMS.repository.BombRepository;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/artillery")
public class ArtilleryHandler {
    @Autowired
    private ArtilleryRepository artilleryRepository;
    @Autowired
    private AircraftRepository aircraftRepository;
    @Autowired
    private BombRepository bombRepository;
    @Autowired
    private VesselRepository vesselRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<T_artillery> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return artilleryRepository.findAll(request);
    }

    @GetMapping("/count")
    public List<Long> count() {
        List<Long> values = new ArrayList<>();
        Long aircraft = aircraftRepository.count();
        Long artillery = artilleryRepository.count();
        Long bomb = bombRepository.count();
        Long vessel = vesselRepository.count();
        values.add(aircraft);
        values.add(artillery);
        values.add(bomb);
        values.add(vessel);
        return values;
    }

    @GetMapping("/search")
    public Page<T_artillery> search(RuleForm ruleForm) {
        PageRequest request = PageRequest.of(ruleForm.getPage() - 1, ruleForm.getSize() );
        Page<T_artillery> artillery = artilleryRepository.findAll(new Specification<T_artillery>() {
            @Override
            public Predicate toPredicate(Root<T_artillery> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate aircraftpredicate = null;
                System.out.println("ruleForm.getKey()");
                if (ruleForm.getKey().equals("name")) {
                    aircraftpredicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + ruleForm.getValue() + "%");
                }
                if (ruleForm.getKey().equals("rd_company")) {
                    aircraftpredicate = criteriaBuilder.like(root.get("rd_company").as(String.class), "%" + ruleForm.getValue() + "%");

                }
                if (ruleForm.getKey().equals("type")) {
                    aircraftpredicate = criteriaBuilder.like(root.get("type").as(String.class), "%" + ruleForm.getValue() + "%");
                }
                return aircraftpredicate;
            }
        }, request);
        return artillery;
    }
    @PostMapping("/save")
    public String save(@RequestBody T_artillery book){
        T_artillery result = artilleryRepository.save(book);
        if(result != null){
            return "success";
        }else{
            return "error";
        }
    }

    @GetMapping("/findById/{id}")
    public T_artillery findById(@PathVariable("id") Integer id){
        return artilleryRepository.findById(id).get();
    }

    @PutMapping("/update")
    public String update(@RequestBody T_artillery book){
        T_artillery result = artilleryRepository.save(book);
        if(result != null){
            return "success";
        }else{
            return "error";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        artilleryRepository.deleteById(id);
    }

}

