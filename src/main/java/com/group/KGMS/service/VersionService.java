package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
//图谱版本管理
public interface VersionService {
    //增加一个新的版本
    String insertNewVersion(int mergeNumber,int completionNumber,int evaluationNumber);
    //分页获取所有版本信息
    PageInfo<Map<String,Object>> getVersionByPage(Integer pageNum, Integer limitNum);
    //时间降序分页获取所有版本信息
    PageInfo<Map<String,Object>> getVersionByPageByTimeDesc(Integer pageNum, Integer limitNum);
    //根据id删除版本信息
    int deleteVersionById(String versionId);
    //分页获取所有merge信息
    PageInfo<Map<String,Object>> getMergeByPage(Integer pageNum, Integer limitNum,String verionId);
    //分页获取所有completion信息
    PageInfo<Map<String,Object>> getCompletionByPage(Integer pageNum, Integer limitNum,String verionId);
    //分页获取所有evaluation信息
    PageInfo<Map<String,Object>> getEvaluationByPage(Integer pageNum, Integer limitNum,String verionId);
    //同步所有未同步的版本
    void synchronizeVersion();
    //获取更新的版本次数
    int getNumOfVersion();
    //获取更新的版本次数
    String getLatestTimeOfVersion();

}
