package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.mapper.CandidateTripleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateTripleServiceImpl implements CandidateTripleService {
    @Autowired
    CandidateTripleMapper candidateTripleMapper;
    @Override
    public PageInfo<CandidateTriple> getCandidateTripleByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum,limitNum);
        PageInfo<CandidateTriple> info = new PageInfo<CandidateTriple>(candidateTripleMapper.getTriplesList());
        return info;
    }
}
