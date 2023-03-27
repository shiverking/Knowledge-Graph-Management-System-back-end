package com.group.KGMS.util;

import com.group.KGMS.utils.CoreOWLUtil;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.*;
import java.io.IOException;

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

    @Test
    public void testAddProperty() throws IOException {
        OntModel ontModel = CoreOWLUtil.owl2OntModel();
        OntClass ontClass = CoreOWLUtil.createClass(ontModel, "装备物资");
        CoreOWLUtil.addProperty(ontModel, ontClass, "测试一下");
    }

}
