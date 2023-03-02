package com.group.KGMS.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateOntology;
import com.group.KGMS.entity.CandidateOntologyClass;
import com.group.KGMS.service.CandidateOntologyClassService;
import com.group.KGMS.service.CandidateOntologyService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.controller
 * @Author: zt
 * @CreateTime: 2022-12-02  17:03
 * @Description:
 */

@RestController
@RequestMapping("/candidateOntology")
public class CandidateOntologyController {

    @Autowired
    private CandidateOntologyService candidateOntologyService;
    @Autowired
    private CandidateOntologyClassService candidateOntologyClassService;

    /*
     * @Description: 分页获取候选本体
     * @Author: zt
     * @Date: 2022/12/2 17:47
     * @param: [page, limit]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @PostMapping("/getAllCandidateOntology")
    public JsonResult getCandidateOntologies(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageInfo<CandidateOntology> allCandidateOntologyByPage = candidateOntologyService.getAllCandidateOntologyByPage(page, limit);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",allCandidateOntologyByPage.getList(),allCandidateOntologyByPage.getTotal());
    }

    /*
     * @Description: 获取某个候选本体的树形结构
     * @Author: zt
     * @Date: 2023/3/2 17:20
     * @param: [candidateOntologyId]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @PostMapping("/getTreeClassByCandidateOntologyId")
    public JsonResult getClassByCandidateOntologyId(@RequestParam("candidateOntologyId") Integer candidateOntologyId){
        System.out.println(candidateOntologyId);
        CandidateOntologyClass rootClass = candidateOntologyClassService.getRootClassByCandidateOntologyId(candidateOntologyId);
        String treeJson = JSONObject.toJSONString(rootClass);
        System.out.println(treeJson);
        return JsonResult.success("success",treeJson);
    }

    /*
     * @Description: 向某个候选本体中添加新的类
     * @Author: zt
     * @Date: 2023/3/2 17:34
     * @param: [newClass]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @PostMapping("/addClass")
    public JsonResult addClass(@RequestBody CandidateOntologyClass newClass) throws IOException {
        System.out.println("newClass = " + newClass);
        String newClassName = newClass.getName();
        Integer parentId = newClass.getParentId();
        Integer belongCandidateId = newClass.getBelongCandidateId();
        boolean result = candidateOntologyClassService.save(newClassName, parentId, belongCandidateId);
        return JsonResult.success("success", result);
    }

}
