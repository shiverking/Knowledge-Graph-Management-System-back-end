package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.mapper.CandidateTripleMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CandidateTripleServiceImpl implements CandidateTripleService {
    @Autowired
    CandidateTripleMapper candidateTripleMapper;

    @Resource
    SqlSessionFactory sqlSessionFactory;

    @Override
    public PageInfo<CandidateTriple> getCandidateTripleByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum, limitNum);
        PageInfo<CandidateTriple> info = new PageInfo<CandidateTriple>(candidateTripleMapper.getTriplesList());
        return info;
    }

    @Override
    public PageInfo<CandidateTriple> getTriplesListWithTimeLimitByPage(Integer pageNum, Integer limitNum, Date startTime, Date endTime) {
        PageHelper.startPage(pageNum, limitNum);
        PageInfo<CandidateTriple> info = new PageInfo<CandidateTriple>(candidateTripleMapper.getTriplesListWithTimeLimit(startTime, endTime));
        return info;
    }

    @Override
    public PageInfo<CandidateTriple> getTriplesListWithSourceLimitByPage(Integer pageNum, Integer limitNum, String source) {
        PageHelper.startPage(pageNum, limitNum);
        PageInfo<CandidateTriple> info = new PageInfo<CandidateTriple>(candidateTripleMapper.getTriplesListWithSourceLimit(source));
        return info;
    }

    @Override
    public PageInfo<CandidateTriple> getTriplesListWithSourceAnTimeLimitByPage(Integer pageNum, Integer limitNum, Date startTime, Date endTime, String source) {
        PageHelper.startPage(pageNum, limitNum);
        PageInfo<CandidateTriple> info = new PageInfo<CandidateTriple>(candidateTripleMapper.getTriplesListWithSourceAndTimeLimit(startTime, endTime, source));
        return info;
    }

    @Override
    public int deleteCandidateTripleById(Long id) {
        return candidateTripleMapper.deleteCandidateTripleById(id);
    }

    @Override
    public int updateCandidateTripleById(Long id, String head, String relation, String tail, String headCategory, String tailCategory) {
        if (headCategory.equals("暂无")) {
            headCategory = "";
        }
        if (tailCategory.equals("暂无")) {
            tailCategory = "";
        }
        return candidateTripleMapper.updateCandidateTripleById(id, head, relation, tail, headCategory, tailCategory);
    }

    /**
     * 插入候选图谱
     * @param candidateTripleList
     * @return
     */
    @Override
    public int insertNewCandidateTriplesBatch(List<CandidateTriple> candidateTripleList) {
        int result = 1;
        try {
            SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            CandidateTripleMapper candidateTripleMapper = openSession.getMapper(CandidateTripleMapper.class);
            for (CandidateTriple triple : candidateTripleList) {
                candidateTripleMapper.insertCandidateTriples(triple.getHead(),triple.getRelation(),triple.getTail(),triple.getHeadCategory(),triple.getTail(),triple.getStatus(),triple.getSource(),triple.getTime());
            }
            openSession.commit();
            openSession.clearCache();
            openSession.close();
        } catch (Exception e) {
            result = 0;
            System.out.println(e);
        }
        return result;
    }

    @Override
    public boolean updateCandidateTripleHeadCategoryByName(String name, String category) {
        return candidateTripleMapper.updateCandidateTripleHeadCategoryByName(name,category)|| candidateTripleMapper.updateCandidateTripleTailCategoryByName(name,category);

    }
}
