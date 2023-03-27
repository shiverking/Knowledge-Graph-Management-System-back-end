package com.group.KGMS.service;


import com.group.KGMS.entity.Record;
import com.group.KGMS.entity.SemistructuredDataOriginal;

import com.group.KGMS.entity.T_crawler;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrawlerServiceImpl implements CrawlerService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public JsonResult findByCondition(String name, Integer status, Integer pageNum, Integer pageSize) {
        Query query = new Query();
        if(!name.isEmpty()) {
            query.addCriteria(Criteria.where("name").regex(name));
        }
        if(status != null) {
            query.addCriteria(Criteria.where("status").is(status));
        }
        //设置起始数
        query.skip((pageNum - 1) * pageSize);
        //设置查询条数
        query.limit(pageSize);
        query.fields().include("cid").include("name").include("remark").include("status").include("cron").include("website");
        BasicQuery basicQuery = new BasicQuery(query.getQueryObject().toJson());
        return JsonResult.success(mongoTemplate.find(query, T_crawler.class), mongoTemplate.count(basicQuery,T_crawler.class));
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

    @Override
    public int addCrawler(T_crawler crawler) {
        Query query = new Query();
        query.with(Sort.by(Sort.Order.desc("cid")));
        query.limit(1);
        query.fields().include("cid");
        T_crawler res = mongoTemplate.findOne(query, T_crawler.class);
        crawler.setCid(res.getCid() + 1);
        crawler.setStatus(0);
        crawler.setPath("/" + crawler.getSpider_name());
        T_crawler back = mongoTemplate.save(crawler);
        if(back != null) {
            return 1;
        }
        else{
            return 0;
        }
    }

    @Override
    public T_crawler findpathBycid(Integer cid) {
        Query query = new Query(Criteria.where("cid").is(cid));
        return mongoTemplate.findOne(query, T_crawler.class);
    }

    @Override
    public void uploadCrawlerFile(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        String storePath = "C:\\Users\\20806\\Desktop\\crawlerFile\\";

        File fileDir = new File(storePath);
        File[] files = fileDir.listFiles();
        for (File f : files) {
            if(f.getName() == filename) {
                f.delete();
            }
        }

        String path = storePath + filename;
        File filePath = new File(path);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        outputStream.write(file.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public void setcrawlstatusBycid(Integer cid,Integer status) {
        Query query = new Query(Criteria.where("cid").is(cid));
        Update update = new Update();
        update.set("status", status);
        mongoTemplate.upsert(query,update, T_crawler.class);
    }
    @Override
    public void setrecordstatusBycid(String id,Integer status) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("status", status);
        Date date=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = sdf.format(date);
        update.set("end_time", date1);
        mongoTemplate.upsert(query,update, Record.class);
    }
    @Override
    public String addrecord(Integer cid) {
        Record record = new Record();
        Date date=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = sdf.format(date);
        record.setCid(cid);
        record.setStart_time(date1);
        record.setStatus(1);
        return mongoTemplate.insert(record).get_id();
    }
    @Override
    public JsonResult findrecordbycid(Integer pageNum, Integer pageSize,Integer cid) {
        Query query = new Query(Criteria.where("cid").is(cid));
        //设置起始数
        query.skip((pageNum - 1) * pageSize);
        //设置查询条数
        query.limit(pageSize);
        BasicQuery basicQuery = new BasicQuery(query.getQueryObject().toJson());
        return JsonResult.success(mongoTemplate.find(query, Record.class),mongoTemplate.count(basicQuery, Record.class));
    }
}
