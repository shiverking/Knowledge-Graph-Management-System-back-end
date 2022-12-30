package com.group.KGMS.mapper;

import com.group.KGMS.entity.CandidateKG;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.Date;
import java.util.List;

@Mapper
public interface CandidateKGMapper {
    List<CandidateKG> getAllCandidateKg();
    //插入一条新的候选图谱
    int insertNewKG(CandidateKG candidateKG);
    //根据id获取候选图谱名称
    String getNameById(Long Id);
    //根据id删除候选图谱
    int deleteKgById(@Param("id") Long id);
}
