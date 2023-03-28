package com.group.KGMS.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface CacheMapper {
    //将临时记录加入缓存表
    int insertNewMergeCache(@Param("head") String head, @Param("headCategory")String headCategory,@Param("head_from") String head_from, @Param("relation") String relation, @Param("tail") String tail,@Param("tailCategory")String tailCategory ,@Param("tail_from") String tail_from,
                            @Param("score") double score, @Param("operation") String operation, @Param("time") Date time);

    List<Map<String, Object>> getAllMergeCache();

    int clearMergeCache();

    //插入版本表
    int insertNewMerge(@Param("version_id") String version_id, @Param("head") String head, @Param("headCategory")String headCategory,@Param("head_from") String head_from, @Param("relation") String relation, @Param("tail") String tail,@Param("tailCategory")String tailCategory, @Param("tail_from") String tail_from,
                       @Param("score") double score, @Param("operation") String operation, @Param("time") Date time);

    //将补全临时记录加入缓存表
    int insertNewCompletionCache(@Param("head") String head, @Param("headCategory")String headCategory,@Param("rel") String rel, @Param("tail") String tail, @Param("tailCategory")String tailCategory, @Param("time") Date time);

    List<Map<String, Object>> getAllCompletionCache();

    int clearCompletionCache();

    int insertNewCompletion(@Param("version_id") String version_id, @Param("head") String head, @Param("rel") String rel, @Param("tail") String tail, @Param("time") Date time);

    //将质量评估记录加入缓存表
    int insertNewEvaluationCache(@Param("head") String head, @Param("rel") String rel, @Param("tail") String tail, @Param("tail_typ") String tail_typ, @Param("tail_typ_new") String tail_typ_new, @Param("head_typ") String head_typ,
                                 @Param("head_typ_new") String head_typ_new, @Param("head_new") String head_new, @Param("rel_new") String rel_new, @Param("tail_new") String tail_new, @Param("update_form") Long update_form,
                                 @Param("error_typ") String error_typ);

    List<Map<String, Object>> getAllEvaluationCache();

    int clearEvaluationCache();

    int insertNewEvaluation(@Param("version_id") String version_id, @Param("head") String head, @Param("rel") String rel, @Param("tail") String tail, @Param("tail_typ") String tail_typ, @Param("tail_typ_new") String tail_typ_new, @Param("head_typ") String head_typ,
                            @Param("head_typ_new") String head_typ_new, @Param("head_new") String head_new, @Param("rel_new") String rel_new, @Param("tail_new") String tail_new, @Param("update_form") Long update_form,
                            @Param("error_typ") String error_typ);
}
