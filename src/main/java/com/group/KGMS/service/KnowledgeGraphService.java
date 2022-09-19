package com.group.KGMS.service;

import com.group.KGMS.entity.Triple;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface KnowledgeGraphService {
    List<Triple> getAllTriples(String tableName);
    PageInfo<Triple> getAllTriplesByPage(String tableName,Integer pageNum, Integer limitNum);
}
