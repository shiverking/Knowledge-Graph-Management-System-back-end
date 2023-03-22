package com.group.KGMS.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.KGMS.entity.CandidateOntology;
import com.group.KGMS.entity.CandidateOntologyClass;
import com.group.KGMS.entity.CandidateOntologyTriple;
import com.group.KGMS.mapper.CandidateOntologyClassMapper;
import com.group.KGMS.mapper.CandidateOntologyMapper;
import com.group.KGMS.mapper.CandidateOntologyTripleMapper;
import com.group.KGMS.utils.OWLUtil;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-03-07  00:31
 * @Description:
 */
@Service
public class CandidateOntologyTripleServiceImpl extends ServiceImpl<CandidateOntologyTripleMapper, CandidateOntologyTriple> implements CandidateOntologyTripleService{

    @Autowired
    private CandidateOntologyClassMapper candidateOntologyClassMapper;

    @Autowired
    private CandidateOntologyTripleMapper candidateOntologyTripleMapper;

    @Resource
    private CandidateOntologyMapper candidateOntologyMapper;

    @Override
    public List<CandidateOntologyTriple> getAllRelationByCandidateOntologyId(Integer candidateOntologyId) {
        QueryWrapper<CandidateOntologyTriple> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("belong_candidate_id", candidateOntologyId);
        List<CandidateOntologyTriple> tripleList = candidateOntologyTripleMapper.selectList(queryWrapper);
        return tripleList;
    }

    @Override
    public void saveRelation(String headClassName, String relation, String tailClassName, Integer candidateOntologyId) throws IOException {
        //判断要添加的关系存不存在，如果已经存在就无法添加
        LambdaQueryWrapper<CandidateOntologyTriple> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CandidateOntologyTriple::getHeadClassName, headClassName)
                .eq(CandidateOntologyTriple::getRelationName, relation)
                .eq(CandidateOntologyTriple::getTailClassName, tailClassName)
                .eq(CandidateOntologyTriple::getBelongCandidateOntologyId, candidateOntologyId);
        if(candidateOntologyTripleMapper.exists(queryWrapper)){
            throw new RuntimeException("这个关系已经存在，不可重复添加");
        }
        //在文件中根据传过来的数据在文件中进行添加
        CandidateOntology candidateOntology = candidateOntologyMapper.selectById(candidateOntologyId);
        OntModel ontModel = OWLUtil.owl2OntModel(candidateOntology.getName());
        OntClass headClass = OWLUtil.createClass(ontModel, headClassName, candidateOntology.getName());
        OntClass tailClass = OWLUtil.createClass(ontModel, tailClassName, candidateOntology.getName());
        OWLUtil.addRelation(ontModel, headClass, tailClass, relation, candidateOntology.getName());
        //需要根据传过来的id查找到对应的类的对象
        LambdaQueryWrapper<CandidateOntologyClass> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CandidateOntologyClass::getName, headClassName)
                        .eq(CandidateOntologyClass::getBelongCandidateId, candidateOntologyId);
        CandidateOntologyClass head = candidateOntologyClassMapper.selectOne(lambdaQueryWrapper);
        LambdaQueryWrapper<CandidateOntologyClass> lqw = new LambdaQueryWrapper<>();
        lqw.eq(CandidateOntologyClass::getName, tailClassName)
                .eq(CandidateOntologyClass::getBelongCandidateId, candidateOntologyId);
        CandidateOntologyClass tail = candidateOntologyClassMapper.selectOne(lqw);
        CandidateOntologyTriple newRelation = new CandidateOntologyTriple(head.getId(), head.getName(),
                relation, tail.getId(), tail.getName(), candidateOntologyId);
        candidateOntologyTripleMapper.insert(newRelation);
    }

    @Override
    public void removeRelation(Integer headClassId, Integer tailClassId, String relation, Integer candidateOntologyId) throws IOException {
        //根据头类id，关系名，尾类id，候选本体id删除数据库中唯一确定的记录
        LambdaQueryWrapper<CandidateOntologyTriple> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CandidateOntologyTriple::getHeadClassId, headClassId)
                .eq(CandidateOntologyTriple::getTailClassId, tailClassId)
                .eq(CandidateOntologyTriple::getRelationName, relation)
                .eq(CandidateOntologyTriple::getBelongCandidateOntologyId, candidateOntologyId);
        candidateOntologyTripleMapper.delete(lambdaQueryWrapper);
        CandidateOntology candidateOntology = candidateOntologyMapper.selectById(candidateOntologyId);
        OntModel ontModel = OWLUtil.owl2OntModel(candidateOntology.getName());
        //根据头尾类id在存储类的表里查找到对应的类名，再使用OWLUtil工具类获取到OntClass对象
        LambdaQueryWrapper<CandidateOntologyClass> lqw = new LambdaQueryWrapper<>();
        lqw.eq(CandidateOntologyClass::getId, headClassId)
                        .eq(CandidateOntologyClass::getBelongCandidateId, candidateOntologyId);
        CandidateOntologyClass headClass = candidateOntologyClassMapper.selectOne(lqw);
        LambdaQueryWrapper<CandidateOntologyClass> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(CandidateOntologyClass::getId, tailClassId)
                .eq(CandidateOntologyClass::getBelongCandidateId, candidateOntologyId);
        CandidateOntologyClass tailClass = candidateOntologyClassMapper.selectOne(lqw1);
        //在OWL文件中删除掉想要删除的关系的头类和尾类，因为OWL文件中关系不能移除
        OntClass domain = OWLUtil.createClass(ontModel, headClass.getName(), candidateOntology.getName());
        OntClass range = OWLUtil.createClass(ontModel, tailClass.getName(), candidateOntology.getName());
        OWLUtil.removeRelation(ontModel, relation, domain, range, candidateOntology.getName());
    }
}
