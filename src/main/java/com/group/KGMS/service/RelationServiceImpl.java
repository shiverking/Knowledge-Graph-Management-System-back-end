package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.Entity;
import com.group.KGMS.entity.Relation;
import com.group.KGMS.entity.Triple;
import com.group.KGMS.mapper.RelationMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class RelationServiceImpl implements RelationService {
    @Resource
    SqlSessionFactory sqlSessionFactory;
    @Autowired
    RelationMapper relationMapper;

    @Override
    public int insertNewRelation(List<Triple> triples) {
        int result = 0;
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try{
            RelationMapper relationMapperTemp =  sqlSession.getMapper(RelationMapper.class);
            for(Triple triple:triples){
                relationMapperTemp.insertNewRelation(triple.getRelation());
            }
            sqlSession.commit();
            sqlSession.clearCache();
            result = 1;
        }catch(Exception e){
            System.out.println(e);
        }finally{
            sqlSession.close();
        }
        return result;
    }

    @Override
    public PageInfo<Relation> getAllRelationByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum,limitNum);
        PageInfo<Relation> info = new PageInfo<Relation>(relationMapper.getAllRelation());
        return info;
    }
}
