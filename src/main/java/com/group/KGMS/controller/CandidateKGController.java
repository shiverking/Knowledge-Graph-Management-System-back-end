package com.group.KGMS.controller;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateKG;
import com.group.KGMS.entity.CandidateKGInfo;
import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.service.CandidateKgService;
import com.group.KGMS.service.CandidateTripleService;
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
    @Autowired
    CandidateTripleService candidateTripleService;
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
    /**
     * 分页获取已融合的候选图谱
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/candidateKg/getAllOldCandidateKg")
    @ResponseBody
    public JsonResult getAllOldCandidateKgs(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageInfo<CandidateKG> pageInfo = candidateKgService.getOldCandidateKgByPage(page,limit);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }

    /**
     * 创建一个新的候选图谱
     * @param info
     * @return
     */
    @PostMapping("/candidateKg/new")
    @ResponseBody
    public JsonResult insertNewCandidateKG(@RequestBody Map<String, Object> info){
        Date create_time = new Date();
        Date change_time = new Date();
        String name = (String) info.get("name");
        //等着这获取当前用户名并更换即可
        String creator = "admin";
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
    /**
     * 根据id删除某条三元组
     * @param info
     * @return
     */
    @PostMapping("/candidateKg/delete")
    @ResponseBody
    public JsonResult deleteCandidateTripleById(@RequestBody Map<String, Object> info){
        Long id = Long.parseLong(String.valueOf(info.get("id")));
        if(candidateTripleService.deleteCandidateTripleById(id)==1){
            return JsonResult.success("success");
        }
        //删除失败
        return JsonResult.success("failure");
    }

    /**
     * 根据id更新
     * @param info
     * @return
     */
    @PostMapping("/candidateKg/update")
    @ResponseBody
    public JsonResult updateCandidateTripleById(@RequestBody Map<String, Object> info){
        Map<String,Object> map = (Map<String,Object>)info.get("newTriple");
        Long id = Long.parseLong(String.valueOf(map.get("id")));
        String head = String.valueOf(map.get("head"));
        String headCategory = String.valueOf(map.get("head_type"));
        String relation = String.valueOf(map.get("rel"));
        String tail = String.valueOf(map.get("tail"));
        String tailCategory = String.valueOf(map.get("tail_type"));
        if(candidateTripleService.updateCandidateTripleById(id,head,relation,tail,headCategory,tailCategory)==1){
            //更新成功
            return JsonResult.success("success");
        }
        //更新失败
        return JsonResult.success("failure");
    }
    /**
     * 根据id删除某个候选图谱
     * @param info
     * @return
     */
    @PostMapping("/candidateKg/deleteKg")
    @ResponseBody
    public JsonResult deleteCandidateKGById(@RequestBody Map<String, Object> info){
        Long id = Long.parseLong(String.valueOf(info.get("id")));
        if(candidateKgService.deleteKgById(id)==1){
            //删除成功
            return JsonResult.success("success");
        }
        //删除失败
        return JsonResult.success("failure");
    }
    /**
     * 根据idc查找某个图谱的INFO
     * @param info
     * @return
     */
    @PostMapping("/candidateKg/info")
    @ResponseBody
    public JsonResult getCandidateKGInfoById(@RequestBody Map<String, Object> info){
        Long id = Long.parseLong(String.valueOf(info.get("id")));
        CandidateKGInfo kgInfo = candidateKgService.getCandiateKGInfo(id);
        return JsonResult.success("success",kgInfo);
    }
}
