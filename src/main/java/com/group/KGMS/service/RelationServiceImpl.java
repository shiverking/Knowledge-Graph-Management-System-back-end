package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.Relation;
import com.group.KGMS.mapper.RelationMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class RelationServiceImpl implements RelationService {
    @Autowired
    RelationMapper relationMapper;
    @Resource
    SqlSessionFactory sqlSessionFactory;
    /**
     * 判断实体是否已经存在
     * @param name
     * @return
     */
    @Override
    public Long ifRelationExists(String name) {
        return relationMapper.ifRelationExists(name);
    }

    @Override
    public Long insertNewRelation(Relation relation) {
        if(relationMapper.insertNewRelation(relation)==1){
            return relation.getId();
        }
        return Long.valueOf(-1);
    }

    @Override
    public PageInfo<Relation> getAllRelationByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum,limitNum);
        PageInfo<Relation> info = new PageInfo<Relation>(relationMapper.getAllRelation());
        return info;
    }
}
