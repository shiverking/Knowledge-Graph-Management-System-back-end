package com.group.KGMS.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateOntology;
import com.group.KGMS.entity.CandidateOntologyAttribute;
import com.group.KGMS.entity.CandidateOntologyClass;
import com.group.KGMS.entity.CandidateOntologyTriple;
import com.group.KGMS.service.CandidateOntologyAttributeService;
import com.group.KGMS.service.CandidateOntologyClassService;
import com.group.KGMS.service.CandidateOntologyService;
import com.group.KGMS.service.CandidateOntologyTripleService;
import com.group.KGMS.utils.JsonResult;
import org.hibernate.collection.internal.PersistentIdentifierBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.controller
 * @Author: zt
 * @CreateTime: 2022-12-02  17:03
 * @Description:
 */

@RestController
@RequestMapping("/candidateOntology")
public class CandidateOntologyController {

    @Autowired
    private CandidateOntologyService candidateOntologyService;
    @Autowired
    private CandidateOntologyClassService candidateOntologyClassService;
    @Autowired
    private CandidateOntologyTripleService candidateOntologyTripleService;

    @Resource
    private CandidateOntologyAttributeService candidateOntologyAttributeService;


    /*
     * @Description: 添加候选本体
     * @Author: zt
     * @Date: 2023/3/28 11:42
     * @param: [newOnto]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @PostMapping("/addCandidateOntology")
    public JsonResult addCandidateOntology(@RequestBody CandidateOntology newOnto){
        return candidateOntologyService.saveOnto(newOnto);
    }

    /*
     * @Description: 分页获取候选本体
     * @Author: zt
     * @Date: 2022/12/2 17:47
     * @param: [page, limit]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @PostMapping("/getAllCandidateOntology")
    public JsonResult getCandidateOntologies(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        System.out.println("分页获取候选本体接收");
        PageInfo<CandidateOntology> allCandidateOntologyByPage = candidateOntologyService.getAllCandidateOntologyByPage(page, limit);
        //第一个是结果列表，第二个是总数
        return JsonResult.success("success",allCandidateOntologyByPage.getList(),allCandidateOntologyByPage.getTotal());
    }

    /*
     * @Description: 获取某个候选本体的树形结构
     * @Author: zt
     * @Date: 2023/3/2 17:20
     * @param: [candidateOntologyId]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @PostMapping("/getTreeClassByCandidateOntologyId")
    public JsonResult getClassByCandidateOntologyId(@RequestParam("candidateOntologyId") Integer candidateOntologyId){
        System.out.println(candidateOntologyId);
        CandidateOntologyClass rootClass = candidateOntologyClassService.getRootClassByCandidateOntologyId(candidateOntologyId);
//        String treeJson = JSONObject.toJSONString(rootClass);
//        System.out.println(treeJson);
        return JsonResult.success("success",rootClass);
    }

    /*
     * @Description: 向某个候选本体中添加新的类
     * @Author: zt
     * @Date: 2023/3/2 17:34
     * @param: [newClass]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @PostMapping("/addClass")
    public JsonResult addClass(@RequestBody CandidateOntologyClass newClass){
        System.out.println("newClass = " + newClass);
        String newClassName = newClass.getName();
        Integer parentId = newClass.getParentId();
        Integer belongCandidateId = newClass.getBelongCandidateId();
        try {
            candidateOntologyClassService.save(newClassName, parentId, belongCandidateId);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error("增加失败");
        }
        return JsonResult.success("success");
    }

    /*
     * @Description: 删除某个候选本体中的类
     * @Author: zt
     * @Date: 2023/3/3 17:27
     * @param: [delClass]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @DeleteMapping("/deleteClass")
    public JsonResult deleteClass(@RequestBody CandidateOntologyClass delClass){
        System.out.println("delClass = " + delClass);
        if("Thing".equals(delClass.getName())){
            return JsonResult.error("不能删除根节点");
        }
        try {
            candidateOntologyClassService.remove(delClass.getName(), delClass.getBelongCandidateId());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error("这个类有子类，无法删除");
        }
        return JsonResult.success("success");
    }

    /*
     * @Description: 获取某个候选本体的类列表，作为图节点
     * @Author: zt
     * @Date: 2023/3/7 0:24
     * @param: [candidateOntologyId 候选本体id]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @GetMapping("/getGraphNode/{candidateOntologyId}")
    public JsonResult getGraphNode(@PathVariable("candidateOntologyId") Integer candidateOntologyId){
        List<CandidateOntologyClass> classList = candidateOntologyClassService.getAllClassByCandidateOntologyId(candidateOntologyId);
        return JsonResult.success("success", classList);
    }

    /*
     * @Description: 获取某个候选本体的关系列表，作为图的边
     * @Author: zt
     * @Date: 2023/3/7 0:56
     * @param: [candidateOntologyId 候选本体id]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @GetMapping("/getGraphEdge/{candidateOntologyId}")
    public JsonResult getGraphEdge(@PathVariable("candidateOntologyId") Integer candidateOntologyId){
        List<CandidateOntologyTriple> relationList = candidateOntologyTripleService.getAllRelationByCandidateOntologyId(candidateOntologyId);
        return JsonResult.success("success", relationList);
    }

    /*
     * @Description: 向某个候选本体的两个类中间添加关系
     * @Author: zt
     * @Date: 2023/3/7 13:57
     * @param: [newRelation]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @PostMapping("/addRelation")
    public JsonResult addRelation(@RequestBody CandidateOntologyTriple newRelation){
        try {
            candidateOntologyTripleService.saveRelation(newRelation.getHeadClassName(), newRelation.getRelationName(), newRelation.getTailClassName(), newRelation.getBelongCandidateOntologyId());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error("添加关系失败，请联系管理员");
        }
        return JsonResult.success("success");
    }

    /*
     * @Description: 删除某个候选本体中的某条关系
     * @Author: zt
     * @Date: 2023/3/8 12:05
     * @param: [candidateOntologyTriple]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @DeleteMapping("/deleteRelation")
    public JsonResult deleteRelation(@RequestBody CandidateOntologyTriple candidateOntologyTriple){
        try {
            candidateOntologyTripleService.removeRelation(candidateOntologyTriple.getHeadClassId(), candidateOntologyTriple.getTailClassId(),
                    candidateOntologyTriple.getRelationName(), candidateOntologyTriple.getBelongCandidateOntologyId());
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.error("删除关系失败，请联系管理员");
        }
        return JsonResult.success("success");
    }

    /*
     * @Description: 获取某个某个候选本体中某个类的属性列表
     * @Author: zt
     * @Date: 2023/3/23 16:10
     * @param: [classId, belongCandidateOntologyId]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @GetMapping("/getAttribute/{classId}/{belongCandidateOntologyId}")
    public JsonResult getAttribute(@PathVariable("classId") Integer classId, @PathVariable("belongCandidateOntologyId") Integer belongCandidateOntologyId){
        return candidateOntologyAttributeService.getAttribute(classId, belongCandidateOntologyId);
    }

    @PostMapping("/addAttribute")
    public JsonResult addAttribute(@RequestBody CandidateOntologyAttribute newAttribute){
        return candidateOntologyAttributeService.addAttribute(newAttribute);
    }

    @DeleteMapping("/deleteAttribute/{attributeId}")
    public JsonResult deleteAttribute(@PathVariable("attributeId") Integer attributeId){
        return candidateOntologyAttributeService.deleteAttribute(attributeId);
    }

    /*
     * @Description: 结构化数据生成候选本体
     * @Author: zt
     * @Date: 2023/3/28 13:15
     * @param: []
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @GetMapping("/structuredDataToOntology/{candidateOntoName}")
    public JsonResult structuredDataToOntology(@PathVariable("candidateOntoName") String candidateOntoName){
        return candidateOntologyService.structuredDataToOntology(candidateOntoName);
    }

    /*
     * @Description: 删除某个候选本体
     * @Author: zt
     * @Date: 2023/3/28 18:05
     * @param: [candidateOntoId]
     * @return: com.group.KGMS.utils.JsonResult
     **/
    @DeleteMapping("/deleteCandidateOnto/{candidateOntoId}")
    public JsonResult deleteCandidateOnto(@PathVariable("candidateOntoId") Integer candidateOntoId){
        return candidateOntologyService.deleteCandidateOnto(candidateOntoId);
    }

}
