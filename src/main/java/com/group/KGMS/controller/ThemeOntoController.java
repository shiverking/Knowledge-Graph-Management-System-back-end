package com.group.KGMS.controller;

import com.group.KGMS.service.ThemeOntoClassService;
import com.group.KGMS.service.ThemeOntoService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.controller
 * @Author: zt
 * @CreateTime: 2023-05-10  21:47
 * @Description:
 */

@Controller
@ResponseBody
@RequestMapping("/themeOnto")
public class ThemeOntoController {

    @Resource
    private ThemeOntoService themeOntoService;

    @Resource
    private ThemeOntoClassService themeOntoClassService;

    @GetMapping("/list")
    public JsonResult getThemeOntoList(){
        return themeOntoService.getThemeOntoList();
    }

    @GetMapping("/graphNode/{id}")
    public JsonResult getThemeOntoGraphNode(@PathVariable("id") Integer themeOntoId){
        return themeOntoClassService.getNodeByThemeOntoId(themeOntoId);
    }

    @GetMapping("/graphEdge/{id}")
    public JsonResult getThemeOntoGraphEdge(@PathVariable("id") Integer themeOntoId){
        return themeOntoClassService.getEdgeByThemeOntoId(themeOntoId);
    }

}
