package com.group.KGMS;

import com.group.KGMS.Repository.GraphNodeRepository;
import com.group.KGMS.entity.GraphNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class Neo4jTest {
    @Autowired
    GraphNodeRepository graphNodeRepository;
    @Test
    public void testCreate(){
        GraphNode node = new GraphNode();
        node.setName("测试节点");
        node.setType("测试节点属性");
        node = graphNodeRepository.save(node);
        GraphNode node2 = new GraphNode();
        node2.setName("测试节点2");
        node2.setType("测试节点属性2");
        node2 = graphNodeRepository.save(node2);
    }
    @Test
    public void findNeo4j(){
        //查询全部
        List<GraphNode> nodeList = graphNodeRepository.findAll();
        System.out.println("nodeList："+nodeList);
        //根据id查询
        Optional<GraphNode> byName = graphNodeRepository.findById("测试节点2");
        System.out.println("byName："+byName);
        //根据条件查询 需要Example对象，Example对象包含查询条件与对应条件的类型，以下查询条件字段为label,值为"测试节点"
        // ExampleMatcher.GenericPropertyMatchers.contains()代表此字段只需要包含就查出，相当于like
        GraphNode graphNode = new GraphNode();
        graphNode.setType("测试节点3");
        List<GraphNode> nodeListByLabel = graphNodeRepository.findAll(Example.of(graphNode, ExampleMatcher.matching().withMatcher(
                "type", ExampleMatcher.GenericPropertyMatchers.contains()
        )));
        System.out.println("nodeListByProperty："+nodeListByLabel);
    }
    @Test
    public void testCypher(){
//        graphNodeRepository.creatRelationByName("测试关系4");
//        GraphNode node = graphNodeRepository.creatNode("测试节点6","测试属性6");
//        if(node!=null){
//            System.out.println("success");
//        }
    }
    @Test
    public void testCypher2(){
        graphNodeRepository.creatRelationByName("约翰保罗琼斯号导弹驱逐舰","执行官","布赖恩·劳斯");
    }
}
