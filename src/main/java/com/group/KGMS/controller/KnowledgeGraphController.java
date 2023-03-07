package com.group.KGMS.controller;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.Triple;
import com.group.KGMS.service.KnowledgeGraphService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KnowledgeGraphController {
    @Autowired
    KnowledgeGraphService knowledgeGraphService;
    /**
     * 分页获取用户列表
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/kg/getAllTriples")
    @ResponseBody
    public JsonResult getUsers(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        //默认表名是militarykg
        String tableName = "militarykg";
        PageInfo<Triple> pageInfo = knowledgeGraphService.getAllTriplesByPage(tableName,page,limit);
//        System.out.println(pageInfo.getList());
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }
}
