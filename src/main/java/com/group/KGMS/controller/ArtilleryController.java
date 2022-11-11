package com.group.KGMS.controller;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.T_artillery;
import com.group.KGMS.entity.RuleForm;
//import com.group.KGMS.repository.ArtilleryRepository;
import com.group.KGMS.service.ArtilleryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/artillery")
public class ArtilleryController {
//    @Autowired
//    private ArtilleryRepository artilleryRepository;
    @Autowired
    private ArtilleryService artilleryService;

    @GetMapping("/findAll/{page}/{size}")
    public PageInfo<T_artillery> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return artilleryService.findAllArtillery(page,size);
    }

//    @GetMapping("/count")
//    public List<Long> count() {
//        List<Long> values = new ArrayList<>();
//        Long aircraft = aircraftRepository.count();
//        Long artillery = artilleryRepository.count();
//        Long bomb = bombRepository.count();
//        Long vessel = vesselRepository.count();
//        values.add(aircraft);
//        values.add(artillery);
//        values.add(bomb);
//        values.add(vessel);
//        return values;
//    }

    @GetMapping("/search")
    public PageInfo<T_artillery> search(RuleForm ruleForm){
        return artilleryService.search(ruleForm);
    }
    @PostMapping("/save")
    public String save(@RequestBody T_artillery artillery){
        int result = artilleryService.save(artillery);
        if(result ==1){
            return "success";
        }else{
            return "error";
        }
    }

    @GetMapping("/findById/{id}")
    public T_artillery findById(@PathVariable("id") Integer id){
        return artilleryService.findById(id);
    }

    @PutMapping("/update")
    public String update(@RequestBody T_artillery artillery){
        int result = artilleryService.update(artillery);
        if(result == 1){
            return "success";
        }else{
            return "error";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        artilleryService.delete(id);
    }
}




