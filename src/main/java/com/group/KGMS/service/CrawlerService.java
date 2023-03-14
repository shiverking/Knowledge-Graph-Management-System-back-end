package com.group.KGMS.service;

import com.group.KGMS.entity.T_crawler;
import com.group.KGMS.utils.JsonResult;

import java.util.List;
import java.util.Map;

public interface CrawlerService {

    JsonResult findAll(Integer pageNum, Integer pageSize);

    JsonResult findByStatus(Integer status, Integer pageNum, Integer pageSize);

    JsonResult findByName(String name, Integer pageNum, Integer pageSize);

    Map<String, Long> statistic();

}
