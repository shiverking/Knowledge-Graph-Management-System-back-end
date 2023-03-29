package com.group.KGMS.mapper;

import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.entity.Triple;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface TripleMapper {
    List<Triple> getAllTriple();

    //将候选三元组变为三元组
    int insertCandidateTripleIntoTriple(Triple triple);

    //从一个候选图谱中获得所有的三元组
    List<Triple> getAllTriplesFromSameCandidateKg(@Param("candidateId") Long candidateId);

    //删除原有的候选图谱
    int deleteCandidateTriples(CandidateTriple candidateTripleTriple);

    //更新三元组所属的候选图谱
    int updateCandidateIdOfTriple(@Param("tripleId") Long tripleId, @Param("newCandidateId") Long newCandidateId);

    //向核心图谱插入融合改动
    int insertMergeChange(@Param("head") String head, @Param("headCategory") String headCategory,@Param("relation") String relation, @Param("tail") String tail, @Param("tailCategory") String tailCategory, @Param("time") Date time);

    //向核心图谱插入补全改动
    int insertCompletionChange(@Param("head") String head, @Param("relation") String relation, @Param("tail") String tail, @Param("headCategory") String headCategory,@Param("tailCategory") String tailCategory,@Param("time") Date time);

    //在核心图谱中修改头实体
    int updateEntityHeadName(@Param("oldName")String oldName, @Param("newName")String newName);

    //在核心图谱中修改尾实体
    int updateEntityTailName(@Param("oldName")String oldName, @Param("newName")String newName);

    //质量评估的策略一，删除
    int evaluationDelete(@Param("head") String head,@Param("relation") String relation, @Param("tail") String tail);
    //质量评估的策略二，更新实体
    int evaluationUpdateEntityCategory(@Param("head") String head, @Param("relation") String relation, @Param("tail") String tail, @Param("newHeadCategory") String newHeadCategory, @Param("newTailCategory") String newTailCategory);
    //质量评估的策略三，更新关系
    int evaluationUpdateRelation(@Param("head") String head,@Param("relation") String relation, @Param("tail") String tail,@Param("newRel") String newRel);
}
