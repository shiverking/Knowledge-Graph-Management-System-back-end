package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.*;
import com.group.KGMS.mapper.CandidateKGMapper;
import com.group.KGMS.mapper.TripleMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class TripleServiceImpl implements TripleService {
    @Autowired
    TripleMapper tripleMapper;
    @Autowired
    CandidateKGMapper candidateKGMapper;
    @Autowired
    EntityService entityService;
    @Autowired
    RelationService relationService;
    @Resource
    SqlSessionFactory sqlSessionFactory;
    /**
     * 将候选三元组批量插入三元组库中
     * @return
     */
    @Override
    public int insertIntoTriplesFromCandidateTriple(List<CandidateTriple> list, Long candidateKgId) {
        int result = 1;
        String status="已入库";
        for(int i=0;i<list.size();i++){
            Triple triple  = new Triple();
            triple.setHead(list.get(i).getHead());
            triple.setTail(list.get(i).getTail());
            triple.setRelation(list.get(i).getRelation());
            triple.setTime(new Date());
            triple.setCandidateId(candidateKgId);
            triple.setStatus(status);
            tripleMapper.insertCandidateTripleIntoTriple(triple);
            //获取三元组id,判断是否存在并插入头尾实体
            Long tripleId = triple.getId();
            Long headId = entityService.ifEntityExists(triple.getHead());
            if(headId==null){
                Entity entity = new Entity();
                entity.setName(list.get(i).getHead());
                entity.setCategory(list.get(i).getHeadCategory());
                entityService.insertNewEntity(entity);
                headId  = entity.getId();
            }
            Long tailId = entityService.ifEntityExists(triple.getTail());
            if(tailId==null){
                Entity entity = new Entity();
                entity.setName(list.get(i).getTail());
                entity.setCategory(list.get(i).getTailCategory());
                entityService.insertNewEntity(entity);
                tailId  = entity.getId();
            }
            Long relationId = relationService.ifRelationExists(triple.getRelation());
            if(relationId==null){
                Relation relation = new Relation();
                relation.setName(list.get(i).getRelation());
                relationService.insertNewRelation(relation);
                relationId  = relation.getId();
            }
            //插入关系
            if(entityService.insertNewCorrelationForEachTriple(tripleId,headId,tailId,relationId)!=0){
                deleteCandidateTriples(list.get(i));
            }
            else{
                result = 0;
            }
        }
        return result;
    }

    /**
     * 将三元组从已存在的图谱中再次加入三元组库
     * @param list
     * @return
     */
    @Override
    public int insertIntoTriplesFromExistsKg(List<Triple> list) {
        int result = 0;
        for(int i=0;i<list.size();i++){
            Long oldId = list.get(i).getId();
            Triple triple  = new Triple();
            triple.setHead(list.get(i).getHead());
            triple.setTail(list.get(i).getTail());
            triple.setRelation(list.get(i).getRelation());
            triple.setTime(new Date());
            triple.setCandidateId(list.get(i).getCandidateId());
            triple.setStatus(list.get(i).getStatus());
            tripleMapper.insertCandidateTripleIntoTriple(triple);
            //在triple_ids中插入关系
            Map<String,Object> oldCorrelation = entityService.selectSpecificCorrelation(oldId);
            Long headId = Long.parseLong(String.valueOf(oldCorrelation.get("head_id")));
            Long tailId = Long.parseLong(String.valueOf(oldCorrelation.get("tail_id")));
            Long realtionId = Long.parseLong(String.valueOf(oldCorrelation.get("relation_id")));
            if(entityService.insertNewCorrelationForEachTriple(triple.getId(),headId,tailId,realtionId)!=0){
                result =1;
            }
        }
        return result;
    }

    /**
     * 将候选图谱从库中删除
     * @param triple
     * @return
     */
    @Override
    public int deleteCandidateTriples(CandidateTriple triple) {
        int result = 0;
        if(tripleMapper.deleteCandidateTriples(triple)==1){
            result = 1;
        }
        return result;
    }

    /**
     * 分页获取所有来自相同图谱的三元组
     * @param pageNum
     * @param limitNum
     * @param candidateId
     * @return
     */
    @Override
    public PageInfo<Triple> getTripleFromSameKgByPage(Integer pageNum, Integer limitNum, Long candidateId) {
        PageHelper.startPage(pageNum,limitNum);
        PageInfo<Triple> info = new PageInfo<Triple>(tripleMapper.getAllTriplesFromSameCandidateKg(candidateId));
        return info;
    }

    /**
     * 分页获取所有三元组
     * @param pageNum
     * @param limitNum
     * @return
     */
    @Override
    public PageInfo<Triple> getTripleByPage(Integer pageNum, Integer limitNum){
        PageHelper.startPage(pageNum,limitNum);
        List<Triple> tripleList = tripleMapper.getAllTriple();
        PageInfo<Triple> info = new PageInfo<Triple>(tripleList);
        //为类动态增加一个候选图谱名称的属性
//        List<Triple> newTripleList = new ArrayList<>();
//        for(Triple triple:tripleList){
//            Long id = triple.getId();
//            String candidateName = candidateKGMapper.getNameById(id);
//            Object studentVO = new StudentVO();
//            BeanUtils.copyProperties(studentDO, studentVO);
//            studentVO.setClassName("六年级二班");
//            studentVos.add(studentVO);
//            Map<String, Object> propertiesMap = new HashMap<>(1);
//            propertiesMap.put("candidateName", candidateName);
//            Object obj = PropertyAppender.generate(triple, propertiesMap);
//            newTripleList.add(obj);
//        }
        //这一步的作用是将封装后的列表放到分页对象中
//        info.setList(newTripleList);
        return info;
    }

    @Override
    public List<Triple> getTripleFromSameKg(Long candidateId) {
        return tripleMapper.getAllTriplesFromSameCandidateKg(candidateId);
    }

    /**
     * 更新三元组所属的候选图谱
     * @param newKgId
     * @param ids
     * @return
     */
    @Override
    public int updateTriplesCandidateId(List<Long> ids,Long newKgId) {
        int result = 1;
        try {
            SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            TripleMapper tmpMapper = openSession.getMapper(TripleMapper.class);
            for(Long tripleId:ids){
                tmpMapper.updateCandidateIdOfTriple(tripleId,newKgId);
            }
            openSession.commit();
            openSession.clearCache();
            openSession.close();
        } catch (Exception e){
            result = 0;
            System.out.println(e);
        }
        return result;
    }

    /**
     * 将所有没有实体对齐的三元组加入核心图谱
     * @param triples
     * @return
     */
    @Override
    public int insertMergeChangeNoNameChange(List<Map<String,String>> triples) {
        int result = 1;
        try {
            SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            TripleMapper tmpMapper = openSession.getMapper(TripleMapper.class);
            for(Map<String,String> record:triples){
                tmpMapper.insertMergeChange(record.get("head"),record.get("relation"),record.get("tail"),new Date());
            }
            openSession.commit();
            openSession.clearCache();
            openSession.close();
        } catch (Exception e){
            result = 0;
            System.out.println(e);
        }
        return result;
    }
    /**
     * 将所有三元组，经过分类后加入核心图谱
     * @param triples
     * @return
     */
    @Override
    public int insertAllMergeChange(List<Map<String, Object>> triples){
        List<Map<String,String>> noEntityNameChange = new ArrayList<>();
        List<Map<String,String>> EntityNameChange = new ArrayList<>();
        for(Map<String,Object> map:triples){
            if(map.get("head_from").toString().equals("null")&&map.get("tail_from").toString().equals("null")&&map.get("operation").equals("插入")){
                Map<String,String> newMap = new HashMap<>();
                newMap.put("head",(String)map.get("head"));
                newMap.put("relation",(String)map.get("relation"));
                newMap.put("tail",(String)map.get("tail"));
                noEntityNameChange.add(newMap);
            }
        }
        //先只做没有实体对齐名称改变的
        return insertMergeChangeNoNameChange(noEntityNameChange);
    }

    /**
     * 将补全改动插入核心图谱
     * @param triples
     * @return
     */
    @Override
    public int insertCompletionChange(List<Map<String, Object>> triples){
        int result = 1;
        try {
            SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            TripleMapper tmpMapper = openSession.getMapper(TripleMapper.class);
            for(Map<String,Object> map:triples){
                tmpMapper.insertCompletionChange((String)map.get("head"),(String)map.get("rel"),(String)map.get("tail"),new Date());
            }
            openSession.commit();
            openSession.clearCache();
            openSession.close();
        } catch (Exception e){
            result = 0;
            System.out.println(e);
        }
        return result;
    }
}
