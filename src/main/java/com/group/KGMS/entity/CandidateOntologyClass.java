package com.group.KGMS.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.group.KGMS.utils.treejson.TreeItem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.entity
 * @Author: zt
 * @CreateTime: 2022-12-22  18:52
 * @Description: 候选本体中每个类的pojo，是为了对类之间的父子关系进行维护，对应candidate_onto_class这个表
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("candidate_onto_class")
public class CandidateOntologyClass {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("class_name")
    private String name;

    //父id
    @TableField("parent_id")
    private Integer parentId;

    @TableField("belong_candidate_id")
    private Integer belongCandidateId;

    //用一个list来存放每一个类对应的子类,这一列在数据表中不存在，用@TableField(exist = false)进行标识
    @TableField(exist = false)
    private List<CandidateOntologyClass> children = new ArrayList<>();

    public CandidateOntologyClass(String name, Integer parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public CandidateOntologyClass(Integer id, String name, Integer parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public CandidateOntologyClass(String name, Integer parentId, Integer belongCandidateId) {
        this.name = name;
        this.parentId = parentId;
        this.belongCandidateId = belongCandidateId;
    }

    public CandidateOntologyClass(Integer id, String name, Integer parentId, Integer belongCandidateId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.belongCandidateId = belongCandidateId;
    }
}
