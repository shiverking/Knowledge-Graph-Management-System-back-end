package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.Triple;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface UntructuredTextService {
    //分页获取所有的非结构化文本
    PageInfo<Map<Object,String>> getUnstructuredTextByPage(Integer pageNum, Integer limitNum);
}
