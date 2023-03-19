package com.group.KGMS.service;

import com.group.KGMS.entity.Image;
import com.group.KGMS.entity.T_crawler;
import com.group.KGMS.entity.UnstructuredTextOriginal;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService{
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public JsonResult getimage(Integer pageNum, Integer pageSize, Integer cid)
    {
        //创建查询对象
        Query query = new Query();
        //设置起始数
        query.skip((pageNum - 1) * pageSize);
        //设置查询条数
        query.limit(pageSize);
        if(cid!=null){
            query.addCriteria(
                    Criteria.where("cid").is(cid)
            );
        }
        Query query1 = new Query();
        query1.addCriteria(
                Criteria.where("cid").is(cid)
        );
        return JsonResult.success(mongoTemplate.find(query, Image.class),mongoTemplate.count(query1,Image.class));
    }
    @Override
    public List<Map> statistic() {
//        Map<String, Long> statistic = new HashMap<String, Long>();
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("cid").count().as("总数"));
        List<Map> list = mongoTemplate.aggregate(aggregation, Image.class,
                Map.class).getMappedResults();

        return list;
    }
    @Override
    public List<String> getimagetype(Integer cid) {
        Query query = new Query();
        query.addCriteria(
                Criteria.where("cid").is(cid)
        );
        return  mongoTemplate.findDistinct(query,"type",Image.class,String.class);
    }
    @Override
    public JsonResult getimagebytype(Integer pageNum, Integer pageSize, Integer cid,String type) {
        //创建查询对象
        Query query = new Query();
        //设置起始数
        query.skip((pageNum - 1) * pageSize);
        //设置查询条数
        query.limit(pageSize);
        if(cid!=null){
            query.addCriteria(
                    Criteria.where("cid").is(cid)
            );
            query.addCriteria(
                    Criteria.where("type").is(type)
            );
        }
        Query query1 = new Query();
        query1.addCriteria(
                Criteria.where("cid").is(cid)
        );
        query1.addCriteria(
                Criteria.where("type").is(type)
        );
        return JsonResult.success(mongoTemplate.find(query, Image.class),mongoTemplate.count(query1,Image.class));
    }
}
