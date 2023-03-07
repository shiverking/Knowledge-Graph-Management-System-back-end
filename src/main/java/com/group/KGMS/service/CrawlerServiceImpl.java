package com.group.KGMS.service;

import com.group.KGMS.entity.T_crawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrawlerServiceImpl implements CrawlerService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<T_crawler> findAll() {
        Query query = new Query();
        query.fields().include("cid").include("name").include("remark").include("status");
        return mongoTemplate.find(query, T_crawler.class);
    }

    @Override
    public List<T_crawler> findByStatus(int status) {
        Query query = new Query(Criteria.where("status").is(status));
        return mongoTemplate.find(query, T_crawler.class);
    }

    @Override
    public List<T_crawler> findByName(String name) {
        Query query = new Query(Criteria.where("name").regex(name));
        return mongoTemplate.find(query, T_crawler.class);
    }

    @Override
    public Map<String, Long> statistic() {
        Map<String, Long> statistic = new HashMap<String, Long>();
        Query query = new Query(Criteria.where("status").is(0));
        Long normal = mongoTemplate.count(query, T_crawler.class);
        query = new Query(Criteria.where("status").is(1));
        Long running = mongoTemplate.count(query, T_crawler.class);
        query = new Query(Criteria.where("status").is(-1));
        Long abnormal = mongoTemplate.count(query, T_crawler.class);
        statistic.put("正常", normal);
        statistic.put("运行中", running);
        statistic.put("异常", abnormal);
        return statistic;
    }
}
