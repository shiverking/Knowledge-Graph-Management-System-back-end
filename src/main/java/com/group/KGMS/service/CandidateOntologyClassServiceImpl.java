package com.group.KGMS.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.KGMS.entity.CandidateOntologyClass;
import com.group.KGMS.mapper.CandidateOntologyClassMapper;
import com.group.KGMS.utils.OWLUtil;
import com.group.KGMS.utils.TreeJsonCandidateOntologyClass;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2022-12-22  21:32
 * @Description: 候选本体类别信息的service，主要服务于父子关系
 */

@Service
public class CandidateOntologyClassServiceImpl extends ServiceImpl<CandidateOntologyClassMapper, CandidateOntologyClass> implements CandidateOntologyClassService{

    @Autowired
    private CandidateOntologyClassMapper candidateOntologyClassMapper;

    @Override
    public boolean save(String className, Integer parentId, Integer belongCandidateId) throws IOException {
        //根据传入的参数新建要插入数据库的对象
        CandidateOntologyClass newClass = new CandidateOntologyClass(className, parentId, belongCandidateId);
        //根据父类id和所属候选本体id查询出数据库中父类的记录
        QueryWrapper<CandidateOntologyClass> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("belong_candidate_id", belongCandidateId)
                .eq("id", parentId);
        CandidateOntologyClass parentClass = candidateOntologyClassMapper.selectOne(queryWrapper);
        //读取OWL文件，然后根据父类和子类名称创建父子类对象，然后为他们添加父子类关系，并写入OWL文件中
        OntModel ontModel = OWLUtil.owl2OntModel();
        OntClass sonClass = OWLUtil.createClass(ontModel, className);
        OntClass fatherClass = OWLUtil.createClass(ontModel, parentClass.getName());
        OWLUtil.addSubClass(ontModel, fatherClass, sonClass);
        //新的类添加入数据库中
        int result = candidateOntologyClassMapper.insert(newClass);
        return result > 0;
    }

    @Override
    public void remove(String className, Integer belongCandidateId) throws Exception {
        //根据传入的类别名称在数据库中查询出对应的这个类的记录
        LambdaQueryWrapper<CandidateOntologyClass> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CandidateOntologyClass::getName, className)
                .eq(CandidateOntologyClass::getBelongCandidateId, belongCandidateId);
        CandidateOntologyClass delClass = candidateOntologyClassMapper.selectOne(wrapper);
        //读取OWL文件
        OntModel ontModel = OWLUtil.owl2OntModel();
        //根据查出来的类别名称创建出来OWL文件中对应的类
        OntClass ontClass = OWLUtil.createClass(ontModel, delClass.getName());
        //利用OWL文件判断现在要删除的类是否有子类，有子类就抛出异常，不允许删除
        if(ontClass.hasSubClass()){
            throw new RuntimeException("不能删除，这个类有子类");
        }
        //没有子类就在数据库和OWL文件中进行删除
        candidateOntologyClassMapper.deleteById(delClass);
        OWLUtil.removeClass(ontModel, className);
    }

    @Override
    public CandidateOntologyClass getRootClassByCandidateOntologyId(Integer candidateOntologyId) {
        QueryWrapper<CandidateOntologyClass> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("belong_candidate_id", candidateOntologyId);
        //获取指定候选本体id的所有类
        List<CandidateOntologyClass> ontologyClassList = candidateOntologyClassMapper.selectList(queryWrapper);
        TreeJsonCandidateOntologyClass treeJsonUtil = TreeJsonCandidateOntologyClass.getInstance();
        //利用树形工具类把所有的类封装成一个树形的结构，返回根节点
        CandidateOntologyClass rootClass = treeJsonUtil.enquireTree(ontologyClassList);
        return rootClass;
    }
}
