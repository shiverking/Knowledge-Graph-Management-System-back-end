package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateKG;
import com.group.KGMS.entity.CandidateKGInfo;
import com.group.KGMS.mapper.CandidateKGInfoMapper;
import com.group.KGMS.mapper.CandidateKGMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CandidateKgServiceImpl implements CandidateKgService {
    @Autowired
    CandidateKGMapper candidateKGMapper;
    @Autowired
    CandidateKGInfoMapper candidateKGInfoMapper;

    /**
     * 分页获取所有候选图谱
     *
     * @param pageNum
     * @param limitNum
     * @return
     */
    @Override
    public PageInfo<CandidateKG> getCandidateKgByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum, limitNum);
        PageInfo<CandidateKG> info = new PageInfo<CandidateKG>(candidateKGMapper.getAllCandidateKg());
        return info;
    }

    /**
     * 分页获取所有融合过得候选图谱
     *
     * @param pageNum
     * @param limitNum
     * @return
     */
    @Override
    public PageInfo<CandidateKG> getOldCandidateKgByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum, limitNum);
        PageInfo<CandidateKG> info = new PageInfo<CandidateKG>(candidateKGMapper.getAllOldCandidateKg());
        return info;
    }

    /**
     * 插入一个新的候选图谱。并返回其在数据库中的id
     *
     * @param name
     * @param creator
     * @param create_time
     * @param change_time
     * @param status
     * @param comment
     * @return
     */
    @Override
    public Long insertNewKG(String name, String creator, Date create_time, Date change_time, String status, String comment) {
        CandidateKG kg = new CandidateKG();
        kg.setName(name);
        kg.setChangeTime(change_time);
        kg.setCreateTime(create_time);
        kg.setComment(comment);
        kg.setStatus(status);
        kg.setCreator(creator);
        int tryInsert = candidateKGMapper.insertNewKG(kg);
        if (tryInsert == 1) {
            return kg.getId();
        }
        return (long) 0;
    }

    /**
     * 根据id获取候选图谱名称
     *
     * @param id
     * @return
     */
    @Override
    public String getNameById(Long id) {
        return candidateKGMapper.getNameById(id);
    }

    /**
     * 删除候选图谱
     *
     * @param id
     * @return
     */
    @Override
    public int deleteKgById(Long id) {
        return candidateKGMapper.deleteKgById(id);
    }

    /**
     * 根据id将融合后的候选图谱,将新换成旧
     *
     * @param id
     * @return
     */
    @Override
    public int updateKgToOldById(Long id) {
        return candidateKGMapper.updateKgfromNewtoOld(id);
    }

    /**
     * 插入一条新的INFO信息
     *
     * @param info
     * @return
     */
    @Override
    public int insertCandiateKGInfo(CandidateKGInfo info) {
        return candidateKGInfoMapper.insertNewKGInfo(info);
    }

    /**
     * 根据id查找INFO信息
     * @param id
     * @return
     */
    @Override
    public CandidateKGInfo getCandiateKGInfo(Long id) {
        return candidateKGInfoMapper.getInfoById(id);
    }
}
