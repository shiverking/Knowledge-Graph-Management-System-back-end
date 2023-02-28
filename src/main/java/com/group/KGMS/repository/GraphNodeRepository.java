package com.group.KGMS.repository;
import com.group.KGMS.entity.GraphNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface GraphNodeRepository extends Neo4jRepository<GraphNode,String> {
    //删除所有节点
    @Query("MATCH (n) detach delete(n)")
    int deleteDatabase();
    //获取一共有多少个节点
    @Query("MATCH (n) RETURN count(n)")
    int getEntityNum();
    //获取一共有多少个节点
    @Query("MATCH ()-[r]-() RETURN count(r)/2")
    int getRelationNum();
    //根据两个节点名建立一条关系
    @Query("MATCH (a),(b) WHERE a.name = {0} AND b.name = {2} MERGE (a)-[r:relation{name:{1}}]->(b)")
    void creatRelationByName(String from,String label,String to);
    //根据name建立节点，如果该节点的name存在则不建立
    @Query("MERGE (n:node {name:{0}}) ON CREATE SET n.type = {1}")
    void creatNode(String name,String type);
    //根据name查找节点并修改其type
    @Query("MATCH (n:node { name: {0}}) SET n.type ={1}  RETURN n")
    GraphNode modifyNodeType(String name,String newType);
    //修改节点的name,如果存在与修改后name相同的节点，将这些节点合并
    @Query("MATCH (n:node { name: {0}}) SET n.type ={1}  RETURN n")
    GraphNode modifyNodeName(String oldName,String newName);
    //修改relation的name
    @Query("MATCH (a:node{name:{0}})-[r]->(b:node{name:{1}}) SET r.name = {2} RETURN r")
    GraphNode modifyRelationName(String from,String to,String newName);
}
