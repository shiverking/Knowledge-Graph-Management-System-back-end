package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateKG;
import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.entity.Triple;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface TripleService {
    //将未分类的三元组转入三元组库
    int insertIntoTriplesFromCandidateTriple(List<Triple> tripleList, Long candidateKgId);
    //将未分类的三元组删除
    int deleteCandidateTriples(List<CandidateTriple> tripleList);
    //分页获取所有来相同候选图谱的三元组
    PageInfo<Triple> getTripleFromSameKgByPage(Integer pageNum, Integer limitNum, Long candidateId);
    //分页获取所有三元组
    PageInfo<Triple> getTripleByPage(Integer pageNum, Integer limitNum);
    //分页获取所有来相同候选图谱的三元组(bufeny )
    List<Triple> getTripleFromSameKg(Long candidateId);
}
