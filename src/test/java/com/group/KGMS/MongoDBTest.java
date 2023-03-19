package com.group.KGMS;

import com.group.KGMS.entity.Image;
import com.group.KGMS.entity.Record;
import com.group.KGMS.entity.UnstructuredText;
import com.group.KGMS.entity.UnstructuredTextOriginal;
import com.group.KGMS.repository.UnstructuredTextRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MongoDBTest {
    @Autowired
    UnstructuredTextRepository unstructuredTextRepository;
    @Autowired
    MongoTemplate mongoTemplate;
    @Test
    public void getAllText(){
//          System.out.println(unstructuredTextRepository);
        List<UnstructuredText> textList = unstructuredTextRepository.findAll();
        for(int i=0;i<10;i++){
            System.out.println(textList.get(i));
        }
    }

    @Test
    public void findDistinct(){
        Pageable pageable=PageRequest.of(0,5);
        //创建查询对象
        Query query = new Query();
        query.addCriteria(
                new Criteria().and("cid").is(2)
        );
        query.with(pageable);
        System.out.println(mongoTemplate.find(query, UnstructuredTextOriginal.class));
//        return list;
    }
}
