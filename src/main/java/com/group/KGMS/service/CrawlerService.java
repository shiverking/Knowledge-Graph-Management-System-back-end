package com.group.KGMS.service;

import com.group.KGMS.entity.Record;
import com.group.KGMS.entity.T_crawler;
import com.group.KGMS.utils.JsonResult;

import java.util.List;
import java.util.Map;

public interface CrawlerService {

    JsonResult findAll(Integer pageNum, Integer pageSize);

    JsonResult findByStatus(Integer status, Integer pageNum, Integer pageSize);

    JsonResult findByName(String name, Integer pageNum, Integer pageSize);

    Map<String, Long> statistic();

    T_crawler findpathBycid(Integer cid);

    void setcrawlstatusBycid(Integer cid,Integer status);

    void setrecordstatusBycid(String id,Integer status);

    String addrecord(Integer cid);

    JsonResult findrecordbycid(Integer pageNum, Integer pageSize,Integer cid);

}
