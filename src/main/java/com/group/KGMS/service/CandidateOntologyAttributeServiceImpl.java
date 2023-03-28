package com.group.KGMS.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.KGMS.entity.CandidateOntology;
import com.group.KGMS.entity.CandidateOntologyAttribute;
import com.group.KGMS.entity.CandidateOntologyClass;
import com.group.KGMS.mapper.CandidateOntologyAttributeMapper;
import com.group.KGMS.utils.JsonResult;
import com.group.KGMS.utils.OWLUtil;
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
 * @CreateTime: 2023-03-23  15:55
 * @Description:
 */

@Service
public class CandidateOntologyAttributeServiceImpl extends ServiceImpl<CandidateOntologyAttributeMapper, CandidateOntologyAttribute> implements CandidateOntologyAttributeService{

    @Resource
    private CandidateOntologyClassService candidateOntologyClassService;

    @Resource
    private CandidateOntologyService candidateOntologyService;

    @Override
    public JsonResult getAttribute(Integer classId, Integer belongCandidateOntologyId) {
        List<CandidateOntologyAttribute> attributeList = query().eq("class_id", classId).eq("belong_candidate_id", belongCandidateOntologyId).list();
        return JsonResult.success(attributeList);
    }

    @Override
    public JsonResult addAttribute(CandidateOntologyAttribute newAttribute) {
        CandidateOntologyAttribute candidateOntologyAttribute = query().eq("attribute_name", newAttribute.getAttributeName()).eq("belong_candidate_id", newAttribute.getBelongCandidateId()).eq("class_id", newAttribute.getClassId()).one();
        if(candidateOntologyAttribute != null){
            return JsonResult.error("该属性已存在");
        }
        try {
            CandidateOntologyClass ontologyClass = candidateOntologyClassService.getById(newAttribute.getClassId());
            newAttribute.setClassName(ontologyClass.getName());
            save(newAttribute);
            CandidateOntology candidateOntology = candidateOntologyService.getById(newAttribute.getBelongCandidateId());
            OntModel ontModel = OWLUtil.owl2OntModel(candidateOntology.getName());
            OntClass ontClass = OWLUtil.createClass(ontModel, ontologyClass.getName(), candidateOntology.getName());
            OWLUtil.addProperty(ontModel, ontClass, newAttribute.getAttributeName(), candidateOntology.getName());
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.error("添加属性失败");
        }
        return JsonResult.success();
    }

    @Override
    public JsonResult deleteAttribute(Integer attributeId) {
        try {
            CandidateOntologyAttribute candidateOntologyAttribute = getById(attributeId);
            CandidateOntology candidateOntology = candidateOntologyService.getById(candidateOntologyAttribute.getBelongCandidateId());
            OntModel ontModel = OWLUtil.owl2OntModel(candidateOntology.getName());
            OntClass ontClass = OWLUtil.createClass(ontModel, candidateOntologyAttribute.getClassName(), candidateOntology.getName());
            removeById(attributeId);
            OWLUtil.removeProperty(ontModel, ontClass, candidateOntologyAttribute.getAttributeName(), candidateOntology.getName());
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.error("删除失败");
        }
        return JsonResult.success();
    }
}
