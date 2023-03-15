package com.group.KGMS.util;

import com.group.KGMS.utils.OWLUtil;
import org.apache.jena.ontology.OntModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.util
 * @Author: zt
 * @CreateTime: 2023-02-28  17:29
 * @Description:
 */

@SpringBootTest
public class OWLUtilTest {

    @Autowired
    private OWLUtil owlUtil;

    @Test
    public void testOwl2OntModel(){
        OntModel ontModel = owlUtil.owl2OntModel();
        System.out.println("ontModel = " + ontModel);
    }

}
