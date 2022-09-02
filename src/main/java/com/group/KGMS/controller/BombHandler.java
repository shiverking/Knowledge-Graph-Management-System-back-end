package com.group.KGMS.controller;

import com.group.KGMS.entity.T_bomb;
import com.group.KGMS.repository.BombRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bomb")
public class BombHandler {
    @Autowired
    private BombRepository bookRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<T_bomb> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        return bookRepository.findAll(request);
    }
}
