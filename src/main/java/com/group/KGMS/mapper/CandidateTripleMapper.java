package com.group.KGMS.mapper;

import com.group.KGMS.entity.CandidateTriple;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface CandidateTripleMapper {
    List<CandidateTriple> getTriplesList();

    //带有时间限制的候选三元组
    List<CandidateTriple> getTriplesListWithTimeLimit(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    //带有来源限制的候选三元组
    List<CandidateTriple> getTriplesListWithSourceLimit(@Param("source") String source);

    //带有时间和来源限制的候选三元组
    List<CandidateTriple> getTriplesListWithSourceAndTimeLimit(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("source") String source);

    //根据Id删除某个候选三元组
    int deleteCandidateTripleById(@Param("id") Long id);

    //根据Id更新候选三元组
    int updateCandidateTripleById(@Param("id") Long id, @Param("head") String head, @Param("relation") String relation, @Param("tail") String tail, @Param("head_category") String head_category, @Param("tail_category") String tail_category);

    //插入新的候选三元组
    int insertCandidateTriples(@Param("head") String head, @Param("relation") String relation, @Param("tail") String tail, @Param("head_category") String head_category, @Param("tail_category") String tail_category, @Param("status") String status, @Param("source") String source,@Param("time") Date time);

    //插入新的属性三元组
    int insertCandidateAttributes(@Param("head") String head, @Param("headCategory") String headCategory,@Param("attributeName") String attributeName, @Param("attributeValue") String attributeValue, @Param("source") String source, @Param("time") Date time);

    boolean updateCandidateTripleHeadCategoryByName(@Param("name") String name, @Param("category") String category);
    boolean updateCandidateTripleTailCategoryByName(@Param("name") String name, @Param("category") String category);
}
