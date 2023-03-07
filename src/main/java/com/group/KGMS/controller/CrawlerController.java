package com.group.KGMS.controller;

import com.group.KGMS.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crawler")
public class CrawlerController {
    @Autowired
    private CrawlerService crawlerService;


}
