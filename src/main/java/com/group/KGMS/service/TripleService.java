package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.entity.Triple;

import java.util.List;
import java.util.Map;

public interface TripleService {
    //将未分类的三元组转入三元组库
    int insertIntoTriplesFromCandidateTriple(List<CandidateTriple> tripleList, Long candidateKgId);

    //将已存在的三元组再次加入三元组库
    int insertIntoTriplesFromExistsKg(List<Triple> tripleList);

    //将未分类的三元组删除
    int deleteCandidateTriples(CandidateTriple tripleList);

    //分页获取所有来相同候选图谱的三元组
    PageInfo<Triple> getTripleFromSameKgByPage(Integer pageNum, Integer limitNum, Long candidateId);

    //分页获取所有三元组
    PageInfo<Triple> getTripleByPage(Integer pageNum, Integer limitNum);

    //分页获取所有来相同候选图谱的三元组(bufeny )
    List<Triple> getTripleFromSameKg(Long candidateId);

    //更新三元组所属的候选图谱
    int updateTriplesCandidateId(List<Long> ids, Long newKgId);

    //版本更新时,插入所有融合改动(不存在实体对齐的情况)
    int insertMergeChangeNoNameChange(List<Map<String, String>> triples);

    //版本更新时,插入所有融合改动(存在实体对齐的情况)
    int insertMergeChangeNameChange(List<Map<String, String>> triples);

    //版本更新时,插入所有融合改动(所有情况)
    int insertAllMergeChange(List<Map<String, Object>> triples);

    //版本更新时,插入所有补全改动
    int insertCompletionChange(List<Map<String, Object>> triples);

    //版本更新时,插入所有质量评估改动
    int insertEvaluationChange(List<Map<String, Object>> triples);

    //修改核心图谱实体名称
    int updateCoreKgEntityName(String oldName,String newName);
}
