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
    public void testSave() throws Exception {
        candidateOntologyClassService.save("类别1的子类的子类2", 2, 1);
    }

    @Test
    public void testRemove(){
        try {
            candidateOntologyClassService.remove("类别1的子类", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAll(){
        candidateOntologyClassService.getAllClassByCandidateOntologyId(1);
    }

}
