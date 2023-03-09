package com.group.KGMS.service;

import com.group.KGMS.entity.T_crawler;
import com.group.KGMS.utils.JsonResult;

import java.util.List;
import java.util.Map;

public interface CrawlerService {
    JsonResult findAll(Integer pageNum, Integer pageSize);
    List<T_crawler> findByStatus(int status);

    List<T_crawler> findByName(String name);

    Map<String, Long> statistic();
}
