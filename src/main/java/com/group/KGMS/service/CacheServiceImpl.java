package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    @Autowired
    TripleService tripleService;

    @Override
    public int insertNewMergeCache(List<Map<String, Object>> list) {
        int result = 1;
        try {
            SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            CacheMapper tmpMapper = openSession.getMapper(CacheMapper.class);
            for (int i = 0; i < list.size(); i++) {
                String head = String.valueOf(list.get(i).get("head"));
                String head_from = String.valueOf(list.get(i).get("head_from"));
                String relation = String.valueOf(list.get(i).get("relation"));
                String tail = String.valueOf(list.get(i).get("tail"));
                String tail_from = String.valueOf(list.get(i).get("tail_from"));
                String operation = String.valueOf(list.get(i).get("operation"));
                Date time = new Date();
                Double score = 0.0;
                if (operation.equals("插入") && list.get(i).get("score") != null) {
                    score = Double.valueOf(String.valueOf(list.get(i).get("score")));
                }
                tmpMapper.insertNewMergeCache(head, head_from, relation, tail, tail_from, score, operation, time);
            }
            openSession.commit();
            openSession.clearCache();
            openSession.close();
        } catch (Exception e) {
            System.out.println(e);
            result = 0;
        }
        return result;
    }

    /**
     * 分页获取融合的merge cache
     *
     * @param pageNum
     * @param limitNum
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> getMergeCacheByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum, limitNum);
        List<Map<String, Object>> mergeCacheList = cacheMapper.getAllMergeCache();
        PageInfo<Map<String, Object>> info = new PageInfo<Map<String, Object>>(mergeCacheList);
        return info;
    }

    /**
     * 插入一条新的补全缓存
     *
     * @param list
     * @return
     */
    @Override
    public int insertNewCompletionCache(List<Map<String, Object>> list) {
        int result = 1;
        try {
            SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            CacheMapper tmpMapper = openSession.getMapper(CacheMapper.class);
            for (int i = 0; i < list.size(); i++) {
                String head = String.valueOf(list.get(i).get("head"));
                String relation = String.valueOf(list.get(i).get("rel"));
                String tail = String.valueOf(list.get(i).get("tail"));
                String pred_form = String.valueOf(list.get(i).get("pred_form"));
                Double pred_prob = Double.valueOf(String.valueOf(list.get(i).get("pred_prob")));
                Date time = new Date();
                tmpMapper.insertNewCompletionCache(head, relation, tail, pred_form, pred_prob, time);
            }
            openSession.commit();
            openSession.clearCache();
            openSession.close();
        } catch (Exception e) {
            System.out.println(e);
            result = 0;
        }
        return result;
    }

    /**
     * 分页查找补全缓存
     *
     * @param pageNum
     * @param limitNum
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> getCompletionCacheByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum, limitNum);
        List<Map<String, Object>> completionCacheList = cacheMapper.getAllCompletionCache();
        PageInfo<Map<String, Object>> info = new PageInfo<Map<String, Object>>(completionCacheList);
        return info;
    }

    /**
     * 插入一条新的质量评估缓存
     *
     * @param list
     * @return
     */
    @Override
    public int insertNewEvaluationCache(List<Map<String, Object>> list) {
        int result = 1;
        try {
            SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            CacheMapper tmpMapper = openSession.getMapper(CacheMapper.class);
            for (int i = 0; i < list.size(); i++) {
                String head = String.valueOf(list.get(i).get("head"));
                String rel = String.valueOf(list.get(i).get("rel"));
                String tail = String.valueOf(list.get(i).get("tail"));
                String tail_typ = String.valueOf(list.get(i).get("tail_typ"));
                String head_typ = String.valueOf(list.get(i).get("head_typ"));
                String head_typ_new = String.valueOf(list.get(i).get("head_typ_new"));
                String tail_typ_new = String.valueOf(list.get(i).get("tail_typ_new"));
                String tail_new = String.valueOf(list.get(i).get("tail_new"));
                String head_new = String.valueOf(list.get(i).get("head_new"));
                String rel_new = String.valueOf(list.get(i).get("rel_new"));
                Long update_form = Long.parseLong(String.valueOf(list.get(i).get("update_form")));
                String error_typ = String.valueOf(list.get(i).get("error_typ"));
                tmpMapper.insertNewEvaluationCache(head, rel, tail, tail_typ, tail_typ_new, head_typ, head_typ_new, head_new, rel_new, tail_new, update_form, error_typ);
            }
            openSession.commit();
            openSession.clearCache();
            openSession.close();
        } catch (Exception e) {
            System.out.println(e);
            result = 0;
        }
        return result;
    }

    @Override
    public PageInfo<Map<String, Object>> getEvaluationCacheByPage(Integer pageNum, Integer limitNum) {
        PageHelper.startPage(pageNum, limitNum);
        List<Map<String, Object>> evaluationCacheList = cacheMapper.getAllEvaluationCache();
        PageInfo<Map<String, Object>> info = new PageInfo<Map<String, Object>>(evaluationCacheList);
        return info;
    }

    /**
     * 将操作记录迁移，并将合适的
     *
     * @param versionId
     * @return
     */
    @Override
    public List<Map<String, Object>> appendNewMergeToVersion(String versionId) {
        List<Map<String, Object>> mergeCacheList = cacheMapper.getAllMergeCache();
        try {
            SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            CacheMapper tmpMapper = openSession.getMapper(CacheMapper.class);
            for (int i = 0; i < mergeCacheList.size(); i++) {
                String head = String.valueOf(mergeCacheList.get(i).get("head"));
                String head_from = String.valueOf(mergeCacheList.get(i).get("head_from"));
                String relation = String.valueOf(mergeCacheList.get(i).get("relation"));
                String tail = String.valueOf(mergeCacheList.get(i).get("tail"));
                String tail_from = String.valueOf(mergeCacheList.get(i).get("tail_from"));
                String operation = String.valueOf(mergeCacheList.get(i).get("operation"));
                Date time = new Date();
                Double score = Double.valueOf(String.valueOf(mergeCacheList.get(i).get("score")));
                tmpMapper.insertNewMerge(versionId, head, head_from, relation, tail, tail_from, score, operation, time);
            }
            //清空缓存表
            tmpMapper.clearMergeCache();
            openSession.commit();
            openSession.clearCache();
            openSession.close();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return mergeCacheList;
    }

    @Override
    public List<Map<String, Object>> appendNewCompletionToVersion(String versionId) {
        List<Map<String, Object>> completionCacheList = cacheMapper.getAllCompletionCache();
        try {
            SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            CacheMapper tmpMapper = openSession.getMapper(CacheMapper.class);
            for (int i = 0; i < completionCacheList.size(); i++) {
                String head = String.valueOf(completionCacheList.get(i).get("head"));
                String relation = String.valueOf(completionCacheList.get(i).get("rel"));
                String tail = String.valueOf(completionCacheList.get(i).get("tail"));
                String pred_form = String.valueOf(completionCacheList.get(i).get("pred_form"));
                Double pred_prob = Double.valueOf(String.valueOf(completionCacheList.get(i).get("pred_prob")));
                Date time = new Date();
                tmpMapper.insertNewCompletion(versionId, head, relation, tail, pred_form, pred_prob, time);
            }
            tmpMapper.clearCompletionCache();
            openSession.commit();
            openSession.clearCache();
            openSession.close();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return completionCacheList;
    }

    @Override
    public List<Map<String, Object>> appendNewEvaluationToVersion(String versionId) {
        List<Map<String, Object>> evaluationCacheList = cacheMapper.getAllEvaluationCache();
        try {
            SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            CacheMapper tmpMapper = openSession.getMapper(CacheMapper.class);
            for (int i = 0; i < evaluationCacheList.size(); i++) {
                String head = String.valueOf(evaluationCacheList.get(i).get("head"));
                String rel = String.valueOf(evaluationCacheList.get(i).get("rel"));
                String tail = String.valueOf(evaluationCacheList.get(i).get("tail"));
                String tail_typ = String.valueOf(evaluationCacheList.get(i).get("tail_typ"));
                String head_typ = String.valueOf(evaluationCacheList.get(i).get("head_typ"));
                String head_typ_new = String.valueOf(evaluationCacheList.get(i).get("head_typ_new"));
                String tail_typ_new = String.valueOf(evaluationCacheList.get(i).get("tail_typ_new"));
                String tail_new = String.valueOf(evaluationCacheList.get(i).get("tail_new"));
                String head_new = String.valueOf(evaluationCacheList.get(i).get("head_new"));
                String rel_new = String.valueOf(evaluationCacheList.get(i).get("rel_new"));
                Long update_form = Long.parseLong(String.valueOf(evaluationCacheList.get(i).get("update_form")));
                String error_typ = String.valueOf(evaluationCacheList.get(i).get("error_typ"));
                tmpMapper.insertNewEvaluation(versionId, head, rel, tail, tail_typ, tail_typ_new, head_typ, head_typ_new, head_new, rel_new, tail_new, update_form, error_typ);
            }
            tmpMapper.clearEvaluationCache();
            openSession.commit();
            openSession.clearCache();
            openSession.close();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return evaluationCacheList;
    }

    /**
     * 删除所有融合，补全和质量评估改动
     *
     * @return
     */
    @Override
    public int deleteAll() {
        cacheMapper.clearMergeCache();
        cacheMapper.clearCompletionCache();
        cacheMapper.clearEvaluationCache();
        return 1;
    }
}
