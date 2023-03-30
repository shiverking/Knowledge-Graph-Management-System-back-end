package com.group.KGMS.service;

import com.group.KGMS.entity.CoreOntologyClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-03-08  15:55
 * @Description:
 */

@SpringBootTest
public class CoreOntologyClassServiceTest {

    @Autowired
    private CoreOntologyClassService coreOntologyClassService;

    @Test
    public void testGetRootClass(){
        CoreOntologyClass result = coreOntologyClassService.getRootClass();
        System.out.println("result = " + result);
    }

    @Test
    public void testSave() throws IOException {
//        coreOntologyClassService.save("a_c", 2);
    }

    @Test
    public void testRemove() throws Exception {
        coreOntologyClassService.remove("a_b_a");
    }

    @Test
    public void testGetBeMergedClass(){
        coreOntologyClassService.getBeMergedClass(12,1);
    }

    @Test
    public void testMerge() throws IOException {
        coreOntologyClassService.merge(10, 1, 1);
    }

}
