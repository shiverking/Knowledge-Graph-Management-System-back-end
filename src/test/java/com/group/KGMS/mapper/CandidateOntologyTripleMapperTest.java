package com.group.KGMS.mapper;

import com.group.KGMS.entity.CandidateOntologyTriple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.mapper
 * @Author: zt
 * @CreateTime: 2023-03-06  23:59
 * @Description:
 */

@SpringBootTest
public class CandidateOntologyTripleMapperTest {

    @Autowired
    private CandidateOntologyTripleMapper candidateOntologyTripleMapper;

    @Test
    public void testSelect(){
        CandidateOntologyTriple result = candidateOntologyTripleMapper.selectById(1);
        System.out.println("result = " + result);
    }

}
