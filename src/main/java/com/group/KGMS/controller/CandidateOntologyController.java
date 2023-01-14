package com.group.KGMS.controller;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateOntology;
import com.group.KGMS.service.CandidateOntologyService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.controller
 * @Author: zt
 * @CreateTime: 2022-12-02  17:03
 * @Description:
 */

@Controller
public class CandidateOntologyController {
    @Autowired
    CandidateOntologyService candidateOntologyService;

    /*
     * @Description: 分页获取候选本体
     * @Author: zt
     * @Date: 2022/12/2 17:47
     * @param: [page, limit]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @PostMapping("/candidateOntology/getAllCandidateOntology")
    @ResponseBody
    public JsonResult getCandidateOntologies(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageInfo<CandidateOntology> allCandidateOntologyByPage = candidateOntologyService.getAllCandidateOntologyByPage(page, limit);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",allCandidateOntologyByPage.getList(),allCandidateOntologyByPage.getTotal());
    }
}
