package com.group.KGMS.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.KGMS.entity.CoreOntologyClass;
import com.group.KGMS.entity.CoreOntologyTriple;
import com.group.KGMS.entity.ThemeOntoClass;
import com.group.KGMS.mapper.ThemeOntoClassMapper;
import com.group.KGMS.utils.JsonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-05-10  21:46
 * @Description:
 */

@Service
public class ThemeOntoClassServiceImpl extends ServiceImpl<ThemeOntoClassMapper, ThemeOntoClass> implements ThemeOntoClassService{

    @Resource
    private CoreOntologyClassService coreOntologyClassService;

    @Resource
    private CoreOntologyTripleService coreOntologyTripleService;

    @Override
    public JsonResult getNodeByThemeOntoId(Integer themeOntoId) {
        List<ThemeOntoClass> themeOntoClassList = query().eq("theme_onto_id", themeOntoId).list();
        List<Integer> ids = new ArrayList<>();
        for (ThemeOntoClass themeOntoClass : themeOntoClassList) {
            ids.add(themeOntoClass.getClassId());
        }
        List<CoreOntologyClass> classes = coreOntologyClassService.listByIds(ids);
        return JsonResult.success(classes);
    }

    @Override
    public JsonResult getEdgeByThemeOntoId(Integer themeOntoId) {
        List<ThemeOntoClass> themeOntoClassList = query().eq("theme_onto_id", themeOntoId).list();
        List<Integer> ids = new ArrayList<>();
        for (ThemeOntoClass themeOntoClass : themeOntoClassList) {
            ids.add(themeOntoClass.getClassId());
        }
        List<CoreOntologyTriple> list = coreOntologyTripleService.query().in("head_class_id", ids).in("tail_class_id", ids).list();
        return JsonResult.success(list);
    }
}
