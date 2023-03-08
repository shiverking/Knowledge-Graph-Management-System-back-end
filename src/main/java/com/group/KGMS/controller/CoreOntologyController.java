package com.group.KGMS.controller;

import com.alibaba.fastjson2.JSONObject;
import com.group.KGMS.entity.CoreOntologyClass;
import com.group.KGMS.entity.CoreOntologyTriple;
import com.group.KGMS.service.CoreOntologyClassService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.net.idn.Punycode;

import java.io.IOException;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.controller
 * @Author: zt
 * @CreateTime: 2023-03-08  15:57
 * @Description:
 */

@RestController
@RequestMapping("/coreOntology")
public class CoreOntologyController {

    @Autowired
    private CoreOntologyClassService coreOntologyClassService;

    /*
     * @Description: 获取核心本体树形结构数据
     * @Author: zt
     * @Date: 2023/3/9 0:24
     * @param: []
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @GetMapping("/getTreeData")
    public JsonResult getTreeData(){
        CoreOntologyClass rootClass = coreOntologyClassService.getRootClass();
        String treeJson = JSONObject.toJSONString(rootClass);
        System.out.println("treeJson = " + treeJson);
        return JsonResult.success("success", treeJson);
    }

    /*
     * @Description: 向核心本体中添加新的类
     * @Author: zt
     * @Date: 2023/3/9 0:33
     * @param: [newClass]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @PostMapping("/addClass")
    public JsonResult addClass(@RequestBody CoreOntologyClass newClass){
        System.out.println("newClass = " + newClass);
        try {
            coreOntologyClassService.save(newClass.getClassName(), newClass.getParentId());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error("添加失败");
        }
        return JsonResult.success("success");
    }

    @DeleteMapping("/deleteClass")
    public JsonResult deleteClass(@RequestBody CoreOntologyClass coreOntologyClass){
        try {
            coreOntologyClassService.remove(coreOntologyClass.getClassName());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error("删除失败，这个类有子类");
        }
        return JsonResult.success("success");
    }


}
