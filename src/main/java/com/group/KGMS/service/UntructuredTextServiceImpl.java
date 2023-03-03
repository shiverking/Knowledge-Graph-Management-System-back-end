package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.mapper.UnstructuredTextMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UntructuredTextServiceImpl implements UntructuredTextService {
    @Autowired
    UnstructuredTextMapper unstructuredTextMapper;

    @Override
    public PageInfo<Map<Object, String>> getUnstructuredTextByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum, limitNum);
        List<Map<Object, String>> textList = unstructuredTextMapper.getAllUnStructuredText();
        PageInfo<Map<Object, String>> info = new PageInfo<Map<Object, String>>(textList);
        return info;
    }
}
