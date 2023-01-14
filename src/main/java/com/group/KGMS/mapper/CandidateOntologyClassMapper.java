package com.group.KGMS.mapper;

import com.group.KGMS.entity.CandidateOntologyClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.mapper
 * @Author: zt
 * @CreateTime: 2022-12-22  21:36
 * @Description: 候选本体的类相关信息的mapper
 */

@Mapper
public interface CandidateOntologyClassMapper {
    //根据候选本体的id获取所有的类别信息
    List<CandidateOntologyClass> getClassByCandidateOntologyId(@Param("CandidateOntologyId") Integer CandidateOntologyId);
}
