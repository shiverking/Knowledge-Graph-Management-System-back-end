package com.group.KGMS.service;

import com.group.KGMS.entity.CandidateOntologyTriple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-03-07  00:38
 * @Description:
 */

@SpringBootTest
public class CandidateOntologyClassTripleServiceTest {

    @Autowired
    private CandidateOntologyTripleService candidateOntologyTripleService;

    @Test
    public void testGetAll(){
        List<CandidateOntologyTriple> list = candidateOntologyTripleService.getAllRelationByCandidateOntologyId(1);
        list.forEach(System.out::println);
    }

}
