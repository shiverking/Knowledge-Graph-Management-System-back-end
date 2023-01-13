package com.group.KGMS.service;

import com.group.KGMS.entity.CandidateOntologyClass;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2022-12-22  21:32
 * @Description: 候选本体类父子信息的service
 */
public interface CandidateOntologyClassService {
    CandidateOntologyClass getClassByCandidateOntologyId(Integer CandidateOntologyId);
}
