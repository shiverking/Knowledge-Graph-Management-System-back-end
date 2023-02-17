package com.group.KGMS.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface VersionMapper {
    int insertNewVersion(@Param("version_number")String version_number, @Param("name")String name, @Param("submit_time") Date submit_time, @Param("submitted_by")String submitted_by);
    List<Map<String,Object>> getAllVersion();
    int deleteVersionById(@Param("version_number")String version_number);
    //根据versionId 查找所有merge
    List<Map<String,Object>> getAllMergeById(String versionId);
    //根据versionId 查找所有completion
    List<Map<String,Object>> getAllCompletionById(String versionId);
    //根据versionId 查找所有evaluation
    List<Map<String,Object>> getAllEvaluationById(String versionId);
    //找到所有未同步的版本号
    List<String> getAllUnsynchronizedVersions();
    //根据版本号将同步列修改为1
    int updateSynchronizationById(@Param("version_number")String version_number);
    //获取版本号的个数
    int getNumOfVersion();
    //获取版本号的个数
    Date getLatestTimeOfVersion();
    //按照时间降序查找所有版本
    List<Map<String,Object>> getAllVersionByTimeDesc();
}
