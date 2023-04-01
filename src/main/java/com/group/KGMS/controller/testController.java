package com.group.KGMS.controller;

import com.group.KGMS.dto.OntologyDto;
import com.group.KGMS.service.BigDataService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.print.attribute.standard.JobSheets;


/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.controller
 * @Author: zt
 * @CreateTime: 2023-03-02  10:48
 * @Description:
 */

@RestController
@RequestMapping("/bigdata")
public class testController {

    @Resource
    private BigDataService bigDataService;

    @PostMapping("/ontoModel")
    public JsonResult getOntoFormBigdata(@RequestBody OntologyDto ontologyDto){
        System.out.println("ontologyDto = " + ontologyDto);
        return bigDataService.saveOnto(ontologyDto);
    }

    @GetMapping("/getClass")
    public JsonResult getClassList(){
        return bigDataService.getClassList();
    }

    @GetMapping("/getRelation")
    public JsonResult getRelationList(){
        return bigDataService.getRelationList();
    }

    @GetMapping("/getAttribute")
    public JsonResult getAttributeList(){
        return bigDataService.getAttributeList();
    }


}
