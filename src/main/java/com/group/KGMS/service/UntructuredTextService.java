package com.group.KGMS.service;

import com.group.KGMS.entity.UnstructuredText;

import java.util.List;

public interface UntructuredTextService {
    //将已抽取的文档的状态设置为“已抽取”
    void updateUnstructuredTextStatusById(List<String> idList);
    //分页获取所有非结构化文本
    List<UnstructuredText> getUnstructuredTextByPage(Integer pageNum, Integer limitNum);
    //查询所有数据的综述
    Long getSumOfUnstructuredText();
}
