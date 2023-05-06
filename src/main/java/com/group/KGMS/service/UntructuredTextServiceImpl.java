package com.group.KGMS.service;

import com.group.KGMS.entity.UnstructuredText;
import com.group.KGMS.entity.UnstructuredTextOriginal;
import com.group.KGMS.mapper.UnstructuredTextMapper;
import com.group.KGMS.repository.UnstructuredTextRepository;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UntructuredTextServiceImpl implements UntructuredTextService {
    @Autowired
    UnstructuredTextMapper unstructuredTextMapper;
    @Resource
    private MongoTemplate mongoTemplate;
    @Autowired
    UnstructuredTextRepository unstructuredTextRepository;

    @Override
    public void updateUnstructuredTextStatusById(List<String> idList) {
        for (String id : idList) {
            Query query = new Query(Criteria.where("_id").is(id));
            Update update = new Update().set("status", "已抽取");
            mongoTemplate.updateFirst(query, update, "unstructured_text_translate");
        }
    }

    @Override
    public List<UnstructuredText> getUnstructuredTextByPage(Integer pageNum, Integer pageSize) {
        //创建查询对象
        Query query = new Query();
        //设置起始数
        query.skip((pageNum - 1) * pageSize);
        //设置查询条数
        query.limit(pageSize);
//        如果有条件的话
//        if (conditions != null) {
//            for (String field: conditions.keySet()) {
//                query.addCriteria(new Criteria(field).is(conditions.get(field)));
//            }
//        }
        return mongoTemplate.find(query, UnstructuredText.class);
    }
    @Override
    public JsonResult getUnstructuredTextByPageandcid(Integer pageNum, Integer pageSize, Integer cid) {
        //创建查询对象
        Query query = new Query();
        if(cid!=null){
            query.addCriteria(
                    Criteria.where("cid").is(cid)
            );
        }
        //设置起始数
        query.skip((pageNum - 1) * pageSize);
        //设置查询条数
        query.limit(pageSize);
        BasicQuery basicQuery = new BasicQuery(query.getQueryObject().toJson());
        return JsonResult.success(mongoTemplate.find(query, UnstructuredTextOriginal.class),mongoTemplate.count(basicQuery,UnstructuredTextOriginal.class));
    }
    @Override
    public JsonResult getAllTextBytitle(Integer pageNum, Integer pageSize, String title) {
        //创建查询对象
        Query query = new Query();
        if(title!=null){
            query.addCriteria(
                    Criteria.where("title").regex(title)
            );
        }
        //设置起始数
        query.skip((pageNum - 1) * pageSize);
        //设置查询条数
        query.limit(pageSize);
        BasicQuery basicQuery = new BasicQuery(query.getQueryObject().toJson());
        return JsonResult.success(mongoTemplate.find(query, UnstructuredTextOriginal.class),mongoTemplate.count(basicQuery,UnstructuredTextOriginal.class));
    }
    @Override
    public Long getSumOfUnstructuredText() {
        return unstructuredTextRepository.count();
    }
}
