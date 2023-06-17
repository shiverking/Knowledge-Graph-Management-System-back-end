package com.group.KGMS.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.KGMS.entity.*;
//import com.group.KGMS.repository.*;
import com.group.KGMS.service.PersonService;
import com.group.KGMS.service.TaskService;
import com.group.KGMS.service.VesselService;
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
@RequestMapping("/task")
public class TaskController {
//    @Autowired
//    private TaskRepository taskRepository;
//    @Autowired
//    private PlanRepository planRepository;
//    @Autowired
//    private TaskpersonRepository taskpersonRepository;
//    @Autowired
//    private TaskweaponRepository taskweaponRepository;
    @Autowired
    private TaskService taskService;
    @Autowired
    private PersonService personService;
    @Autowired
    private VesselService vesselService;
    @GetMapping("/findByPlanid/{id}")
    public List<T_task> findByPlanid(@PathVariable("id") Integer id){
        return taskService.findByPlanid(id);

    }

//    @GetMapping("/findAll/{page}/{size}")
//    public Page<T_task> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
//        PageRequest request = PageRequest.of(page,size);
//        return taskRepository.findAll(request);
//    }
//    @GetMapping("/count")
//    public List<Long> count(){
//        List<Long> values = new ArrayList<>();
//        Long plan = planRepository.count();
//        Long task = taskRepository.count();
//        Long taskperson = taskpersonRepository.count();
//        Long taskweapon = taskweaponRepository.count();
//        values.add(plan);
//        values.add(task);
//        values.add(taskperson);
//        values.add(taskweapon);
//        return values;
//    }
//    @GetMapping("/findById/{id}")
//    public List<T_task> findById(@PathVariable("id") Integer id) {
//        List<T_task> task= new ArrayList<>();
//
//        task.add( taskRepository.findById(id).get());
//        return task;
//    }
//    @PostMapping("/saves")
//    public void save(@RequestBody List<T_task> book){
//        T_task result =null;
//        int max =planRepository.max()+1;
//        for(int i =0;i< book.size();i++){
//            book.get(i).setPlan_id(max);
//            result = taskRepository.save(book.get(i));
//        }
//
//    }
//    @DeleteMapping("/deleteById/{id}")
//    public void deleteById(@PathVariable("id") Integer id){
//        taskRepository.deleteById(id);
//    }
//
    @PostMapping("/update")
    public void update(@RequestBody Map<String,Object> map){
//        List<T_person> po = (List<T_person>)map.get("deleteperson");
        ObjectMapper objectMapper = new ObjectMapper();
//        List<T_person> person_old = objectMapper.convertValue(po, new TypeReference<List<T_person>>() {});
//        List<T_vessel> vo =(List<T_vessel>) map.get("deletevessel");
//        List<T_vessel> vessel_old = objectMapper.convertValue(vo, new TypeReference<List<T_vessel>>() {} );
        List<T_person> pn =(List<T_person>) map.get("person_new");
        List<T_person> person_new = objectMapper.convertValue(pn, new TypeReference<List<T_person>>() {});
        List<T_vessel> vn =(List<T_vessel>) map.get("vessel_new");
        List<T_vessel> vessel_new = objectMapper.convertValue(vn, new TypeReference<List<T_vessel>>() {});
        int task_id = person_new.get(0).getTask_id();
        int plan_id = person_new.get(0).getPlan_id();
        personService.deleteTaskid(task_id);
        vesselService.deleteTaskid(task_id);
        for(int i =0;i< person_new.size();i++){
                personService.saveTaskid(task_id,plan_id,person_new.get(i).getId());
        }
        for(int i =0;i< vessel_new.size();i++){
                vesselService.saveTaskid(task_id,vessel_new.get(i).getId());
        }
    }

}
