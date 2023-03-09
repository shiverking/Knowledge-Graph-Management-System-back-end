package com.group.KGMS.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.KGMS.entity.CandidateOntologyClass;
import com.group.KGMS.entity.CandidateOntologyTriple;
import com.group.KGMS.entity.CoreOntologyClass;
import com.group.KGMS.entity.CoreOntologyTriple;
import com.group.KGMS.mapper.CoreOntologyClassMapper;
import com.group.KGMS.mapper.CoreOntologyTripleMapper;
import com.group.KGMS.utils.CoreOWLUtil;
import com.group.KGMS.utils.OWLUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-03-09  10:13
 * @Description:
 */

@Service
public class CoreOntologyTripleServiceImpl extends ServiceImpl<CoreOntologyTripleMapper, CoreOntologyTriple> implements CoreOntologyTripleService{

    @Autowired
    private CoreOntologyClassMapper coreOntologyClassMapper;

    @Autowired
    private CoreOntologyTripleMapper coreOntologyTripleMapper;

    @Override
    public void saveRelation(String headClassName, String relationName, String tailClassName) throws Exception {
        //判断要添加的关系存不存在，如果已经存在就无法添加
        LambdaQueryWrapper<CoreOntologyTriple> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CoreOntologyTriple::getHeadClassName, headClassName)
                .eq(CoreOntologyTriple::getRelationName, relationName)
                .eq(CoreOntologyTriple::getTailClassName, tailClassName);
        if(coreOntologyTripleMapper.exists(queryWrapper)){
            throw new RuntimeException("这个关系已经存在，不可重复添加");
        }
        //在文件中根据传过来的数据在文件中进行添加
        OntModel ontModel = CoreOWLUtil.owl2OntModel();
        OntClass headClass = CoreOWLUtil.createClass(ontModel, headClassName);
        OntClass tailClass = CoreOWLUtil.createClass(ontModel, tailClassName);
        CoreOWLUtil.addRelation(ontModel, headClass, tailClass, relationName);
        //需要根据传过来的id查找到对应的类的对象
        LambdaQueryWrapper<CoreOntologyClass> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CoreOntologyClass::getClassName, headClassName);
        CoreOntologyClass head = coreOntologyClassMapper.selectOne(lambdaQueryWrapper);
        LambdaQueryWrapper<CoreOntologyClass> lqw = new LambdaQueryWrapper<>();
        lqw.eq(CoreOntologyClass::getClassName, tailClassName);
        CoreOntologyClass tail = coreOntologyClassMapper.selectOne(lqw);
        CoreOntologyTriple newRelation = new CoreOntologyTriple(head.getId(), head.getClassName(),
                relationName, tail.getId(), tail.getClassName());
        coreOntologyTripleMapper.insert(newRelation);
    }

    @Override
    public void removeRelation(Integer headClassId, String relationName, Integer tailClassId) throws IOException {
        //根据头类id，关系名，尾类id，候选本体id删除数据库中唯一确定的记录
        LambdaQueryWrapper<CoreOntologyTriple> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CoreOntologyTriple::getHeadClassId, headClassId)
                .eq(CoreOntologyTriple::getTailClassId, tailClassId)
                .eq(CoreOntologyTriple::getRelationName, relationName);
        coreOntologyTripleMapper.delete(lambdaQueryWrapper);
        OntModel ontModel = CoreOWLUtil.owl2OntModel();
        //根据头尾类id在存储类的表里查找到对应的类名，再使用CoreOWLUtil工具类获取到OntClass对象
        CoreOntologyClass headClass = coreOntologyClassMapper.selectById(headClassId);
        CoreOntologyClass tailClass = coreOntologyClassMapper.selectById(tailClassId);
        //在OWL文件中删除掉想要删除的关系的头类和尾类，因为OWL文件中关系不能移除
        OntClass domain = CoreOWLUtil.createClass(ontModel, headClass.getClassName());
        OntClass range = CoreOWLUtil.createClass(ontModel, tailClass.getClassName());
        CoreOWLUtil.removeRelation(ontModel, relationName, domain, range);
    }

}
