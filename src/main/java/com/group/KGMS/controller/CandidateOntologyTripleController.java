package com.group.KGMS.controller;

import com.alibaba.fastjson2.JSONObject;
import com.group.KGMS.entity.CandidateOntologyClass;
import com.group.KGMS.service.CandidateOntologyClassService;
import com.group.KGMS.service.CandidateOntologyService;
import com.group.KGMS.service.CandidateOntologyTripleService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.controller
 * @Author: zt
 * @CreateTime: 2022-12-08  19:43
 * @Description:
 */

@Controller
public class CandidateOntologyTripleController {

    @Autowired
    CandidateOntologyTripleService candidateOntologyTripleService;
    @Autowired
    CandidateOntologyClassService candidateOntologyClassService;

    @PostMapping("/candidateOntology/getTreeClassByCandidateOntologyId")
    @ResponseBody
    public JsonResult getClassByCandidateOntologyId(@RequestParam("candidateOntologyId") Integer candidateOntologyId){
        System.out.println(candidateOntologyId);
        CandidateOntologyClass rootClass = candidateOntologyClassService.getClassByCandidateOntologyId(candidateOntologyId);
        String treeJson = JSONObject.toJSONString(rootClass);
        System.out.println(treeJson);
        return JsonResult.success("success",treeJson);
    }
}
