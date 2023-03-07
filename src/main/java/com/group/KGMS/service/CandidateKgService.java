package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateKG;
import com.group.KGMS.entity.CandidateKGInfo;

import java.util.Date;

public interface CandidateKgService {
    //分页获取所有候选图谱
    PageInfo<CandidateKG> getCandidateKgByPage(Integer pageNum, Integer limitNum);

    //分页获取所有融合过的候选图谱
    PageInfo<CandidateKG> getOldCandidateKgByPage(Integer pageNum, Integer limitNum);

    //插入一个新的候选图谱
    Long insertNewKG(String name, String creator, Date create_time, Date change_time, String status, String comment);

    //根据Id获取图谱的名称
    String getNameById(Long id);

    //根据id删除候选图谱
    int deleteKgById(Long id);

    //根据id将融合后的候选图谱,将新换成旧
    int updateKgToOldById(Long id);

    //插入一条新的INFO信息
    int getCandiateKGInfo(CandidateKGInfo info);

    //根据Id查找INFO信息
    CandidateKGInfo getCandiateKGInfo(Long id);
}
