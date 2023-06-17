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
import org.springframework.data.mongodb.core.query.Update;

import java.text.SimpleDateFormat;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
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
//        Pageable pageable=PageRequest.of(0,5);
//        //创建查询对象
//        Query query = new Query();
//        query.addCriteria(
//                new Criteria().and("cid").is(2)
//        );
//        query.with(pageable);
//        System.out.println(mongoTemplate.find(query, UnstructuredTextOriginal.class));

//        return list;
                // 创建包含多个字典的列表（注意：实际代码中应该使用 Dictionary 类型）
                List<Map<String, Integer>> dictionaries = new ArrayList<>();
                Map<String, Integer> dictionary1 = new HashMap<>();
                dictionary1.put("apple", 1);
                dictionary1.put("banana", 2);
                dictionary1.put("cherry", 3);
                dictionary1.put("date", 4);
                dictionary1.put("elderberry", 5);
                dictionaries.add(dictionary1);

                Map<String, Integer> dictionary2 = new HashMap<>();
                dictionary2.put("fig", 6);
                dictionary2.put("grape", 7);
                dictionary2.put("apple", 8); // 添加重复项
                dictionaries.add(dictionary2);

                // 获取所有字典的所有键
                Set<String> keys = new HashSet<>();
                for (Map<String, Integer> dictionary : dictionaries) {
                    keys.addAll(dictionary.keySet());
                }
                // 去掉重复值
                Set<String> uniqueKeys = new HashSet<>(keys);
                // 输出所有键（去掉重复值）
                System.out.println("All keys (unique):");
                for (String key : uniqueKeys) {
                    System.out.println(key);
                }


    }
}
