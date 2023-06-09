package com.group.KGMS.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.KGMS.entity.ThemeOnto;
import com.group.KGMS.mapper.ThemeOntoMapper;
import com.group.KGMS.utils.JsonResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-05-10  21:44
 * @Description:
 */

@Service
public class ThemeOntoServiceImpl extends ServiceImpl<ThemeOntoMapper, ThemeOnto> implements ThemeOntoService {

    @Override
    public JsonResult getThemeOntoList() {
        List<ThemeOnto> themeOntoList = list();
        return JsonResult.success(themeOntoList);
    }

}
