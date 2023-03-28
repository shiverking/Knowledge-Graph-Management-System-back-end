package com.group.KGMS.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.dto.AttributeDto;
import com.group.KGMS.dto.ClassDto;
import com.group.KGMS.dto.RelationDto;
import com.group.KGMS.entity.CandidateOntology;
import com.group.KGMS.entity.CandidateOntologyAttribute;
import com.group.KGMS.entity.CandidateOntologyClass;
import com.group.KGMS.mapper.CandidateOntologyClassMapper;
import com.group.KGMS.mapper.CandidateOntologyMapper;
import com.group.KGMS.utils.JsonResult;
import com.group.KGMS.utils.OWLUtil;
import org.apache.jena.ontology.OntModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2022-12-02  16:47
 * @Description:
 */

@Service
public class CandidateOntologyServiceImpl extends ServiceImpl<CandidateOntologyMapper, CandidateOntology> implements CandidateOntologyService {

    @Autowired
    private CandidateOntologyMapper candidateOntologyMapper;

    @Resource
    private CandidateOntologyClassMapper candidateOntologyClassMapper;

    @Resource
    private ClassDtoService classDtoService;

    @Resource
    private CandidateOntologyClassService candidateOntologyClassService;

    @Resource
    private RelationDtoService relationDtoService;

    @Resource
    private AttributeDtoService attributeDtoService;

    @Resource
    private CandidateOntologyTripleService candidateOntologyTripleService;

    @Lazy
    @Resource
    private CandidateOntologyAttributeService candidateOntologyAttributeService;

    @Override
    public PageInfo<CandidateOntology> getAllCandidateOntologyByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum,limitNum);
        PageInfo<CandidateOntology> allCandidateOntologyByPage = new PageInfo<>(candidateOntologyMapper.getAllCandidateOntology());
        return allCandidateOntologyByPage;
    }

    @Override
    @Transactional
    public JsonResult saveOnto(CandidateOntology newOnto) {
        LambdaQueryWrapper<CandidateOntology> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CandidateOntology::getName, newOnto.getName());
        CandidateOntology ontology = candidateOntologyMapper.selectOne(queryWrapper);
        if(ontology != null){
            return JsonResult.error("该候选本体已存在");
        }
        try {
            candidateOntologyMapper.insert(newOnto);
            LambdaQueryWrapper<CandidateOntology> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CandidateOntology::getName, newOnto.getName());
            CandidateOntology candidateOntology = candidateOntologyMapper.selectOne(wrapper);
            CandidateOntologyClass candidateOntologyClass = new CandidateOntologyClass();
            candidateOntologyClass.setName("Thing");
            candidateOntologyClass.setBelongCandidateId(candidateOntology.getId());
            candidateOntologyClassMapper.insert(candidateOntologyClass);
            OWLUtil.addOWL(newOnto.getName());
            OntModel ontModel = OWLUtil.owl2OntModel(newOnto.getName());
            OWLUtil.createClass(ontModel, "Thing", newOnto.getName());
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.error("添加失败");
        }
        return JsonResult.success();
    }

    @Override
    @Transactional
    public JsonResult structuredDataToOntology(String candidateOntoName) {
        //获取新建的候选本体对象
        CandidateOntology candidateOntology = query().eq("name", candidateOntoName).one();
        //获取类的列表
        List<ClassDto> classList = classDtoService.list();
        //拿到根节点Thing
        ClassDto rootClass = classDtoService.query().eq("parent_id", 0).one();
        //把所有的类添加到候选本体中
        while (!classList.isEmpty()){
            for (int i = 0; i < classList.size(); i++) {
                ClassDto clazz = classList.get(i);
                //拿到每一个类
                System.out.println("clazz = " + clazz);
                //发现这个类就是根节点，直接退出此次循环，并将其从列表中剔除
                if(clazz.getName().equals(rootClass.getName())){
                    classList.remove(i);
                    break;
                }
                //找到当前类的父类
                ClassDto parentClass = classDtoService.getById(clazz.getParentId());
                //查找当前类的父类在该候选本体中是否存在
                CandidateOntologyClass candidateParentClass = candidateOntologyClassService.query().eq("class_name", parentClass.getName())
                        .eq("belong_candidate_id", candidateOntology.getId()).one();
                //父类在候选本体中不存在，就退出此次循环
                if(candidateParentClass == null){
                    break;
                }
                //父类在候选本体中存在，就将这个类插入候选本体中
                CandidateOntologyClass insertClass = new CandidateOntologyClass();
                //将类保存到数据库以及OWL文件中，然后将这个类从list中移除
                try {
                    candidateOntologyClassService.save(clazz.getName(), candidateParentClass.getId(), candidateOntology.getId());
                    classList.remove(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    return JsonResult.error("生成候选本体失败");
                }
            }
        }
        //获取关系列表
        List<RelationDto> relationList = relationDtoService.list();
        //把所有的关系添加到候选本体中
        for(RelationDto relation : relationList){
            ClassDto headClass = classDtoService.getById(relation.getStartId());
            String headClassName = headClass.getName();
            ClassDto endClass = classDtoService.getById(relation.getEndId());
            String endClassName = endClass.getName();
            try {
                //将关系存入候选本体
                candidateOntologyTripleService.saveRelation(headClassName, relation.getName(), endClassName, candidateOntology.getId());
            } catch (IOException e) {
                e.printStackTrace();
                return JsonResult.error("生成候选本体失败");
            }
        }
        //获取属性列表
        List<AttributeDto> attributeList = attributeDtoService.list();
        //把所有的属性添加到候选本体中
        for(AttributeDto attribute : attributeList){
            //查询这个属性是属于哪个类
            ClassDto classDto = classDtoService.getById(attribute.getClassId());
            CandidateOntologyClass waitInsertClass = candidateOntologyClassService.query().eq("class_name", classDto.getName()).eq("belong_candidate_id", candidateOntology.getId()).one();
            CandidateOntologyAttribute ontologyAttribute = new CandidateOntologyAttribute();
            //将必要数据进行保存
            ontologyAttribute.setClassId(waitInsertClass.getId());
            ontologyAttribute.setAttributeName(attribute.getName());
            ontologyAttribute.setComment(attribute.getDetail());
            ontologyAttribute.setBelongCandidateId(candidateOntology.getId());
            candidateOntologyAttributeService.addAttribute(ontologyAttribute);
        }
        return JsonResult.success();
    }
}
