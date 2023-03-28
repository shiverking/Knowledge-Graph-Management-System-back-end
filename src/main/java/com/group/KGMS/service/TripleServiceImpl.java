package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.*;
import com.group.KGMS.mapper.CandidateKGInfoMapper;
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
    @Autowired
    CandidateKGInfoMapper candidateKGInfoMapper;

    /**
     * 将候选三元组批量插入三元组库中
     *
     * @return
     */
    @Override
    public int insertIntoTriplesFromCandidateTriple(List<CandidateTriple> list, Long candidateKgId) {
        int result = 1;
        String status = "已入库";
        Set<Long> entitySet = new HashSet<>();
        Set<Long> relationSet = new HashSet<>();
//        //三元组数
//        Long tripleCount = Long.valueOf(list.size());
//        //实体数
//        Long entityCount = Long.valueOf(0);
//        //关系数
//        Long relationCount = Long.valueOf(0);
        for (int i = 0; i < list.size(); i++) {
            Triple triple = new Triple();
            triple.setHead(list.get(i).getHead());
            triple.setHeadCategory(list.get(i).getHeadCategory());
            triple.setTail(list.get(i).getTail());
            triple.setTailCategory(list.get(i).getTailCategory());
            triple.setRelation(list.get(i).getRelation());
            triple.setTime(new Date());
            triple.setCandidateId(candidateKgId);
            triple.setStatus(status);
            tripleMapper.insertCandidateTripleIntoTriple(triple);
            //获取三元组id,判断是否存在并插入头尾实体
            Long tripleId = triple.getId();
            Long headId = entityService.ifEntityExists(triple.getHead());
            if(headId!=null){
                entitySet.add(headId);
            }
            //没有该实体则新建一个加入
            else if (headId == null) {
                Entity entity = new Entity();
                entity.setName(list.get(i).getHead());
                entity.setCategory(list.get(i).getHeadCategory());
                entityService.insertNewEntity(entity);
                headId = entity.getId();
                entitySet.add(headId);
            }
            Long tailId = entityService.ifEntityExists(triple.getTail());
            if(tailId!=null){
                entitySet.add(tailId);
            }
            else if (tailId == null) {
                Entity entity = new Entity();
                entity.setName(list.get(i).getTail());
                entity.setCategory(list.get(i).getTailCategory());
                entityService.insertNewEntity(entity);
                tailId = entity.getId();
                entitySet.add(tailId);
            }
            Long relationId = relationService.ifRelationExists(triple.getRelation());
            if(relationId!=null){
                relationSet.add(relationId);
            }
            else if (relationId == null) {
                Relation relation = new Relation();
                relation.setName(list.get(i).getRelation());
                relationService.insertNewRelation(relation);
                relationId = relation.getId();
                relationSet.add(relationId);
            }
            //插入关系
            if (entityService.insertNewCorrelationForEachTriple(tripleId, headId, tailId, relationId) != 0) {
                deleteCandidateTriples(list.get(i));
            } else {
                result = 0;
            }
        }
        //插入一条info 信息
        CandidateKGInfo info = new CandidateKGInfo();
        info.setTripleCount(Long.valueOf(list.size()));
        info.setRelationCount(Long.valueOf(list.size()));
        info.setRelationTypeCount(Long.valueOf(relationSet.size()));
        info.setEntityCount(Long.valueOf(entitySet.size()));
        info.setCandidateId(candidateKgId);
        candidateKGInfoMapper.insertNewKGInfo(info);
        return result;
    }

    /**
     * 将三元组从已存在的图谱中再次加入三元组库
     *
     * @param list
     * @return
     */
    @Override
    public int insertIntoTriplesFromExistsKg(List<Triple> list) {
        int result = 0;
        for (int i = 0; i < list.size(); i++) {
            Long oldId = list.get(i).getId();
            Triple triple = new Triple();
            triple.setHead(list.get(i).getHead());
            triple.setHeadCategory(list.get(i).getHeadCategory());
            triple.setTail(list.get(i).getTail());
            triple.setTailCategory(list.get(i).getTailCategory());
            triple.setRelation(list.get(i).getRelation());
            triple.setTime(new Date());
            triple.setCandidateId(list.get(i).getCandidateId());
            triple.setStatus(list.get(i).getStatus());
            tripleMapper.insertCandidateTripleIntoTriple(triple);
            //在triple_ids中插入关系
            Map<String, Object> oldCorrelation = entityService.selectSpecificCorrelation(oldId);
            Long headId = Long.parseLong(String.valueOf(oldCorrelation.get("head_id")));
            Long tailId = Long.parseLong(String.valueOf(oldCorrelation.get("tail_id")));
            Long realtionId = Long.parseLong(String.valueOf(oldCorrelation.get("relation_id")));
            if (entityService.insertNewCorrelationForEachTriple(triple.getId(), headId, tailId, realtionId) != 0) {
                result = 1;
            }
        }
        return result;
    }

    /**
     * 将候选图谱从库中删除
     *
     * @param triple
     * @return
     */
    @Override
    public int deleteCandidateTriples(CandidateTriple triple) {
        int result = 0;
        if (tripleMapper.deleteCandidateTriples(triple) == 1) {
            result = 1;
        }
        return result;
    }

    /**
     * 分页获取所有来自相同图谱的三元组
     *
     * @param pageNum
     * @param limitNum
     * @param candidateId
     * @return
     */
    @Override
    public PageInfo<Triple> getTripleFromSameKgByPage(Integer pageNum, Integer limitNum, Long candidateId) {
        PageHelper.startPage(pageNum, limitNum);
        PageInfo<Triple> info = new PageInfo<Triple>(tripleMapper.getAllTriplesFromSameCandidateKg(candidateId));
        return info;
    }

    /**
     * 分页获取所有三元组
     *
     * @param pageNum
     * @param limitNum
     * @return
     */
    @Override
    public PageInfo<Triple> getTripleByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum, limitNum);
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
     *
     * @param newKgId
     * @param ids
     * @return
     */
    @Override
    public int updateTriplesCandidateId(List<Long> ids, Long newKgId) {
        int result = 1;
        try {
            SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            TripleMapper tmpMapper = openSession.getMapper(TripleMapper.class);
            for (Long tripleId : ids) {
                tmpMapper.updateCandidateIdOfTriple(tripleId, newKgId);
            }
            openSession.commit();
            openSession.clearCache();
            openSession.close();
        } catch (Exception e) {
            result = 0;
            System.out.println(e);
        }
        return result;
    }

    /**
     * 将所有没有实体对齐的三元组加入核心图谱
     *
     * @param triples
     * @return
     */
    @Override
    public int insertMergeChangeNoNameChange(List<Map<String, String>> triples) {
        int result = 1;
        try {
            SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            TripleMapper tmpMapper = openSession.getMapper(TripleMapper.class);
            for (Map<String, String> record : triples) {
                tmpMapper.insertMergeChange(record.get("head"), record.get("headCategory"),record.get("relation"), record.get("tail"),record.get("tailCategory"), new Date());
            }
            openSession.commit();
            openSession.clearCache();
            openSession.close();
        } catch (Exception e) {
            result = 0;
            System.out.println(e);
        }
        return result;
    }

    /**
     * 将所有存在实体对齐的三元组加入核心图谱
     * @param triples
     * @return
     */
    @Override
    public int insertMergeChangeNameChange(List<Map<String, String>> triples) {
        int result = 1;
        try {
            SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            TripleMapper tmpMapper = openSession.getMapper(TripleMapper.class);
            for (Map<String, String> record : triples) {
                //如果不需要修改数据库中的实体，则直接插入
                tmpMapper.insertMergeChange(record.get("head"), record.get("headCategory"),record.get("relation"), record.get("tail"),record.get("tailCategory"), new Date());
                //如果需要修改修改数据库中的实体，则记录后修改
                if(record.get("headChange")!=null){
                    updateCoreKgEntityName(record.get("headChange"),record.get("head"));
                }
                if(record.get("tailChange")!=null){
                    updateCoreKgEntityName(record.get("tailChange"),record.get("tail"));
                }
            }
            openSession.commit();
            openSession.clearCache();
            openSession.close();
        } catch (Exception e) {
            result = 0;
            System.out.println(e);
        }
        return result;
    }

    /**
     * 将所有三元组，经过分类后加入核心图谱
     *
     * @param triples
     * @return
     */
    @Override
    public int insertAllMergeChange(List<Map<String, Object>> triples) {
        List<Map<String, String>> noEntityNameChange = new ArrayList<>();
        List<Map<String, String>> entityNameChange = new ArrayList<>();
        for (Map<String, Object> map : triples) {
            if (map.get("head_from").toString().equals("null") && map.get("tail_from").toString().equals("null") && map.get("operation").equals("插入")) {
                Map<String, String> newMap = new HashMap<>();
                newMap.put("head", (String) map.get("head"));
                newMap.put("headCategory", (String) map.get("head_category"));
                newMap.put("relation", (String) map.get("relation"));
                newMap.put("tail", (String) map.get("tail"));
                newMap.put("tailCategory", (String) map.get("tail_category"));
                noEntityNameChange.add(newMap);
            }
            else if((!map.get("head_from").toString().equals("null") || !map.get("tail_from").toString().equals("null")) && map.get("operation").equals("插入")){
                Map<String, String> newMap = new HashMap<>();
                //如果核心图谱中的实体没有变，只是候选三元组中的实体改变,则插入核心图谱即可
                if(!map.get("head_from").toString().contains("修改核心实体") && !map.get("tail_from").toString().contains("修改核心实体")){
                    newMap.put("head", (String) map.get("head"));
                    newMap.put("relation", (String) map.get("relation"));
                    newMap.put("tail", (String) map.get("tail"));
                    newMap.put("headCategory", (String) map.get("head_category"));
                    newMap.put("tailCategory", (String) map.get("tail_category"));
                }
                else{
                    newMap.put("head", (String) map.get("head"));
                    newMap.put("relation", (String) map.get("relation"));
                    newMap.put("tail", (String) map.get("tail"));
                    newMap.put("headCategory", (String) map.get("head_category"));
                    newMap.put("tailCategory", (String) map.get("tail_category"));
                    if(map.get("head_from").toString().contains("修改核心实体")){
                        String entity = map.get("head_from").toString().split(":")[1];
                        //后续根据这个字段修改core kg中的实体
                        newMap.put("headChange", entity);
                    }
                    if(map.get("tail_from").toString().contains("修改核心实体")){
                        String entity = map.get("tail_from").toString().split(":")[1];
                        //后续根据这个字段修改core kg中的实体
                        newMap.put("tailChange", entity);
                    }
                }
                entityNameChange.add(newMap);
            }
        }
        //修改没有对齐的和有对齐的
        if(insertMergeChangeNoNameChange(noEntityNameChange)==1&&insertMergeChangeNameChange(entityNameChange)==1){
            return 1;
        }
        return 0;
    }

    /**
     * 将补全改动插入核心图谱
     *
     * @param triples
     * @return
     */
    @Override
    public int insertEvaluationChange(List<Map<String, Object>> triples) {
        int result = 1;
        List<Map<String, Object>> deleteGroup = new ArrayList<>();
        List<Map<String, Object>> updateEntityCategory = new ArrayList<>();
        List<Map<String, Object>> updateRelationGroup = new ArrayList<>();
        //分类
        for (Map<String, Object> map : triples) {
            if(map.get("update_form").equals(new Long(0))){
                deleteGroup.add(map);
            }
            else if(map.get("update_form").equals(new Long(1))){
                updateEntityCategory.add(map);
            }
            else if(map.get("update_form").equals(new Long(2))){
                updateRelationGroup.add(map);
            }
        }
        SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        TripleMapper tmpMapper = openSession.getMapper(TripleMapper.class);
        try {
            //处理删除组
            for (Map<String, Object> map : deleteGroup) {
                tmpMapper.evaluationDelete((String) map.get("head"), (String) map.get("rel"), (String) map.get("tail"));
            }
            //处理修改实体组
            for (Map<String, Object> map : updateEntityCategory) {
                tmpMapper.evaluationUpdateEntityCategory((String) map.get("head"), (String) map.get("rel"), (String) map.get("tail"),(String) map.get("head_typ_new"),(String) map.get("tail_typ_new"));
            }
            //处理修改关系组
            for (Map<String, Object> map : updateRelationGroup) {
                tmpMapper.evaluationUpdateRelation((String) map.get("head"), (String) map.get("rel"), (String) map.get("tail"),(String) map.get("rel_new"));
            }
            openSession.commit();
            openSession.clearCache();
            openSession.close();
        } catch (Exception e) {
            openSession.rollback();
            result = 0;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将补全改动插入核心图谱
     *
     * @param records
     * @return
     */
    @Override
    public int insertCompletionChange(List<Map<String, Object>> records) {
        int result = 1;
        SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        TripleMapper tmpMapper = openSession.getMapper(TripleMapper.class);
        try {
            for (Map<String, Object> map : records) {
                tmpMapper.insertCompletionChange((String) map.get("head"), (String) map.get("rel"), (String) map.get("tail"),(String) map.get("head_category"), (String) map.get("tail_category"),new Date());
            }
            openSession.commit();
            openSession.clearCache();
            openSession.close();
        } catch (Exception e) {
            openSession.rollback();
            result = 0;
            System.out.println(e);
        }
        return result;
    }

    /**
     * 修改核心图谱中的实体名称
     * @param oldName
     * @param newName
     * @return
     */
    @Override
    public int updateCoreKgEntityName(String oldName, String newName) {
        if(tripleMapper.updateEntityHeadName(oldName,newName)==1&&tripleMapper.updateEntityTailName(oldName, newName)==1){
            return 1;
        }
        return 0;
    }
}
