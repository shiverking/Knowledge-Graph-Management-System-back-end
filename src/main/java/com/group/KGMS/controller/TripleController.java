package com.group.KGMS.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateKG;
import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.entity.Entity;
import com.group.KGMS.entity.Triple;
import com.group.KGMS.service.*;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    CandidateKgService candidateKgService;
    @Autowired
    CacheService cacheService;
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
        Long candidateId = Long.parseLong(String.valueOf(info.get("id")));
        List<Map<String, Object>> list = (List<Map<String, Object>>) info.get("triples");
        List<CandidateTriple> candidateTripleList = new ArrayList<CandidateTriple>();
        for(int i=0;i<list.size();i++){
            //设置candidateTriples用于删除
            CandidateTriple candidateTriple  = new CandidateTriple();
            candidateTriple.setId(Long.parseLong(String.valueOf(list.get(i).get("id"))));
            candidateTriple.setHead((String) list.get(i).get("head"));
            candidateTriple.setHeadCategory((String) list.get(i).get("headCategory"));
            candidateTriple.setRelation((String) list.get(i).get("relation"));
            candidateTriple.setTail((String) list.get(i).get("tail"));
            candidateTriple.setTailCategory((String) list.get(i).get("tailCategory"));
            candidateTripleList.add(candidateTriple);
        }
        if(tripleService.insertIntoTriplesFromCandidateTriple(candidateTripleList,candidateId)==1){
            return JsonResult.success("success");
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
    /**
     * 候选图谱管理-候选图谱融合
     * 处理融合的函数
     * @param info
     * @return
     */
    @PostMapping("/triples/mergeKg")
    @ResponseBody
    public JsonResult mergeKg(@RequestBody Map<String, Object> info){
        int strategy = Integer.valueOf(String.valueOf(info.get("strategy")));
        List<Map<String, Object>> targetKg = (List<Map<String, Object>>) info.get("targetKg");
        List<Map<String, Object>> fromKg = (List<Map<String, Object>>) info.get("fromKg");
        //融合进老图谱
        if(strategy==1){
            Long oldKgId = Long.parseLong(String.valueOf(info.get("oldKgId")));
            List<Long> ids = new ArrayList<Long>();
            List<Long> fromKgIds = new ArrayList<>();
            for(int i=0;i<fromKg.size();i++){
                ids.add(Long.parseLong(String.valueOf(fromKg.get(i).get("id"))));
                Long candidateKgId = Long.parseLong(String.valueOf(fromKg.get(i).get("candidateId")));
                if(!fromKgIds.contains(candidateKgId)){
                    fromKgIds.add(candidateKgId);
                }
            }
            if(tripleService.updateTriplesCandidateId(ids,oldKgId)==1){
                //删除候选图谱
                for(Long id :fromKgIds){
                    if(candidateKgService.deleteKgById(id)==0){
                        return JsonResult.success("failure");
                    }
                }
                return JsonResult.success("success");
            }
        }
        //融合成新图谱且保留目标图谱和候选图谱
        else if(strategy==2){
            String newKgName = String.valueOf(info.get("newKgName"));
            String newKGComment = String.valueOf(info.get("newKgComment"));
            //构建一个新的图谱
            Long id = candidateKgService.insertNewKG(newKgName,"-" ,new Date(), new Date(),"已建立",newKGComment);
            //暂时不处理实体冲突
            List<Triple> list = new ArrayList<Triple>();
            //将两个图谱的内容复制进新图谱
            for(int i=0;i<targetKg.size();i++){
                //设置candidateTriples用于删除
                Triple triple = new Triple();
                triple.setId(Long.parseLong(String.valueOf(targetKg.get(i).get("id"))));
                triple.setHead((String) targetKg.get(i).get("head"));
                triple.setRelation((String) targetKg.get(i).get("relation"));
                triple.setTail((String) targetKg.get(i).get("tail"));
                triple.setTime(new Date());
                triple.setCandidateId(id);
                triple.setStatus("已入库");
                list.add(triple);
            }
            for(int i=0;i<fromKg.size();i++){
                //设置candidateTriples用于删除
                Triple triple = new Triple();
                triple.setId(Long.parseLong(String.valueOf(fromKg.get(i).get("id"))));
                triple.setHead((String) fromKg.get(i).get("head"));
                triple.setRelation((String) fromKg.get(i).get("relation"));
                triple.setTail((String) fromKg.get(i).get("tail"));
                triple.setTime(new Date());
                triple.setCandidateId(id);
                triple.setStatus("已入库");
                list.add(triple);
            }
            if(tripleService.insertIntoTriplesFromExistsKg(list)==1) {
                return JsonResult.success("success");
            }
        }
        return JsonResult.success("failure");
    }
    /**
     * 融合管理-图谱融合
     * @param info
     * @return
     */
    @PostMapping("/triples/mergeCoreKg")
    @ResponseBody
    public JsonResult mergeCoreKg(@RequestBody Map<String, Object> info){
        int strategy = Integer.valueOf(String.valueOf(info.get("strategy")));
        List<Map<String, Object>> kg = (List<Map<String, Object>>) info.get("kg");
//        List<String> ids = (List<String>) info.get("oldKgId");
        //1不保留候选图谱,2保留候选图谱
        if(strategy==2) {
            for(int i=0;i<kg.size();i++){
                if(kg.get(i).get("res").equals("检测不通过")){
                    kg.get(i).put("operation","忽略");
                }
                else if(kg.get(i).get("res").equals("检测通过")){
                    kg.get(i).put("operation","插入");
                }
            }
            if(cacheService.insertNewMergeCache(kg)==1){
                return JsonResult.success("success");
            }
        }
        return JsonResult.success("failure");
    }
    /**
     * 分页获取MergeCache
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/triples/getMergeCacheByPage")
    @ResponseBody
    public JsonResult getMergeCacheByPage(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageInfo<Map<String,Object>> pageInfo = cacheService.getMergeCacheByPage(page,limit);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }
}
