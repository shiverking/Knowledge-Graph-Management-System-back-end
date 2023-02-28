package com.group.KGMS.mapper;

import com.group.KGMS.entity.CandidateOntologyClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.mapper
 * @Author: zt
 * @CreateTime: 2023-02-28  15:42
 * @Description:
 */

@SpringBootTest
public class MapperTest {

    @Autowired
    private CandidateOntologyClassMapper candidateOntologyClassMapper;

    @Test
    public void testInsert(){
        CandidateOntologyClass newClass = new CandidateOntologyClass("测试数据",1);
        int result = candidateOntologyClassMapper.insert(newClass);
        System.out.println("result = " + result);
    }

}
