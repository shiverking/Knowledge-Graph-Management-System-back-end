package com.group.KGMS.service;

import com.group.KGMS.entity.T_crawler;

import java.util.List;
import java.util.Map;

public interface CrawlerService {
    List<T_crawler> findAll();
    List<T_crawler> findByStatus(int status);

    List<T_crawler> findByName(String name);

    Map<String, Long> statistic();
}
