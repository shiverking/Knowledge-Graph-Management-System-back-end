package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.Triple;
import com.group.KGMS.mapper.CacheMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CacheServiceImpl implements CacheService {
    @Autowired
    CacheMapper cacheMapper;
    @Resource
    SqlSessionFactory sqlSessionFactory;
    @Override
    public int insertNewMergeCache(List<Map<String, Object>> list) {
        int result = 1;
        try {
            SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            CacheMapper tmpMapper = openSession.getMapper(CacheMapper.class);
            for(int i=0;i<list.size();i++){
                String head = String.valueOf(list.get(i).get("head"));
                String head_from = String.valueOf(list.get(i).get("head_from"));
                String relation = String.valueOf(list.get(i).get("relation"));
                String tail = String.valueOf(list.get(i).get("tail"));
                String tail_from = String.valueOf(list.get(i).get("tail_from"));
                String operation = String.valueOf(list.get(i).get("operation"));
                Date time = new Date();
                Double score = 0.0;
                if(operation.equals("插入")){
                    score = Double.valueOf(String.valueOf(list.get(i).get("score")));
                }
                tmpMapper.insertNewMergeCache(head,head_from,relation,tail,tail_from,score,operation,time);
            }
            openSession.commit();
            openSession.clearCache();
            openSession.close();
        } catch (Exception e){
            result = 0;
        }
        return result;
    }

    /**
     * 分页获取融合的merge cache
     * @param pageNum
     * @param limitNum
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> getMergeCacheByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum,limitNum);
        List<Map<String,Object>> mergeCacheList = cacheMapper.getAllMergeCache();
        PageInfo<Map<String,Object>> info = new PageInfo<Map<String,Object>>(mergeCacheList);
        return info;
    }

    /**
     * 插入一条新的补全缓存
     * @param list
     * @return
     */
    @Override
    public int insertNewCompletionCache(List<Map<String, Object>> list){
        int result = 1;
        try {
            SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            CacheMapper tmpMapper = openSession.getMapper(CacheMapper.class);
            for(int i=0;i<list.size();i++){
                String head = String.valueOf(list.get(i).get("head"));
                String relation = String.valueOf(list.get(i).get("rel"));
                String tail = String.valueOf(list.get(i).get("tail"));
                String pred_form = String.valueOf(list.get(i).get("pred_form"));
                Double pred_prob = Double.valueOf(String.valueOf(list.get(i).get("pred_prob")));
                Date time = new Date();
                tmpMapper.insertNewCompletionCache(head,relation,tail,pred_form,pred_prob,time);
            }
            openSession.commit();
            openSession.clearCache();
            openSession.close();
        } catch (Exception e){
            result = 0;
        }
        return result;
    }

    /**
     * 分页查找补全缓存
     * @param pageNum
     * @param limitNum
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> getCompletionCacheByPage(Integer pageNum, Integer limitNum){
        PageHelper.startPage(pageNum,limitNum);
        List<Map<String,Object>> completionCacheList = cacheMapper.getAllCompletionCache();
        PageInfo<Map<String,Object>> info = new PageInfo<Map<String,Object>>(completionCacheList);
        return info;
    }
}
