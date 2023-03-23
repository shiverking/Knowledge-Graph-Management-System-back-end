package com.group.KGMS.controller;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.group.KGMS.entity.RequestInfo;
import com.group.KGMS.mapper.CandidateTripleMapper;
import com.group.KGMS.mapper.RequestMapper;
import com.group.KGMS.service.TaskSchedulerService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class TaskSchedulerController {
    @Autowired
    private TaskSchedulerService taskSchedulerService;
    @Resource
    private RestTemplate restTemplate;
    @Autowired
    CandidateTripleMapper candidateTripleMapper;
    @Autowired
    RequestMapper requestMapper;

    @ResponseBody
    @PostMapping("/startTask")
    public JsonResult startTask(@RequestParam("interval")int interval){
        //先立刻请求一次更新到最新
        manualUpdate();
        taskSchedulerService.startTask(interval);
        return JsonResult.success("success");
    }
    @ResponseBody
    @PostMapping("/stopTask")
    public JsonResult stopTask() {
        taskSchedulerService.stopTask();
        return JsonResult.success("success");
    }

    /**
     * 近期请求记录
     * @return
     */
    @ResponseBody
    @PostMapping("/recentRecords")
    public JsonResult recentRecords() {
        List<RequestInfo> requestInfos = requestMapper.getLastest5Records();
        return JsonResult.success(requestInfos);
    }

    /**
     * 手动更新编目数据系统数据到最新时刻
     * @return
     */
    @ResponseBody
    @PostMapping("/manualUpdate")
    public JsonResult manualUpdate() {
        String url = "http://127.0.0.1:8088/test";
        Date latestTime = requestMapper.getLatestTime();
        //获取最新时刻
        Date nextTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        //拼接请求发送
        String str = restTemplate.getForObject(url + "?startDate="+sdf.format(latestTime)+"&endDate="+sdf.format(nextTime), String.class);
        JSONObject object = JSONObject.parseObject(str);
        int num1 = (int)object.get("attributesNum");
        int num2 = (int)object.get("relationsNum");
        int num = num1+num2;
        if(num2>0){
            JSONArray relations = JSONArray.parse(object.get("relations").toString());
            for(int i=0;i<relations.size();i++){
                JSONObject json = JSONObject.parseObject(relations.get(i).toString());
                candidateTripleMapper.insertCandidateTriples((String)json.get("startEntity"),(String)json.get("relationName"),
                        (String)json.get("endEntity"),(String)json.get("startClass"),(String)json.get("endClass"),"未入库","编目系统",new Date());
            }
        }
        if(num1>0){
            JSONArray attributes = JSONArray.parse(object.get("attributes").toString());
            for(int i=0;i<attributes.size();i++){
                JSONObject json = JSONObject.parseObject(attributes.get(i).toString());
                candidateTripleMapper.insertCandidateAttributes((String) json.get("entityName"), (String) json.get("entityClass"),(String) json.get("attributeName"),(String) json.get("attributeValue"),"编目系统",new Date());
            }
        }
        String uuid = UUID.randomUUID().toString();
        if(requestMapper.insertNewRecord(new RequestInfo(uuid,new Date(),num,"success"))==1) {
            return JsonResult.success("success",nextTime);
        }
        return JsonResult.error();
    }
    /**
     * 最近请求时间
     * @return
     */
    @ResponseBody
    @PostMapping("/getLatestUpdateTime")
    public JsonResult getLatestUpdateTime() {
        Date latest = requestMapper.getLatestTime();
        return JsonResult.success("success",latest);
    }
    /**
     * 最近请求信息
     * @return
     */
    @ResponseBody
    @PostMapping("/getLatestUpdateRecords")
    public JsonResult getLatestUpdateInfo() {
        List<RequestInfo> requestInfos = requestMapper.getLastest5Records();
        List<Map<String,String>> mapList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RequestInfo info : requestInfos){
            Map<String,String> map = new HashMap<>();
            map.put("time",sdf.format(info.getTime()));
            map.put("num",info.getNum().toString());
            map.put("result",info.getResult());
            mapList.add(map);
        }
        return JsonResult.success("success",mapList);
    }
    /**
     * 最近请求信息
     * @return
     */
    @ResponseBody
    @PostMapping("/getLatestProgrammeStatus")
    public JsonResult getLatestProgrammeStatus() {
        boolean programmeStatus = taskSchedulerService.taskStatus();
        return JsonResult.success("success",programmeStatus);
    }
}
