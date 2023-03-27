package com.group.KGMS.service;

import com.group.KGMS.entity.Record;
import com.group.KGMS.entity.T_crawler;
import com.group.KGMS.utils.JsonResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CrawlerService {

    JsonResult findByCondition(String name, Integer status, Integer pageNum, Integer pageSize);

    Map<String, Long> statistic();

    T_crawler findpathBycid(Integer cid);

    void uploadCrawlerFile(MultipartFile file) throws Exception;

    void setcrawlstatusBycid(Integer cid,Integer status);

    void setrecordstatusBycid(String id,Integer status);

    String addrecord(Integer cid);

    JsonResult findrecordbycid(Integer pageNum, Integer pageSize,Integer cid);

    int addCrawler(T_crawler crawler);
}
