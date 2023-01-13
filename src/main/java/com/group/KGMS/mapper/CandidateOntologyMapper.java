package com.group.KGMS.mapper;

import com.group.KGMS.entity.CandidateOntology;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.mapper
 * @Author: zt
 * @CreateTime: 2022-12-02  16:30
 * @Description:
 */

@Mapper
public interface CandidateOntologyMapper {
    //获取所有的候选本体
    List<CandidateOntology> getAllCandidateOntology();
}
