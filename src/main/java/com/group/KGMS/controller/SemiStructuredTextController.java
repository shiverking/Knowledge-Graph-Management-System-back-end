package com.group.KGMS.controller;

import com.group.KGMS.entity.SemistructuredDataOriginal;
import com.group.KGMS.service.SemistructuredDataService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SemiStructuredTextController {
    @Autowired
    SemistructuredDataService semistructuredDataService;

    @PostMapping("/semistructure/getSemistructuredDataBycid")
    @ResponseBody
    public JsonResult getCandidateTriples(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, @RequestParam("cid") Integer cid){
        return semistructuredDataService.getSemistructuredDataBycid(page,limit,cid);
    }

}
