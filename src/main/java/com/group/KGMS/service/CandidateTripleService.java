package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateTriple;
import org.springframework.stereotype.Service;

import java.text.ParseException;

public interface CandidateTripleService {
    public PageInfo<CandidateTriple> getCandidateTripleByPage(Integer pageNum, Integer limitNum);
}
