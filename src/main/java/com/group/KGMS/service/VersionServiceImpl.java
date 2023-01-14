package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.mapper.CacheMapper;
import com.group.KGMS.mapper.VersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class VersionServiceImpl implements VersionService {
    @Autowired
    VersionMapper versionMapper;
    @Autowired
    CacheMapper cacheMapper;
    @Override
    public String insertNewVersion(int mergeNumber,int completionNumber,int evaluationNumber) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy_MM_dd");
        String version_number= UUID.randomUUID().toString();;
        Date submit_time= new Date();
        String partOne = formater.format(submit_time);
        String partTwo = "M"+String.valueOf(mergeNumber);
        String partThree = "C"+String.valueOf(completionNumber);
        String partFour = "E"+String.valueOf(evaluationNumber);
        String name = partOne+"_"+partTwo+"_"+partThree+"_"+partFour;
        String submitted_by = "admin";
        if(versionMapper.insertNewVersion(version_number,name,submit_time,submitted_by)==1){
            return version_number;
        }
        //没有插入结果返回空
        return null;
    }
    @Override
    public PageInfo<Map<String, Object>> getVersionByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum,limitNum);
        List<Map<String,Object>> versionList = versionMapper.getAllVersion();
        PageInfo<Map<String,Object>> info = new PageInfo<Map<String,Object>>(versionList);
        return info;
    }

    @Override
    public int deleteVersionById(String versionId) {
        return versionMapper.deleteVersionById(versionId);
    }

    @Override
    public PageInfo<Map<String, Object>> getMergeByPage(Integer pageNum, Integer limitNum,String versionId) {
        PageHelper.startPage(pageNum,limitNum);
        List<Map<String,Object>> mergeList = versionMapper.getAllMergeById(versionId);
        PageInfo<Map<String,Object>> info = new PageInfo<Map<String,Object>>(mergeList);
        return info;
    }

    @Override
    public PageInfo<Map<String, Object>> getCompletionByPage(Integer pageNum, Integer limitNum,String versionId) {
        PageHelper.startPage(pageNum,limitNum);
        List<Map<String,Object>> completionList = versionMapper.getAllCompletionById(versionId);
        PageInfo<Map<String,Object>> info = new PageInfo<Map<String,Object>>(completionList);
        return info;
    }

    @Override
    public PageInfo<Map<String, Object>> getEvaluationByPage(Integer pageNum, Integer limitNum,String versionId) {
        PageHelper.startPage(pageNum,limitNum);
        List<Map<String,Object>> evaluationList = versionMapper.getAllEvaluationById(versionId);
        PageInfo<Map<String,Object>> info = new PageInfo<Map<String,Object>>(evaluationList);
        return info;
    }
}
