package com.group.KGMS.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.group.KGMS.entity.ThemeOntoClass;
import com.group.KGMS.utils.JsonResult;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-05-10  21:46
 * @Description:
 */
public interface ThemeOntoClassService extends IService<ThemeOntoClass> {
    JsonResult getNodeByThemeOntoId(Integer themeOntoId);

    JsonResult getEdgeByThemeOntoId(Integer themeOntoId);
}


