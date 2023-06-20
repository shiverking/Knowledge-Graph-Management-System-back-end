package com.group.KGMS.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.entity.SemistructuredDataOriginal;
import com.group.KGMS.translate.BaiduTranslate;
import com.group.KGMS.translate.TransApi;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
@Service
public class SemistructuredDataServiceImpl implements SemistructuredDataService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public JsonResult getSemistructuredDataBycid(Integer pageNum, Integer pageSize, Integer cid) {
        //创建查询对象
        Query query = new Query();
        Criteria criteria= new Criteria();
        //设置起始数
        query.skip((pageNum - 1) * pageSize);
        //设置查询条数
        query.limit(pageSize);
        if(cid!=0){
            query.addCriteria(
                    Criteria.where("cid").is(cid)
            );
        }
        BasicQuery basicQuery = new BasicQuery(query.getQueryObject().toJson());
        return JsonResult.success(mongoTemplate.find(query, SemistructuredDataOriginal.class),mongoTemplate.count(basicQuery,SemistructuredDataOriginal.class));

    }
    @Override
    public JsonResult getSemistructuredDataByname(Integer pageNum, Integer pageSize, String name){
        //创建查询对象
        Query query = new Query();
        Criteria criteria= new Criteria();
        //设置起始数
        query.skip((pageNum - 1) * pageSize);
        //设置查询条数
        query.limit(pageSize);
        if(name!=null){
            query.addCriteria(
                    Criteria.where("name").regex(name)
            );
        }
        BasicQuery basicQuery = new BasicQuery(query.getQueryObject().toJson());
        return JsonResult.success(mongoTemplate.find(query, SemistructuredDataOriginal.class),mongoTemplate.count(basicQuery,SemistructuredDataOriginal.class));
    };
    @Override
    public List<CandidateTriple> converttotriples(Map<String, Object> semidata){
        List<CandidateTriple> triples = new ArrayList<>();
//        List<SemistructuredDataOriginal> sdata =(List<SemistructuredDataOriginal>) semidata.get("tableData");
        Object objectData =semidata.get("tableData");
        // 使用 Jackson 进行转换
        ObjectMapper objectMapper = new ObjectMapper();
        List<SemistructuredDataOriginal> sdata = objectMapper.convertValue(objectData, new TypeReference<List<SemistructuredDataOriginal>>() {});
        String yn = (String) semidata.get("translate");
        for (SemistructuredDataOriginal data : sdata) {
            LocalDateTime now = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(now);
            String subject = data.getName();
            if (yn.equals("Y")){
                BaiduTranslate baidutranslate = new BaiduTranslate();
                subject = (baidutranslate.getresult(subject)==null) ? subject : baidutranslate.getresult(subject);
            }
            Class<?> clazz = data.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (!field.getName().equals("name") && !field.getName().equals("_id") && !field.getName().equals("cid") && !field.getName().equals("create_time") && !field.getName().equals("origin")) {
                    try {
                        String value = String.valueOf(field.get(data));
                        if (!value.equals("null")){
                            CandidateTriple triple = new CandidateTriple(null,subject, field.getName(), value,timestamp,null,"value","未入库",data.getOrigin());
                            if (yn.equals("N")) {
                                triples.add(triple);
                            }
                            if (yn.equals("Y")){
                                BaiduTranslate baidutranslate = new BaiduTranslate();
                                String relation = (baidutranslate.getresult(field.getName())==null) ? field.getName() : baidutranslate.getresult(field.getName());
                                String object = (baidutranslate.getresult(value)==null) ? value : value ;
                                triple = new CandidateTriple(null,subject, relation, object,timestamp,null,"value","未入库",data.getOrigin());
                                triples.add(triple);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return triples;
    };
    @Override
    public void updateSemistructuredDataStatusById(List<String> idList) {
        for (String id : idList) {
            Query query = new Query(Criteria.where("_id").is(id));
            Update update = new Update().set("status", "已抽取");
            mongoTemplate.updateFirst(query, update, "semistructured_data_original");
//            mongoTemplate.updateFirst(query, update, "unstructured_text_original");
        }
    }

}
