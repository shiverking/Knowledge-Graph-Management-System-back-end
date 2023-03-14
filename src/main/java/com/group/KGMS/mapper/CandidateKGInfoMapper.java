package com.group.KGMS.mapper;

import com.group.KGMS.entity.CandidateKG;
import com.group.KGMS.entity.CandidateKGInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CandidateKGInfoMapper {
    //插入一条新的候选图谱
    int insertNewKGInfo(CandidateKGInfo candidateKGInfo);

    //根据Id查找candidateInf信息
    CandidateKGInfo getInfoById(@Param("candidateId") Long candidateId);
}
