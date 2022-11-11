package com.group.KGMS.controller;


import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.T_misile;
import com.group.KGMS.entity.RuleForm;
//import com.group.KGMS.repository.MisileRepository;
import com.group.KGMS.service.MisileService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/misile")
public class MisileController {
//    @Autowired
//    private MisileRepository misileRepository;
    @Autowired
    private MisileService misileService;

    @GetMapping("/findAll/{page}/{size}")
    public PageInfo<T_misile> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return misileService.findAllMisile(page,size);
    }

    @GetMapping("/search")
    public PageInfo<T_misile> search(RuleForm ruleForm){
        return misileService.search(ruleForm);
    }
    @PostMapping("/save")
    public String save(@RequestBody T_misile misile){
        int result = misileService.save(misile);
        if(result ==1){
            return "success";
        }else{
            return "error";
        }
    }

    @GetMapping("/findById/{id}")
    public T_misile findById(@PathVariable("id") Integer id){
        return misileService.findById(id);
    }

    @PutMapping("/update")
    public String update(@RequestBody T_misile misile){
        int result = misileService.update(misile);
        if(result == 1){
            return "success";
        }else{
            return "error";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        misileService.delete(id);
    }
}
