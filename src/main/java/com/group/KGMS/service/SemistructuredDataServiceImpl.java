package com.group.KGMS.service;

import com.group.KGMS.entity.SemistructuredDataOriginal;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(cid!=null){
            query.addCriteria(
                    Criteria.where("cid").is(cid)
            );
        }
        BasicQuery basicQuery = new BasicQuery(query.getQueryObject().toJson());
//        如果有条件的话
//        if (conditions != null) {
//            for (String field: conditions.keySet()) {
//                query.addCriteria(new Criteria(field).is(conditions.get(field)));
//            }
//        }
        return JsonResult.success(mongoTemplate.find(query, SemistructuredDataOriginal.class),mongoTemplate.count(basicQuery,SemistructuredDataOriginal.class));

    }

}
