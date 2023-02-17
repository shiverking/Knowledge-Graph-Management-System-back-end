package com.group.KGMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

/**
 * @Description:neo4j 节点类
 */


@Node("node")
@Data
@NoArgsConstructor
public class GraphNode{
    @Id
    @Property(name = "name")
    private String name;
    @Property(name = "type")
    private String type;
    @Relationship(direction = Relationship.Direction.OUTGOING)
    private List<GraphNode> graphNodesList;
}
