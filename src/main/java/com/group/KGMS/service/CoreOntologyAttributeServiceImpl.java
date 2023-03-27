package com.group.KGMS.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.DeleteById;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.KGMS.entity.CoreOntologyAttribute;
import com.group.KGMS.entity.CoreOntologyClass;
import com.group.KGMS.mapper.CoreOntologyAttributeMapper;
import com.group.KGMS.mapper.CoreOntologyClassMapper;
import com.group.KGMS.utils.CoreOWLUtil;
import com.group.KGMS.utils.JsonResult;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-03-23  12:22
 * @Description:
 */

@Service
public class CoreOntologyAttributeServiceImpl extends ServiceImpl<CoreOntologyAttributeMapper, CoreOntologyAttribute> implements CoreOntologyAttributeService{

    @Resource
    private CoreOntologyAttributeMapper coreOntologyAttributeMapper;

    @Resource
    private CoreOntologyClassMapper coreOntologyClassMapper;

    @Override
    public JsonResult getAttributesByClassId(Integer id) {
        List<CoreOntologyAttribute> attributeList = query().eq("class_id", id).list();
        attributeList.forEach(System.out::println);
        return JsonResult.success(attributeList);
    }

    @Override
    public JsonResult addAttribute(CoreOntologyAttribute newAttribute) {
        CoreOntologyAttribute attribute = query().eq("attribute_name", newAttribute.getAttributeName()).eq("class_id", newAttribute.getClassId()).one();
        if(attribute != null){
            return JsonResult.error("该属性已存在");
        }
        CoreOntologyClass coreOntologyClass = coreOntologyClassMapper.selectById(newAttribute.getClassId());
        newAttribute.setClassName(coreOntologyClass.getName());
        try {
            save(newAttribute);
            OntModel ontModel = CoreOWLUtil.owl2OntModel();
            OntClass ontClass = CoreOWLUtil.createClass(ontModel, coreOntologyClass.getName());
            CoreOWLUtil.addProperty(ontModel, ontClass, newAttribute.getAttributeName());
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.error("添加属性失败");
        }
        return JsonResult.success();
    }

    @Override
    public JsonResult deleteAttribute(Integer attributeId) {
        try {
            CoreOntologyAttribute attribute = getById(attributeId);
            OntModel ontModel = CoreOWLUtil.owl2OntModel();
            OntClass ontClass = CoreOWLUtil.createClass(ontModel, attribute.getClassName());
            removeById(attributeId);
            CoreOWLUtil.removeProperty(ontModel, ontClass, attribute.getAttributeName());
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.error("删除属性失败");
        }
        return JsonResult.success();
    }
}
