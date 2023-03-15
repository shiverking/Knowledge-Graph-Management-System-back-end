package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.Entity;
import com.group.KGMS.mapper.EntityMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class EntityServiceImpl implements EntityService {
    @Autowired
    EntityMapper entityMapper;
    @Resource
    SqlSessionFactory sqlSessionFactory;

    /**
     * 判断实体是否存在
     *
     * @param name
     * @return
     */
    @Override
    public Long ifEntityExists(String name) {
        return entityMapper.ifEntityExists(name);
    }

    @Override
    public Long insertNewEntity(Entity entity) {
        if (entityMapper.insertNewEntity(entity) == 1) {
            return entity.getId();
        }
        return Long.valueOf(-1);
    }

    @Override
    public PageInfo<Entity> getAllEntityByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum, limitNum);
        PageInfo<Entity> info = new PageInfo<Entity>(entityMapper.selectAllEntity());
        return info;
    }

    @Override
    public int insertNewCorrelationForEachTriple(Long triple_id, Long head_id, Long tail_id, Long relation_id) {
        return entityMapper.insertNewCorrelationForEachTriple(triple_id, head_id, tail_id, relation_id);
    }

    @Override
    public Map<String, Object> selectSpecificCorrelation(Long triple_id) {
        return entityMapper.selectSpecificCorrelation(triple_id);
    }

    @Override
    public List<String> fuzzyQueryOfEntities(String restrict) {
        return entityMapper.selectEntityIntangibly(restrict);
    }
}
