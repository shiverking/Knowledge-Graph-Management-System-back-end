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

    int insertCandidateTripleIntoTriple(Triple triple);

    List<Triple> getAllTriplesFromSameCandidateKg(@Param("candidateId") Long candidateId);
    //删除原有的候选图谱
    int deleteCandidateTriples(CandidateTriple candidateTripleTriple);
}
