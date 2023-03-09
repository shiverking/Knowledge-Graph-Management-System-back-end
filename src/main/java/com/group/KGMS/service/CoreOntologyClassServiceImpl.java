package com.group.KGMS.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.KGMS.entity.CandidateOntologyClass;
import com.group.KGMS.entity.CandidateOntologyTriple;
import com.group.KGMS.entity.CoreOntologyClass;
import com.group.KGMS.entity.CoreOntologyTriple;
import com.group.KGMS.mapper.CoreOntologyClassMapper;
import com.group.KGMS.mapper.CoreOntologyTripleMapper;
import com.group.KGMS.utils.CoreOWLUtil;
import com.group.KGMS.utils.OWLUtil;
import com.group.KGMS.utils.TreeJsonCoreOntologyClass;
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
 * @CreateTime: 2023-03-08  15:50
 * @Description:
 */

@Service
public class CoreOntologyClassServiceImpl extends ServiceImpl<CoreOntologyClassMapper, CoreOntologyClass> implements CoreOntologyClassService{

    @Autowired
    private CoreOntologyClassMapper coreOntologyClassMapper;

    @Autowired
    private CoreOntologyTripleMapper coreOntologyTripleMapper;

    @Override
    public CoreOntologyClass getRootClass() {
        List<CoreOntologyClass> classList = coreOntologyClassMapper.selectList(null);
        TreeJsonCoreOntologyClass treeJsonUtil = TreeJsonCoreOntologyClass.getInstance();
        CoreOntologyClass rootClass = treeJsonUtil.enquireTree(classList);
        return rootClass;
    }

    @Override
    public boolean save(String className, Integer parentId) throws Exception {
        //构建出要添加的类的对象，如果这个类已经存在就无法添加
        CoreOntologyClass newClass = new CoreOntologyClass(className, parentId);
        LambdaQueryWrapper<CoreOntologyClass> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CoreOntologyClass::getClassName, className);
        if(coreOntologyClassMapper.exists(lambdaQueryWrapper)){
            throw new RuntimeException("这个类已存在，不可以增加");
        }
        //找到要添加的类的父类
        CoreOntologyClass parentClass = coreOntologyClassMapper.selectById(parentId);
        OntModel ontModel = CoreOWLUtil.owl2OntModel();
        OntClass sonClass = CoreOWLUtil.createClass(ontModel, className);
        OntClass fatherClass = CoreOWLUtil.createClass(ontModel, parentClass.getClassName());
        CoreOWLUtil.addSubClass(ontModel, fatherClass, sonClass);
        int result = coreOntologyClassMapper.insert(newClass);
        return result > 0;
    }


    @Override
    public void remove(String className) throws Exception {
        LambdaQueryWrapper<CoreOntologyClass> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CoreOntologyClass::getClassName, className);
        CoreOntologyClass delClass = coreOntologyClassMapper.selectOne(lambdaQueryWrapper);
        //读取OWL文件
        OntModel ontModel = CoreOWLUtil.owl2OntModel();
        //根据查出来的类别名称创建出来OWL文件中对应的类
        OntClass ontClass = CoreOWLUtil.createClass(ontModel, delClass.getClassName());
        //利用OWL文件判断现在要删除的类是否有子类，有子类就抛出异常，不允许删除
        if(ontClass.hasSubClass()){
            throw new RuntimeException("不能删除，这个类有子类");
        }
        //没有子类就在数据库和OWL文件中进行删除
        coreOntologyClassMapper.deleteById(delClass);
        LambdaQueryWrapper<CoreOntologyTriple> lqw = new LambdaQueryWrapper<>();
        lqw.eq(CoreOntologyTriple::getHeadClassName, delClass.getClassName())
                .or()
                .eq(CoreOntologyTriple::getTailClassName, delClass.getClassName());
        List<CoreOntologyTriple> delList = coreOntologyTripleMapper.selectList(lqw);
        //根据到的要删除的列表逐个循环，在OWL文件中找到对应的关系，文件中关系不能删除，但是可以移除关系中的domain和range
        for(CoreOntologyTriple triple : delList){
            CoreOWLUtil.removeRelationDomainAndRange(ontModel, triple.getRelationName(), ontClass);
        }
        coreOntologyTripleMapper.delete(lqw);
        CoreOWLUtil.removeClass(ontModel, className);
    }
}
