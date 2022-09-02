package com.group.KGMS.controller;

import com.group.KGMS.entity.T_resume;
import com.group.KGMS.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resume")
public class ResumeHandler {
    @Autowired
    private ResumeRepository bookRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<T_resume> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        PageRequest request = PageRequest.of(page,size);
        return bookRepository.findAll(request);
    }
}
