package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.repository.GraphNodeRepository;
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
    @Autowired
    GraphNodeRepository graphNodeRepository;
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
    public PageInfo<Map<String, Object>> getVersionByPageByTimeDesc(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum,limitNum);
        List<Map<String,Object>> versionList = versionMapper.getAllVersionByTimeDesc();
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
    //同步所有未同步的版本
    @Override
    public void synchronizeVersion() {
        int res = 0;
        List<String> unsynchronized = versionMapper.getAllUnsynchronizedVersions();
        //开始同步(目前只同步补全和链接预测两种)
        for(int i=0;i<unsynchronized.size();i++){
            String versionId = unsynchronized.get(i);
            List<Map<String,Object>> merge = versionMapper.getAllMergeById(versionId);
            for(Map<String,Object> map : merge){
                //先写出没有对齐的情况
                if(map.get("head_from").toString().equals("null")&&map.get("tail_from").toString().equals("null")&&map.get("operation").equals("插入")){
                    String head = map.get("head").toString();
                    String headType = "";
                    String relation = map.get("relation").toString();
                    //暂时没有属性
                    String tail = map.get("tail").toString();
                    String tailType = "";
                    //首先建立节点
                    graphNodeRepository.creatNode(head,headType);
                    graphNodeRepository.creatNode(tail,tailType);
                    //然后建立关系
                    graphNodeRepository.creatRelationByName(head,relation,tail);
                }
            }
            //修改状态
            versionMapper.updateSynchronizationById(versionId);
        }
    }

    @Override
    public int getNumOfVersion() {
        return versionMapper.getNumOfVersion();
    }

    @Override
    public String getLatestTimeOfVersion() {
        Date latest = versionMapper.getLatestTimeOfVersion();
        String strDateFormat = "yyyy-MM-dd HH:mm";
        SimpleDateFormat fomatter = new SimpleDateFormat(strDateFormat);
        return  fomatter.format(latest);
    }
}
