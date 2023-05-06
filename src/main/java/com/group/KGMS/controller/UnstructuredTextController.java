package com.group.KGMS.controller;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.entity.UnstructuredText;
import com.group.KGMS.entity.UnstructuredTextOriginal;
import com.group.KGMS.service.UntructuredTextService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
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
        List<UnstructuredText> pageInfo = untructuredTextService.getUnstructuredTextByPage(page,limit);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",pageInfo,untructuredTextService.getSumOfUnstructuredText());
    }

    @GetMapping("/unstructure/getAllTextByPageandcid/{page}/{limit}/{cid}")
    public JsonResult getCandidateTriples(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit, @PathVariable("cid") Integer cid){
        return untructuredTextService.getUnstructuredTextByPageandcid(page,limit,cid);
    }
    @GetMapping("/unstructure/getAllTextBytitle/{page}/{limit}/{title}")
    public JsonResult getAllTextBytitle(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit, @PathVariable("title") String title){
        return untructuredTextService.getAllTextBytitle(page,limit,title);
    }
}
