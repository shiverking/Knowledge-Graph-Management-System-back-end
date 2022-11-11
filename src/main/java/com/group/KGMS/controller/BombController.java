package com.group.KGMS.controller;

import com.github.pagehelper.PageInfo;

import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_bomb;
//import com.group.KGMS.repository.BombRepository;

import com.group.KGMS.service.BombService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/bomb")
public class BombController {
//    @Autowired
//    private BombRepository bombRepository;
    @Autowired
    private BombService BombService;

    @GetMapping("/findAll/{page}/{size}")
    public PageInfo<T_bomb> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return BombService.findAllBomb(page,size);
    }

    @GetMapping("/search")
    public PageInfo<T_bomb> search(RuleForm ruleForm){
        return BombService.search(ruleForm);
    }
    @PostMapping("/save")
    public String save(@RequestBody T_bomb bomb){
        int result = BombService.save(bomb);
        if(result ==1){
            return "success";
        }else{
            return "error";
        }
    }

    @GetMapping("/findById/{id}")
    public T_bomb findById(@PathVariable("id") Integer id){
        return BombService.findById(id);
    }

    @PutMapping("/update")
    public String update(@RequestBody T_bomb bomb){
        int result = BombService.update(bomb);
        if(result == 1){
            return "success";
        }else{
            return "error";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        BombService.delete(id);
    }

}
