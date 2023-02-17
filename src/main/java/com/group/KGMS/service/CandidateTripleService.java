package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateTriple;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface CandidateTripleService {
    public PageInfo<CandidateTriple> getCandidateTripleByPage(Integer pageNum, Integer limitNum);
    //带有时间限制的候选三元组
    PageInfo<CandidateTriple> getTriplesListWithTimeLimitByPage(Integer pageNum, Integer limitNum,Date startTime,Date endTime);
    //带有来源限制的候选三元组
    PageInfo<CandidateTriple> getTriplesListWithSourceLimitByPage(Integer pageNum, Integer limitNum, String source);
    //带有时间和来源限制的候选三元组
    PageInfo<CandidateTriple> getTriplesListWithSourceAnTimeLimitByPage(Integer pageNum, Integer limitNum, Date startTime, Date endTime, String source);
    //根据id删除某个候选三元组
    int deleteCandidateTripleById(Long id);
    //根据id更新某个候选三元组
    int updateCandidateTripleById(Long id,String head,String relation,String tail,String head_category,String tail_category);
}
