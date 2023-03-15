package com.group.KGMS.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-03-09  12:45
 * @Description:
 */

@SpringBootTest
public class CoreOntologyTripleServiceTest {

    @Autowired
    private CoreOntologyTripleService coreOntologyTripleService;

    @Test
    public void testSaveRelation() throws Exception {
        coreOntologyTripleService.saveRelation("a_c", "r_d", "a_c_a");
    }

    @Test
    public void testRemoveRelation() throws IOException {
        coreOntologyTripleService.removeRelation(3, "r_e", 10);
    }

}
