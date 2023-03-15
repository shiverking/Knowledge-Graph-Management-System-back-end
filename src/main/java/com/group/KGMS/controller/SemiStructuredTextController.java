package com.group.KGMS.controller;

import com.group.KGMS.entity.SemistructuredDataOriginal;
import com.group.KGMS.service.SemistructuredDataService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/semistructure")
public class SemiStructuredTextController {
    @Autowired
    SemistructuredDataService semistructuredDataService;

    @GetMapping("/getSemistructuredDataBycid/{page}/{limit}/{cid}")
    public JsonResult getCandidateTriples(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit, @PathVariable("cid") Integer cid){
        return semistructuredDataService.getSemistructuredDataBycid(page,limit,cid);
    }

}
