package com.group.KGMS;

import com.group.KGMS.entity.Record;
import com.group.KGMS.entity.UnstructuredText;
import com.group.KGMS.repository.UnstructuredTextRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    public void insertrecord(){
        Record record = new Record();
        Date date=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = sdf.format(date);
        record.setCid(1);
        record.setStart_time(date1);
        record.setStatus(1);
        System.out.println(mongoTemplate.insert(record).get_id());
    }
}
