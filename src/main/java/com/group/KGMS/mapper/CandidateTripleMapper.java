package com.group.KGMS.mapper;

import com.group.KGMS.entity.CandidateTriple;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CandidateTripleMapper {
    List<CandidateTriple> getTriplesList();
}
