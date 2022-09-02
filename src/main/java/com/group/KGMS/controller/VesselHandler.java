package com.group.KGMS.controller;

import com.group.KGMS.entity.T_vessel;
import com.group.KGMS.repository.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vessel")
public class VesselHandler {
    @Autowired
    private VesselRepository bookRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<T_vessel> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        PageRequest request = PageRequest.of(page,size);
        return bookRepository.findAll(request);
    }
}
