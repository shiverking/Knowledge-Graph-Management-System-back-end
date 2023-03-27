package com.group.KGMS.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.group.KGMS.entity.CoreOntologyAttribute;
import com.group.KGMS.utils.JsonResult;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-03-23  12:21
 * @Description:
 */

public interface CoreOntologyAttributeService extends IService<CoreOntologyAttribute> {
    /*
     * @Description: 根据类别id获取属性列表
     * @Author: zt
     * @Date: 2023/3/23 13:05
     * @param: [id 类别id]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    JsonResult getAttributesByClassId(Integer id);

    JsonResult addAttribute(CoreOntologyAttribute newAttribute);

    JsonResult deleteAttribute(Integer attributeId);
}
