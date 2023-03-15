package com.group.KGMS.mapper;

import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.entity.Triple;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface TripleMapper {
    List<Triple> getAllTriple();

    //将候选三元组变为三元组
    int insertCandidateTripleIntoTriple(Triple triple);

    //从一个候选图谱中获得所有的三元组
    List<Triple> getAllTriplesFromSameCandidateKg(@Param("candidateId") Long candidateId);

    //删除原有的候选图谱
    int deleteCandidateTriples(CandidateTriple candidateTripleTriple);

    //更新三元组所属的候选图谱
    int updateCandidateIdOfTriple(@Param("tripleId") Long tripleId, @Param("newCandidateId") Long newCandidateId);

    //向核心图谱插入融合改动
    int insertMergeChange(@Param("head") String head, @Param("relation") String relation, @Param("tail") String tail, @Param("time") Date time);

    //向核心图谱插入补全改动
    int insertCompletionChange(@Param("head") String head, @Param("relation") String relation, @Param("tail") String tail, @Param("time") Date time);
}
