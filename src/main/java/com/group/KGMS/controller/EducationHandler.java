package com.group.KGMS.controller;

import com.group.KGMS.entity.T_education;
import com.group.KGMS.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@RestController
@RequestMapping("/education")
public class EducationHandler {
    @Autowired
    private EducationRepository bookRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<T_education> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return bookRepository.findAll(request);
    }

}