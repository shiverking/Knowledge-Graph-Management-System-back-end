package com.group.KGMS.controller;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateKG;
import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.service.CandidateKgService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

@Controller
public class CandidateKGController {
    @Autowired
    CandidateKgService candidateKgService;

    /**
     * 分页获取候选图谱
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/candidateKg/getAllCandidateKg")
    @ResponseBody
    public JsonResult getCandidateKgs(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageInfo<CandidateKG> pageInfo = candidateKgService.getCandidateKgByPage(page,limit);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }



    @PostMapping("/candidateKg/new")
    @ResponseBody
    public JsonResult insertNewCandidateKG(@RequestBody Map<String, Object> info){
        Date create_time = new Date();
        Date change_time = new Date();
        String name = (String) info.get("name");
        String creator = "-";
        if(info.get("creator")!=null){
            creator =  (String)info.get("creator");
        }
        String status = "已建立";
        String comment = (String) info.get("comment");
        Long id = candidateKgService.insertNewKG(name,creator,create_time,change_time,status,comment);
        if(id!= 0){
            return JsonResult.success("success","新增候选图谱成功",id);
        }
        return JsonResult.error("failure","新增候选图谱失败");
    }
}
