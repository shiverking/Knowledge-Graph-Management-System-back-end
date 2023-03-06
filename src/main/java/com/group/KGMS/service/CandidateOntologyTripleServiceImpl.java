package com.group.KGMS.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.KGMS.entity.CandidateOntologyTriple;
import com.group.KGMS.mapper.CandidateOntologyTripleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private CandidateOntologyTripleMapper candidateOntologyTripleMapper;

    @Override
    public List<CandidateOntologyTriple> getAllRelationByCandidateOntologyId(Integer candidateOntologyId) {
        QueryWrapper<CandidateOntologyTriple> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("belong_candidate_id", candidateOntologyId);
        List<CandidateOntologyTriple> tripleList = candidateOntologyTripleMapper.selectList(queryWrapper);
        return tripleList;
    }
}
