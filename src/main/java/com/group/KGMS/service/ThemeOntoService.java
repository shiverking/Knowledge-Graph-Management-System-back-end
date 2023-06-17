package com.group.KGMS.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.group.KGMS.entity.ThemeOnto;
import com.group.KGMS.utils.JsonResult;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-05-10  21:43
 * @Description:
 */
public interface ThemeOntoService extends IService<ThemeOnto> {
    JsonResult getThemeOntoList();
}

