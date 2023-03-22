package com.group.KGMS.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateOntology;
import com.group.KGMS.entity.CandidateOntologyClass;
import com.group.KGMS.mapper.CandidateOntologyClassMapper;
import com.group.KGMS.mapper.CandidateOntologyMapper;
import com.group.KGMS.utils.JsonResult;
import com.group.KGMS.utils.OWLUtil;
import org.apache.jena.ontology.OntModel;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CandidateOntologyServiceImpl implements CandidateOntologyService {

    @Autowired
    private CandidateOntologyMapper candidateOntologyMapper;

    @Resource
    private CandidateOntologyClassMapper candidateOntologyClassMapper;

    @Override
    public PageInfo<CandidateOntology> getAllCandidateOntologyByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum,limitNum);
        PageInfo<CandidateOntology> allCandidateOntologyByPage = new PageInfo<>(candidateOntologyMapper.getAllCandidateOntology());
        return allCandidateOntologyByPage;
    }

    @Override
    @Transactional
    public JsonResult save(CandidateOntology newOnto) {
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
}
