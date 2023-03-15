package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateOntology;
import com.group.KGMS.mapper.CandidateOntologyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public PageInfo<CandidateOntology> getAllCandidateOntologyByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum,limitNum);
        PageInfo<CandidateOntology> allCandidateOntologyByPage = new PageInfo<>(candidateOntologyMapper.getAllCandidateOntology());
        return allCandidateOntologyByPage;
    }
}
