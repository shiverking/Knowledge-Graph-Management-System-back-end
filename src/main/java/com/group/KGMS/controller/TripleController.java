package com.group.KGMS.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.repository.GraphNodeRepository;
import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.entity.Triple;
import com.group.KGMS.service.*;
import com.group.KGMS.utils.JsonResult;
import com.mongodb.MongoSocketReadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @Autowired
    VersionService versionService;
    @Autowired
    GraphNodeRepository graphNodeRepository;
    @Autowired
    UntructuredTextService untructuredTextService;
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
     * 分页获取候选三元组-条件查找
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/triples/getAllCandidateTriplesConditionally")
    @ResponseBody
    public JsonResult getCandidateTriplesConditionally(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit,@RequestParam(value = "startTime",defaultValue = "null") String startTime,@RequestParam(value = "endTime",defaultValue = "null") String endTime,@RequestParam(value = "source",defaultValue = "null") String source){
        //只有source检索
        if(!source.equals("null")&&startTime.equals("null")&&endTime.equals("null")){
            PageInfo<CandidateTriple> pageInfo = candidateTripleService.getTriplesListWithSourceLimitByPage(page,limit,source);
            return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
        }
        else if(source.equals("null")&&!startTime.equals("null")&&!endTime.equals("null")){
            Date pre = new Date(Long.parseLong(startTime));
            Date next = new Date(Long.parseLong(endTime));
            PageInfo<CandidateTriple> pageInfo = candidateTripleService.getTriplesListWithTimeLimitByPage(page,limit,pre,next);
            return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
        }
        else if(!source.equals("null")&&!startTime.equals("null")&&!endTime.equals("null")){
            Date pre = new Date(Long.parseLong(startTime));
            Date next = new Date(Long.parseLong(endTime));
            PageInfo<CandidateTriple> pageInfo = candidateTripleService.getTriplesListWithSourceAnTimeLimitByPage(page,limit,pre,next,source);
            return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
        }
        PageInfo<CandidateTriple> pageInfo = candidateTripleService.getCandidateTripleByPage(page,limit);
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
        Map<Long,String> nameMap = new HashMap<>();
        List<Triple> triples = tripleService.getTripleFromSameKg(id);
        List<JSONObject> jsonList = new ArrayList<>();
        for(Triple triple:triples){
            Long kgId =triple.getId();
            String name = nameMap.get(kgId);
            if(name==null){
                name = candidateKgService.getNameById(id);
                nameMap.put(kgId,name);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",triple.getId());
            jsonObject.put("head",triple.getHead());
            jsonObject.put("relation",triple.getRelation());
            jsonObject.put("tail",triple.getTail());
            jsonObject.put("time",triple.getTime());
            jsonObject.put("status",triple.getStatus());
            jsonObject.put("candidateName",name);
            jsonList.add(jsonObject);
        }
        //返回所有三元组
        return JsonResult.success("success",jsonList);
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
        List<Integer> oldIds = (List<Integer>) info.get("selectedId");
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
            for(Integer oldId:oldIds){
                //修改isNew列
                candidateKgService.updateKgToOldById((long)oldId);
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
        List<Integer> ids = (List<Integer>) info.get("oldKgId");
        //实体对齐列表和map
        List<Map<String, Object>> mergeList = (List<Map<String, Object>>) info.get("mergeTable");
        Map<Integer,Map<String,String>> mergeMap = new HashMap<>();
        //如果存在对齐结果,记录对齐结果
        if(mergeList.size()>0){
            //k->v k为原值 v为新值
            for(Map<String, Object> map : mergeList){
                Map<String,String> tmpMap  = new HashMap<>();
                tmpMap.put("from",(String)map.get("from"));
                tmpMap.put("to",(String)map.get("to"));
                tmpMap.put("direction",(String)map.get("direction"));
                mergeMap.put((int)map.get("fromId"),tmpMap);
            }
        }
        //1保留候选图谱,目前只有这一种策略
        if(strategy==1) {
            for(int i=0;i<kg.size();i++){
                //插入操作结果
                if(kg.get(i).get("res")!=null&&kg.get(i).get("res").equals("检测不通过")){
                    kg.get(i).put("operation","忽略");
                }
                else{
                    kg.get(i).put("operation","插入");
                    //修改head_from 和 tail_from
                    Map<String,String> alignMap = mergeMap.get((int)kg.get(i).get("id"));
                    //如果有对齐记录
                    if(alignMap!=null){
                        //注意 from代表 候选图谱，to代表核心图谱
                        //修改为核心图谱中的实体
                        if(alignMap.get("direction").equals("left")){
                            //如果是头实体要修改 head_from 记录对齐前的实体
                            if(kg.get(i).get("head").equals(alignMap.get("from"))){
                                kg.get(i).put("head_from",alignMap.get("from"));
                                kg.get(i).put("head",alignMap.get("to"));
                            }
                            //如果是尾实体要修改
                            if(kg.get(i).get("tail").equals(alignMap.get("from"))){
                                kg.get(i).put("tail_from",alignMap.get("from"));
                                kg.get(i).put("tail",alignMap.get("to"));
                            }
                        }
                        //修改核心图谱中的实体
                        else if(alignMap.get("direction").equals("right")){
                            //如果是根据头实体修改
                            if(kg.get(i).get("head").equals(alignMap.get("from"))){
                                kg.get(i).put("head_from","修改核心实体:"+alignMap.get("to"));
                            }
                            //如果是根据尾实体修改
                            if(kg.get(i).get("tail").equals(alignMap.get("from"))){
                                kg.get(i).put("tail_from","修改核心实体:"+alignMap.get("to"));
                            }
                        }
                    }
                }
            }
            //修改isNew列
            for(Integer oldId:ids){
                //修改isNew列
                candidateKgService.updateKgToOldById((long)oldId);
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
    /**
     * 融合管理-图谱融合
     * 插入补全缓存记录
     * @param info
     * @return
     */
    @PostMapping("/triples/completionCoreKg")
    @ResponseBody
    public JsonResult completionCoreKg(@RequestBody Map<String, Object> info){
        List<Map<String, Object>> res = (List<Map<String, Object>>) info.get("res");
        if(cacheService.insertNewCompletionCache(res)==1){
            return JsonResult.success("success");
        }
        return JsonResult.success("failure");
    }
    /**
     * 分页获取CompletionCache
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/triples/getCompletionCacheByPage")
    @ResponseBody
    public JsonResult getCompletionCacheByPage(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageInfo<Map<String,Object>> pageInfo = cacheService.getCompletionCacheByPage(page,limit);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }
    /**
     * 融合管理-图谱融合
     * 插入补全缓存记录
     * @param info
     * @return
     */
    @PostMapping("/triples/evaluationCoreKg")
    @ResponseBody
    public JsonResult evaluationCoreKg(@RequestBody Map<String, Object> info){
        List<Map<String, Object>> res = (List<Map<String, Object>>) info.get("res");
        if(cacheService.insertNewEvaluationCache(res)==1){
            return JsonResult.success("success");
        }
        return JsonResult.error("failure");
    }
    /**
     * 分页获取EvaluationCache
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/triples/getEvaluationCacheByPage")
    @ResponseBody
    public JsonResult getEvaluationCacheByPage(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageInfo<Map<String,Object>> pageInfo = cacheService.getEvaluationCacheByPage(page,limit);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }

    /**
     * 融合管理-图谱融合
     * 插入一条新的记录
     * @param info
     * @return
     */
    @PostMapping("/version/insertNewVersion")
    @ResponseBody
    public JsonResult insertNewVersion(@RequestBody Map<String, Object> info){
        //首先获取各个修改的数量
        int mergeNumber = Integer.valueOf(String.valueOf(info.get("mergeNumber")));
        int completionNumber = Integer.valueOf(String.valueOf(info.get("completionNumber")));
        int evaluationNumber = Integer.valueOf(String.valueOf(info.get("evaluationNumber")));
        //新建一个版本并获取版本号
        String res = versionService.insertNewVersion(mergeNumber,completionNumber,evaluationNumber);
        //插入版本成功
        if(!res.equals("0")){
            boolean first = true;
            boolean second = true;
            boolean third = true;
            //开始迁移数据库
//            if(cacheService.appendNewMergeToVersion(res)==1&&cacheService.appendNewCompletionToVersion(res)==1&&cacheService.appendNewEvaluationToVersion(res)==1){
//                if(tripleService.insertAllMergeChange(merge)==1){
//                    return JsonResult.success("success");
//                }
//                return JsonResult.success("success");
//            }
            List<Map<String,Object>> mergeList = cacheService.appendNewMergeToVersion(res);
            List<Map<String,Object>> completionList = cacheService.appendNewCompletionToVersion(res);
            if(mergeList!=null&&mergeList.size()>0){
                //将其插入核心图谱
                if(tripleService.insertAllMergeChange(mergeList)!=1) {
                    first = false;
                }
            }
            if(completionList!=null&&completionList.size()>0){
                //将补全改动插入核心图谱
                if(tripleService.insertCompletionChange(completionList)!=1) {
                   second = false;
                }
            }
            if(first&&second&&third){
                return JsonResult.success("success");
            }
        }
        else{
            //回退创建版本的操作
            versionService.deleteVersionById(res);
        }
        return JsonResult.error("failure");
    }
    /**
     * 分页查找version
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/version/getVersionByPage")
    @ResponseBody
    public JsonResult getVersionByPage(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageInfo<Map<String,Object>> pageInfo = versionService.getVersionByPage(page,limit);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }
    /**
     * 时间降序分页查找version
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/version/getVersionByPageByTimeDesc")
    @ResponseBody
    public JsonResult getVersionByPageByTimeDesc(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageInfo<Map<String,Object>> pageInfo = versionService.getVersionByPageByTimeDesc(page,limit);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }
    /**
     * 分页查找version_merge
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/version/getMergeByPage")
    @ResponseBody
    public JsonResult getVersionMergeByPage(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit,@RequestParam("versionId") String versionId){
        PageInfo<Map<String,Object>> pageInfo = versionService.getMergeByPage(page,limit,versionId);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }
    /**
     * 分页查找version_completion
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/version/getCompletionByPage")
    @ResponseBody
    public JsonResult getVersionCompletionByPage(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit,@RequestParam("versionId") String versionId){
        PageInfo<Map<String,Object>> pageInfo = versionService.getCompletionByPage(page,limit,versionId);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }
    /**
     * 分页查找version_evalution
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/version/getEvaluationByPage")
    @ResponseBody
    public JsonResult getVersionEvaluationByPage(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit,@RequestParam("versionId") String versionId){
        PageInfo<Map<String,Object>> pageInfo = versionService.getEvaluationByPage(page,limit,versionId);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }
    /**
     * 删除所有缓存记录
     * @return
     */
    @PostMapping("/version/deleteAllCache")
    @ResponseBody
    public JsonResult deleteAllCache(){
        cacheService.deleteAll();
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success");
    }
    /**
     * 同步所有未同步的version
     * @return
     */
    @PostMapping("/version/synchronize")
    @ResponseBody
    public JsonResult synchronization(){
        versionService.synchronizeVersion();
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success");
    }
    /**
     * 同步所有未同步的version
     * @return
     */
    @PostMapping("/coredata/briefInformation")
    @ResponseBody
    public JsonResult briefInformation(){
        String nodeNum = String.valueOf(graphNodeRepository.getEntityNum());
        String entityNum = String.valueOf(graphNodeRepository.getEntityNum());
        String relationNum = String.valueOf(graphNodeRepository.getRelationNum());
        String latest = versionService.getLatestTimeOfVersion();
        String updateTimes = String.valueOf(versionService.getNumOfVersion());
        String childKg = "0";
        Map<String,String> map = new HashMap<>();
        map.put("nodeNum",nodeNum);
        map.put("relationNum",relationNum);
        map.put("latest",latest);
        map.put("updateTimes",updateTimes);
        map.put("entityNum",entityNum);
        map.put("childKg",childKg);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",map);
    }
    /**
     * 非结构数据抽取-结果保存
     * 将抽取结果保存到候选三元组数据库
     * @param info
     * @return
     */
    @PostMapping("/candidate/saveExtraction")
    @ResponseBody
    public JsonResult saveExtractionResult(@RequestBody Map<String, Object> info){
        List<Map<String, Object>> extractResult = (List<Map<String, Object>>) info.get("data");
        List<String> idList = (List<String>) info.get("ids");
        List<CandidateTriple> candidateTripleList = new ArrayList<>();
        //如果没有抽取结果，直接修状态即可
        if(extractResult.size()==0){
            untructuredTextService.updateUnstructuredTextStatusById(idList);
            return JsonResult.success("success");
        }
        for(int i=0;i<extractResult.size();i++){
            CandidateTriple candidateTriple = new CandidateTriple();
            candidateTriple.setHead((String) extractResult.get(i).get("head"));
            candidateTriple.setHeadCategory((String) extractResult.get(i).get("head_typ"));
            candidateTriple.setRelation((String) extractResult.get(i).get("rel"));
            candidateTriple.setTail((String) extractResult.get(i).get("tail"));
            candidateTriple.setTailCategory((String) extractResult.get(i).get("tail_typ"));
            candidateTriple.setTime(new Date());
            candidateTriple.setStatus("未入库");
            candidateTriple.setSource("文本抽取");
            candidateTripleList.add(candidateTriple);
        }
        if(candidateTripleService.insertNewCandidateTriplesBatch(candidateTripleList)==1){
            int maxRetryTimes = 4;
            //重试4次
            for (int retry = 1; retry <= maxRetryTimes; retry++) {
                try {
                    untructuredTextService.updateUnstructuredTextStatusById(idList);
                    return JsonResult.success("success");
                } catch(MongoSocketReadException e){
                    System.out.println("出错,将在1秒后重试");
                }
                // 延时一秒
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return JsonResult.error("failure");
    }
}
