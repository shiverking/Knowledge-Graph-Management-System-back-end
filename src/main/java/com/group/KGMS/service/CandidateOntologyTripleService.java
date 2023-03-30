package com.group.KGMS.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.group.KGMS.entity.CandidateOntologyTriple;

import java.io.IOException;
import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-03-07  00:30
 * @Description:
 */

public interface CandidateOntologyTripleService extends IService<CandidateOntologyTriple> {

    /*
     * @Description: 获取某个候选本体的关系列表
     * @Author: zt
     * @Date: 2023/3/7 0:56
     * @param: [candidateOntologyId]
     * @return: java.util.List<com.group.KGMS.entity.CandidateOntologyTriple>
     **/
    List<CandidateOntologyTriple> getAllRelationByCandidateOntologyId(Integer candidateOntologyId);

    /*
     * @Description: 向某个候选本体中的两个指定的类中间添加关系
     * @Author: zt
     * @Date: 2023/3/7 11:43
     * @param: [candidateOntologyTriple]
     * @return: void
     **/
    void saveRelation(String headClassName, String relation, String tailClassName, Integer candidateOntologyId) throws IOException;

    /*
     * @Description: 删除某个候选本体中的某条关系
     * @Author: zt
     * @Date: 2023/3/8 12:06
     * @param: [headClassId, tailClassId, relation, candidateOntologyId]
     * @return: void
     **/
    void removeRelation(Integer headClassId, Integer tailClassId, String relation, Integer candidateOntologyId) throws IOException;

}
