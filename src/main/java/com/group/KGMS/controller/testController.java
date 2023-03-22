package com.group.KGMS.controller;

import com.group.KGMS.service.BigDataService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.controller
 * @Author: zt
 * @CreateTime: 2023-03-02  10:48
 * @Description:
 */

@RestController
public class testController {

    @Resource
    private BigDataService bigDataService;

    @PostMapping("/bigdata/ontoModel")
    public JsonResult getOntoFormBigdata(@RequestBody JsonResult result){
        return bigDataService.saveOnto(result);
    }


}
