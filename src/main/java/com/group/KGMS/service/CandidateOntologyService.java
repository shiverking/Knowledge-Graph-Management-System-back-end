package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateOntology;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2022-12-02  16:46
 * @Description:
 */

public interface CandidateOntologyService {
    //分页获取所有候选本体
    PageInfo<CandidateOntology> getAllCandidateOntologyByPage(Integer pageNum, Integer limitNum);
}
