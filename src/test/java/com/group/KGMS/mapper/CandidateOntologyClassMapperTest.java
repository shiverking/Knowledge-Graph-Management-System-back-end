package com.group.KGMS.mapper;

import com.group.KGMS.entity.CandidateOntologyClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.mapper
 * @Author: zt
 * @CreateTime: 2023-02-28  15:42
 * @Description:
 */

@SpringBootTest
public class CandidateOntologyClassMapperTest {

    @Autowired
    private CandidateOntologyClassMapper candidateOntologyClassMapper;

    @Test
    public void testInsert(){
        CandidateOntologyClass newClass = new CandidateOntologyClass("测试数据",1);
        int result = candidateOntologyClassMapper.insert(newClass);
        System.out.println("result = " + result);
    }

    @Test
    public void testDelete(){
        int result = candidateOntologyClassMapper.deleteById(9);
        System.out.println("result = " + result);
    }

    @Test
    public void testUpdate(){
        CandidateOntologyClass updateClass = new CandidateOntologyClass(8, "test", 1);
        int result = candidateOntologyClassMapper.updateById(updateClass);
        System.out.println("result = " + result);
    }

    @Test
    public void testSelectById(){
        CandidateOntologyClass result = candidateOntologyClassMapper.selectById(8);
        System.out.println("result = " + result);
    }

    @Test
    public void testSelectAll(){
        List<CandidateOntologyClass> list = candidateOntologyClassMapper.selectList(null);
        list.forEach(System.out::println);
    }
}
