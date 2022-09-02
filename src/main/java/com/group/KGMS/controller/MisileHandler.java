package com.group.KGMS.controller;

import com.group.KGMS.entity.T_misile;
import com.group.KGMS.repository.MisileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/misile")
public class MisileHandler {
    @Autowired
    private MisileRepository bookRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<T_misile> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return bookRepository.findAll(request);
    }
}
