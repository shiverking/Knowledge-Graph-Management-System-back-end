package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface CacheService {
    //增加一个新的融合缓存
    int insertNewMergeCache(List<Map<String, Object>> list);
    //分页获取所有merge cache
    PageInfo<Map<String,Object>> getMergeCacheByPage(Integer pageNum, Integer limitNum);
    //增加一个新的融合缓存
    int insertNewCompletionCache(List<Map<String, Object>> list);
    //分页获取所有merge cache
    PageInfo<Map<String,Object>> getCompletionCacheByPage(Integer pageNum, Integer limitNum);
}
