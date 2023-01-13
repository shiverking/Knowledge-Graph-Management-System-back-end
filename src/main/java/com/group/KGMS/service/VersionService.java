package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;
import java.util.Map;
//图谱版本管理
public interface VersionService {
    //增加一个新的版本
    String insertNewVersion(int mergeNumber,int completionNumber,int evaluationNumber);
    //分页获取所有版本信息
    PageInfo<Map<String,Object>> getVersionByPage(Integer pageNum, Integer limitNum);
    //根据id删除版本信息
    int deleteVersionById(String versionId);
    //分页获取所有merge信息
    PageInfo<Map<String,Object>> getMergeByPage(Integer pageNum, Integer limitNum,String verionId);
    //分页获取所有completion信息
    PageInfo<Map<String,Object>> getCompletionByPage(Integer pageNum, Integer limitNum,String verionId);
    //分页获取所有evaluation信息
    PageInfo<Map<String,Object>> getEvaluationByPage(Integer pageNum, Integer limitNum,String verionId);
}
