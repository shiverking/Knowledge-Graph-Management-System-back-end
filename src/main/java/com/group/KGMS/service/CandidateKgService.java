package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateKG;
import com.group.KGMS.entity.CandidateTriple;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface CandidateKgService {
    //分页获取所有候选图谱
    public PageInfo<CandidateKG> getCandidateKgByPage(Integer pageNum, Integer limitNum);

    //分页获取所有融合过的候选图谱
    public PageInfo<CandidateKG> getOldCandidateKgByPage(Integer pageNum, Integer limitNum);

    Long insertNewKG(String name, String creator, Date create_time, Date change_time, String status, String comment);

    String getNameById(Long id);
    //根据id删除候选图谱
    int deleteKgById(Long id);
    //根据id将融合后的候选图谱,将新换成旧
    int updateKgToOldById(Long id);
}
