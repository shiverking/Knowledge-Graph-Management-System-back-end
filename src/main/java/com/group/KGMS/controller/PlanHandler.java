package com.group.KGMS.controller;


import com.group.KGMS.entity.T_plan;
import com.group.KGMS.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plan")
public class PlanHandler {
    @Autowired
    private PlanRepository bookRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<T_plan> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        PageRequest request = PageRequest.of(page,size);
        return bookRepository.findAll(request);
    }
}
