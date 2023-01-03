package com.group.KGMS.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface CacheMapper {
    //将临时记录加入缓存表
    int insertNewMergeCache(@Param("head")String head,@Param("head_from")String head_from,@Param("relation")String relation,@Param("tail")String tail,@Param("tail_from")String tail_from,
                            @Param("score")double score,@Param("operation")String operation,@Param("time") Date time);
    List<Map<String,Object>> getAllMergeCache();
    //将补全临时记录加入缓存表
    int insertNewCompletionCache(@Param("head")String head,@Param("rel")String rel,@Param("tail")String tail,@Param("pred_form") String pred_form,
                                 @Param("pred_prob")Double pred_prob,@Param("time") Date time);
    List<Map<String,Object>> getAllCompletionCache();
}
