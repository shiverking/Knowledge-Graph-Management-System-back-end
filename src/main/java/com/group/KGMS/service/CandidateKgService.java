package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateKG;
import com.group.KGMS.entity.CandidateTriple;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface CandidateKgService {
    //分页获取所有候选图谱
    public PageInfo<CandidateKG> getCandidateKgByPage(Integer pageNum, Integer limitNum);

    Long insertNewKG(String name, String creator, Date create_time, Date change_time, String status, String comment);

    String getNameById(Long id);
}
