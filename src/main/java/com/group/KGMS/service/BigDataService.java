package com.group.KGMS.service;

import com.group.KGMS.dto.OntologyDto;
import com.group.KGMS.utils.JsonResult;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-03-21  17:26
 * @Description:
 */

public interface BigDataService {
    JsonResult saveOnto(OntologyDto ontologyDto);

    JsonResult getClassList();

    JsonResult getRelationList();

    JsonResult getAttributeList();
}
