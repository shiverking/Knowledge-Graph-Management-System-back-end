package com.group.KGMS.controller;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.Entity;
import com.group.KGMS.entity.Triple;
import com.group.KGMS.service.EntityService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class EntityController {
    @Autowired
    EntityService entityService;
    /**
     * 分页获取所有实体
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/entity/getAllEntity")
    @ResponseBody
    public JsonResult getTriplesFromSameKg(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageInfo<Entity> pageInfo = entityService.getAllEntityByPage(page,limit);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",pageInfo.getList(),pageInfo.getTotal());
    }
    /**
     * 模糊查找所有可能的实体集
     * @param info
     * @return
     */
    @PostMapping("/entity/getPossibleEntity")
    @ResponseBody
    public JsonResult getTriplesFromSameKg(@RequestBody Map<String, Object> info){
        String restrict = (String)info.get("value");
        List<String> result = entityService.fuzzyQueryOfEntities(restrict);
        return JsonResult.success("success",result);
    }
}
