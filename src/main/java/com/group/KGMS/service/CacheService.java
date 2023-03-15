package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface CacheService {
    //增加一个新的融合缓存
    int insertNewMergeCache(List<Map<String, Object>> list);

    //分页获取所有融合缓存
    PageInfo<Map<String, Object>> getMergeCacheByPage(Integer pageNum, Integer limitNum);

    //增加一个新的补全缓存
    int insertNewCompletionCache(List<Map<String, Object>> list);

    //分页获取所有补全缓存
    PageInfo<Map<String, Object>> getCompletionCacheByPage(Integer pageNum, Integer limitNum);

    //增加一个新的评估缓存
    int insertNewEvaluationCache(List<Map<String, Object>> list);

    //分页获取所有评估缓存
    PageInfo<Map<String, Object>> getEvaluationCacheByPage(Integer pageNum, Integer limitNum);

    //将融合缓存追加到版本表
    List<Map<String, Object>> appendNewMergeToVersion(String versionId);

    //将融合缓存追加到版本表
    List<Map<String, Object>> appendNewCompletionToVersion(String versionId);

    //将融合缓存追加到版本表
    int appendNewEvaluationToVersion(String versionId);

    //将融合缓存追加到版本表
    int deleteAll();
}
