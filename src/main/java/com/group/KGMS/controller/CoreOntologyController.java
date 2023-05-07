package com.group.KGMS.controller;

import com.alibaba.fastjson2.JSONObject;
import com.group.KGMS.entity.CoreOntologyAttribute;
import com.group.KGMS.entity.CoreOntologyClass;
import com.group.KGMS.entity.CoreOntologyTriple;
import com.group.KGMS.service.CoreOntologyAttributeService;
import com.group.KGMS.service.CoreOntologyClassService;
import com.group.KGMS.service.CoreOntologyTripleService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.controller
 * @Author: zt
 * @CreateTime: 2023-03-08  15:57
 * @Description:
 */

@RestController
@RequestMapping("/coreOntology")
public class CoreOntologyController {

    @Autowired
    private CoreOntologyClassService coreOntologyClassService;

    @Autowired
    private CoreOntologyTripleService coreOntologyTripleService;

    @Resource
    private CoreOntologyAttributeService coreOntologyAttributeService;


    /*
     * @Description: 获取核心本体树形结构数据
     * @Author: zt
     * @Date: 2023/3/9 0:24
     * @param: []
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @GetMapping("/getTreeData")
    public JsonResult getTreeData(){
        CoreOntologyClass rootClass = coreOntologyClassService.getRootClass();
//        String treeJson = JSONObject.toJSONString(rootClass);
//        System.out.println("treeJson = " + treeJson);
        return JsonResult.success("success", rootClass);
    }

    @GetMapping("/getOntologyData")
    public JsonResult getOntologyData(){
        return coreOntologyClassService.getOntologyData();
    }

//    @GetMapping("/getTreeData")
//    public JsonResult getTreeData(){
//        List<CoreOntologyClass> classList = coreOntologyClassService.list();
//        String json = JSONObject.toJSONString(classList);
//        return JsonResult.success("success", classList);
//    }

    /*
     * @Description: 向核心本体中添加新的类
     * @Author: zt
     * @Date: 2023/3/9 0:33
     * @param: [newClass]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @PostMapping("/addClass")
    public JsonResult addClass(@RequestBody CoreOntologyClass newClass){
        System.out.println("newClass = " + newClass);
        try {
            coreOntologyClassService.save(newClass.getName(), newClass.getParentId());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error("添加失败");
        }
        return JsonResult.success("success");
    }

    /*
     * @Description: 删除候选本体中的某个类
     * @Author: zt
     * @Date: 2023/3/9 10:02
     * @param: [coreOntologyClass]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @DeleteMapping("/deleteClass")
    public JsonResult deleteClass(@RequestBody CoreOntologyClass coreOntologyClass){
        try {
            coreOntologyClassService.remove(coreOntologyClass.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error("删除失败，这个类有子类");
        }
        return JsonResult.success("success");
    }

    /*
     * @Description: 获取核心本体中的类列表
     * @Author: zt
     * @Date: 2023/3/9 13:06
     * @param: []
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @GetMapping("/getGraphNode")
    public JsonResult getGraphNode(){
        List<CoreOntologyClass> classList = coreOntologyClassService.list();
        return JsonResult.success("success", classList);
    }

    /*
     * @Description: 获取核心本体中的关系列表
     * @Author: zt
     * @Date: 2023/3/9 13:06
     * @param: []
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @GetMapping("/getGraphEdge")
    public JsonResult getGraphEdge(){
        List<CoreOntologyTriple> tripleList = coreOntologyTripleService.list();
        tripleList.forEach(System.out::println);
        return JsonResult.success("success", tripleList);
    }

    /*
     * @Description: 向核心本体中添加关系
     * @Author: zt
     * @Date: 2023/3/9 13:06
     * @param: [newRelation]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @PostMapping("/addRelation")
    public JsonResult addRelation(@RequestBody CoreOntologyTriple newRelation){
        try {
            coreOntologyTripleService.saveRelation(newRelation.getHeadClassName(), newRelation.getRelationName(), newRelation.getTailClassName());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error("添加失败");
        }
        return JsonResult.success("success");
    }

    /*
     * @Description: 删除核心本体中的某条关系
     * @Author: zt
     * @Date: 2023/3/9 13:21
     * @param: [coreOntologyTriple]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @DeleteMapping("/deleteRelation")
    public JsonResult deleteRelation(@RequestBody CoreOntologyTriple coreOntologyTriple){
        try {
            coreOntologyTripleService.removeRelation(coreOntologyTriple.getHeadClassId(), coreOntologyTriple.getRelationName(), coreOntologyTriple.getTailClassId());
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.error("删除失败");
        }
        return JsonResult.success("success");
    }

    /*
     * @Description: 两个本体融合
     * @Author: zt
     * @Date: 2023/3/28 18:00
     * @param: [beMergedClassId 候选本体类id, candidateOntologyId 候选本体id, coreOntologyClassId 核心本体类id]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @PutMapping("/mergeOntology")
    public JsonResult mergeOntology(@RequestParam("beMergedClassId") Integer beMergedClassId,
                                    @RequestParam("candidateOntologyId") Integer candidateOntologyId,
                                    @RequestParam("coreOntologyClassId") Integer coreOntologyClassId){
        try {
            coreOntologyClassService.merge(beMergedClassId, candidateOntologyId, coreOntologyClassId);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.error("融合失败");
        }
        return JsonResult.success("success");
    }

    @GetMapping("/getAttribute/{id}")
    public JsonResult getAttribute(@PathVariable("id") Integer id){
        return coreOntologyAttributeService.getAttributesByClassId(id);
    }

    @PostMapping("/addAttribute")
    public JsonResult addAttribute(@RequestBody CoreOntologyAttribute newAttribute){
        return coreOntologyAttributeService.addAttribute(newAttribute);
    }

    @DeleteMapping("/deleteAttribute/{attributeId}")
    public JsonResult deleteAttribute(@PathVariable("attributeId") Integer attributeId){
        return coreOntologyAttributeService.deleteAttribute(attributeId);
    }

}
