package com.group.KGMS.scheduled;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.group.KGMS.entity.CommonSelection;
import com.group.KGMS.entity.RequestInfo;
import com.group.KGMS.mapper.CandidateTripleMapper;
import com.group.KGMS.mapper.RequestMapper;
import com.group.KGMS.utils.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 与编目系统集成的task
 */
@Component
public class TripleSearchTask implements Runnable{
    @Autowired
    CommonSelection commonSelection;
    /**
     * 在Runnable @Autowired注入会null  所以需要手动注入
     */
    public void run() {
        //手动注入,runnable不支持自动注入
        RestTemplate restTemplate = SpringContextUtils.getApplicationContext().getBean(RestTemplate.class);
        CandidateTripleMapper candidateTripleMapper = SpringContextUtils.getApplicationContext().getBean(CandidateTripleMapper.class);
        RequestMapper requestMapper = SpringContextUtils.getApplicationContext().getBean(RequestMapper.class);
        String url = "http://"+commonSelection.getIp()+":"+commonSelection.getPort()+"/"+commonSelection.getKgInterface();
        //String url = "http://192.168.1.10:8888/ontology-model/getInstance";
        Date latestTime = requestMapper.getLatestTime();
        if(latestTime == null){
            Calendar calendar=Calendar.getInstance();
            calendar.set(2000, 0, 1); // 2000年1月1日
            latestTime=calendar.getTime();
        }
        //获取最新时刻
        Date nextTime = new Date();
        //加5分钟
//        Date nextTime = new Date(latestTime.getTime()+5*6000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
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
        requestMapper.insertNewRecord(new RequestInfo(uuid,new Date(),num,"success"));
        System.out.println("定时任务执行:" + new Date());
    }
}
