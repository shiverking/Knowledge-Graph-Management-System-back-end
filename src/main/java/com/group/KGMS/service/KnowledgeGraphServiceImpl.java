package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.Triple;
import com.group.KGMS.repository.mapper.KnowledgeGraphMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.List;

@Service
public class KnowledgeGraphServiceImpl implements KnowledgeGraphService {
    @Autowired
    KnowledgeGraphMapper kgMapper;

    /**
     * 分页返回所有三元组
     * @param tableName 表名
     * @param pageNum 页数
     * @param limitNum 每页限制数
     * @return
     */
    @Override
    public PageInfo<Triple> getAllTriplesByPage(String tableName, Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum,limitNum);
        PageInfo<Triple> info = new PageInfo<Triple>(kgMapper.getAllTriplesFromTable(tableName));
        return info;
    }

    /**
     * 直接返回所有三元组
     * @param tableName
     * @return
     */

    @Override
    public List<Triple> getAllTriples(String tableName) {
        return kgMapper.getAllTriplesFromTable(tableName);
    }
}
