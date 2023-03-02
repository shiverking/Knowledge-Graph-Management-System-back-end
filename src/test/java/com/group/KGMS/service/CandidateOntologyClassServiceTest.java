package com.group.KGMS.service;

import com.group.KGMS.entity.CandidateOntologyClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-03-02  15:03
 * @Description:
 */

@SpringBootTest
public class CandidateOntologyClassServiceTest {

    @Autowired
    private CandidateOntologyClassService candidateOntologyClassService;

    @Test
    public void testGetRootClassByCandidateOntologyId(){
        CandidateOntologyClass rootClass = candidateOntologyClassService.getRootClassByCandidateOntologyId(1);
        System.out.println("rootClass = " + rootClass);
    }

    @Test
    public void testSave() throws IOException {
        candidateOntologyClassService.save("测试数据", 1, 1);
    }

}
