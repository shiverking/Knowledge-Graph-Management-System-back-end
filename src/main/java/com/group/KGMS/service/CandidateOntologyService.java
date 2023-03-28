package com.group.KGMS.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateOntology;
import com.group.KGMS.utils.JsonResult;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2022-12-02  16:46
 * @Description:
 */

public interface CandidateOntologyService extends IService<CandidateOntology> {
    //分页获取所有候选本体
    PageInfo<CandidateOntology> getAllCandidateOntologyByPage(Integer pageNum, Integer limitNum);

    //添加本体
    JsonResult saveOnto(CandidateOntology newOnto);

    /*
     * @Description: 结构化数据生成候选本体
     * @Author: zt
     * @Date: 2023/3/28 13:15
     * @param: []
     * @return: com.group.KGMS.utils.JsonResult
     **/
    JsonResult structuredDataToOntology(String candidateOntoName);
}
