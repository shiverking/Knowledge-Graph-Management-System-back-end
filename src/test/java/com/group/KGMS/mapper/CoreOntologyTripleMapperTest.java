package com.group.KGMS.mapper;

import com.group.KGMS.entity.CoreOntologyTriple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.mapper
 * @Author: zt
 * @CreateTime: 2023-03-09  00:53
 * @Description:
 */

@SpringBootTest
public class CoreOntologyTripleMapperTest {

    @Autowired
    private CoreOntologyTripleMapper coreOntologyTripleMapper;

    @Test
    public void testSelect(){
        List<CoreOntologyTriple> list = coreOntologyTripleMapper.selectList(null);
        list.forEach(System.out::println);
    }

}
