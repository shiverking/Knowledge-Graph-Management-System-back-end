package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.entity.Entity;
import com.group.KGMS.mapper.EntityMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class EntityServiceImpl implements EntityService {
    @Autowired
    EntityMapper entityMapper;
    @Resource
    SqlSessionFactory sqlSessionFactory;
    @Override
    public int insertNewEntity(List<CandidateTriple> candidateTripleList) {
        int result = 0;
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try{
            EntityMapper entityMapperTemp =  sqlSession.getMapper(EntityMapper.class);
            for(CandidateTriple triple:candidateTripleList){
                entityMapperTemp.insertNewEntity(triple.getHead(),triple.getHeadCategory());
                entityMapperTemp.insertNewEntity(triple.getTail(),triple.getTailCategory());
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
    public PageInfo<Entity> getAllEntityByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum,limitNum);
        PageInfo<Entity> info = new PageInfo<Entity>(entityMapper.selectAllEntity());
        return info;
    }
}
