package com.group.KGMS.service;

import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.entity.SemistructuredDataOriginal;
import com.group.KGMS.utils.JsonResult;

import java.util.List;
import java.util.Map;

public interface SemistructuredDataService {
    //获取爬虫对应原始半结构化数据
    JsonResult getSemistructuredDataBycid(Integer pageNum, Integer limitNum, Integer cid);
    JsonResult getSemistructuredDataByname(Integer pageNum, Integer limitNum, String name);
    List<CandidateTriple> converttotriples(Map<String, Object> semidata);
}
