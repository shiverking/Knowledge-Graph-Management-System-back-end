package com.group.KGMS.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.entity.Entity;
import com.group.KGMS.entity.Triple;
import com.group.KGMS.service.CandidateTripleService;
import com.group.KGMS.service.EntityService;
import com.group.KGMS.service.RelationService;
import com.group.KGMS.service.TripleService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class TripleController {
    @Autowired
    CandidateTripleService candidateTripleService;
    @Autowired
    TripleService tripleService;
    @Autowired
    RelationService relationService;
    @Autowired
    EntityService entityService;
    /**
     * 分页获取候选三元组
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/triples/getAllCandidateTriples")
    @ResponseBody
    public JsonResult getCandidateTriples(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageInfo<CandidateTriple> pageInfo = candidateTripleService.getCandidateTripleByPage(page,limit);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }
    /**
     * 将选中的三元组入库
     * @param info
     * @return
     */
    @PostMapping("/triples/insertTriples")
    @ResponseBody
    public JsonResult insertTriples(@RequestBody Map<String, Object> info){
        boolean flag  = true;
        Long candidateId = Long.parseLong(String.valueOf(info.get("id")));
        List<Map<String, Object>> list = (List<Map<String, Object>>) info.get("triples");
        List<Triple> triples = new ArrayList<Triple>();
        List<CandidateTriple> candidateTripleList = new ArrayList<CandidateTriple>();
        for(int i=0;i<list.size();i++){
            Triple triple  = new Triple();
            triple.setHead((String) list.get(i).get("head"));
            triple.setTail((String) list.get(i).get("tail"));
            triple.setRelation((String) list.get(i).get("relation"));
            triple.setTime(new Date());
            triple.setCandidateId(candidateId);
            triple.setStatus((String) list.get(i).get("status"));
            triples.add(triple);
            //设置候选Triple用于删除
            CandidateTriple candidateTriple  = new CandidateTriple();
            candidateTriple.setId(Long.parseLong(String.valueOf(list.get(i).get("id"))));
            candidateTriple.setHead((String) list.get(i).get("head"));
            candidateTriple.setHeadCategory((String) list.get(i).get("headCategory"));
            candidateTriple.setTail((String) list.get(i).get("tail"));
            candidateTriple.setTailCategory((String) list.get(i).get("tailCategory"));
            candidateTripleList.add(candidateTriple);
        }
        if(tripleService.insertIntoTriplesFromCandidateTriple(triples,candidateId)==1){
            //加入实体库
            if(entityService.insertNewEntity(candidateTripleList)!=1){
                flag = false;
            }
            //加入关系库
            if(relationService.insertNewRelation(triples)!=1){
                flag = false;
            }
            //删除
            if(tripleService.deleteCandidateTriples(candidateTripleList)!=1){
                flag = false;
            }
            if(flag){
                return JsonResult.success("success");
            }
        }
        return JsonResult.success("failure");
    }
    /**
     * 分页获取来自相同图谱的候选三元组
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/triples/getTriplesFromSameKg")
    @ResponseBody
    public JsonResult getTriplesFromSameKg(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit,@RequestParam("id") Long id){
        PageInfo<Triple> pageInfo = tripleService.getTripleFromSameKgByPage(page,limit,id);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }
    /**
     * （不分页）获取来自相同图谱的候选三元组
     * @param id
     * @return
     */
    @PostMapping("/triples/getTriplesFromSameKgNotByPage")
    @ResponseBody
    public JsonResult getTriplesFromSameKgNotByPage(@RequestParam("id") Long id){
        List<Triple> triples = tripleService.getTripleFromSameKg(id);
        //返回所有三元组
        return JsonResult.success("success",triples);
    }
    /**
     * 分页获取所有三元组
     * @param limit
     * @param page
     * @return
     */
    @PostMapping("/triples/getAllTriplesByPage")
    @ResponseBody
    public JsonResult getAllTriplesByPage(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageInfo<Triple> pageInfo = tripleService.getTripleByPage(page,limit);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }
}
