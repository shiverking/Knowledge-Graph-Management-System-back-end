package com.group.KGMS.util;

import com.group.KGMS.utils.CoreOWLUtil;
import org.apache.jena.ontology.OntModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.util
 * @Author: zt
 * @CreateTime: 2023-03-08  16:17
 * @Description:
 */

@SpringBootTest
public class CoreOWLUtilTest {

    @Test
    public void testReadOWL(){
        OntModel ontModel = CoreOWLUtil.owl2OntModel();
        System.out.println("ontModel = " + ontModel);
    }

}
