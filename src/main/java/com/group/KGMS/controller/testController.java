package com.group.KGMS.controller;

import com.group.KGMS.utils.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.controller
 * @Author: zt
 * @CreateTime: 2023-03-02  10:48
 * @Description:
 */

@RestController
public class testController {

    @PostMapping("/bigdata/ontoModel")
    public JsonResult getOnto(@RequestBody Map map){
        System.out.println("map = " + map);
        return JsonResult.success("ok");
    }

}
