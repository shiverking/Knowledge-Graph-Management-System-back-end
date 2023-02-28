package com.group.KGMS.controller;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.service.UntructuredTextService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class UnstructuredTextController {
    @Autowired
    UntructuredTextService untructuredTextService;
    /**
     * 分页获取所有非结构文本
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/unstructure/getAllTextByPage")
    @ResponseBody
    public JsonResult getCandidateTriples(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageInfo<Map<Object,String>> pageInfo = untructuredTextService.getUnstructuredTextByPage(page,limit);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }
}
