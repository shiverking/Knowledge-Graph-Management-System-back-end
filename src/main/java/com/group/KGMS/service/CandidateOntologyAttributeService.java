package com.group.KGMS.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.group.KGMS.entity.CandidateOntologyAttribute;
import com.group.KGMS.utils.JsonResult;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-03-23  15:55
 * @Description:
 */

public interface CandidateOntologyAttributeService extends IService<CandidateOntologyAttribute> {
    JsonResult getAttribute(Integer classId, Integer belongCandidateOntologyId);

    JsonResult addAttribute(CandidateOntologyAttribute newAttribute);

    JsonResult deleteAttribute(Integer attributeId);
}
