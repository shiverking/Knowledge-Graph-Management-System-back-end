package com.group.KGMS;

import com.group.KGMS.entity.UnstructuredText;
import com.group.KGMS.repository.UnstructuredTextRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MongoDBTest {
    @Autowired
    UnstructuredTextRepository unstructuredTextRepository;
    @Test
    public void getAllText(){
//          System.out.println(unstructuredTextRepository);
        List<UnstructuredText> textList = unstructuredTextRepository.findAll();
        for(int i=0;i<10;i++){
            System.out.println(textList.get(i));
        }
    }
}
