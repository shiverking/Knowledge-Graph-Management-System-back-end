package com.group.KGMS.service;

import com.group.KGMS.entity.CandidateOntologyClass;
import com.group.KGMS.mapper.CandidateOntologyClassMapper;
import com.group.KGMS.utils.TreeJsonCandidateOntologyClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2022-12-22  21:32
 * @Description: 候选本体类别信息的service，主要服务于父子关系
 */

@Service
public class CandidateOntologyClassServiceImpl implements CandidateOntologyClassService {

    @Autowired
    CandidateOntologyClassMapper candidateOntologyClassMapper;

    /*
     * @Description: 根据候选本体的id，获取一个根类，根类里用孩子列表来进行存储父子关系，用树形json工具类进行处理
     * @Author: zt
     * @Date: 2022/12/22 21:54
     * @param: [candidateOntologyId]
     * @return: com.group.KGMS.entity.CandidateOntologyClass
     **/
    @Override
    public CandidateOntologyClass getClassByCandidateOntologyId(Integer candidateOntologyId) {
        List<CandidateOntologyClass> ontologyClassList = candidateOntologyClassMapper.getClassByCandidateOntologyId(candidateOntologyId);
        TreeJsonCandidateOntologyClass treeJsonUtil = TreeJsonCandidateOntologyClass.getInstance();
        CandidateOntologyClass rootClass = treeJsonUtil.enquireTree(ontologyClassList);
        return rootClass;
    }
}
