package com.group.KGMS.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.entity
 * @Author: zt
 * @CreateTime: 2022-12-08  19:14
 * @Description: 候选本体中对象属性形成的三元组的pojo，对应candidate_onto_object_property这个表
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("candidate_onto_object_property")
public class CandidateOntologyTriple {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("head_class_id")
    private Integer headClassId;

    @TableField("head_class_name")
    private String headClassName;

    @TableField("relation_id")
    private Integer relationId;

    @TableField("relation_name")
    private String relationName;

    @TableField("tail_class_id")
    private Integer tailClassId;

    @TableField("tail_class_name")
    private String tailClassName;

    @TableField("belong_candidate_id")
    private Integer belongCandidateOntologyId;

    public CandidateOntologyTriple(Integer headClassId, String headClassName, String relationName, Integer tailClassId, String tailClassName, Integer belongCandidateOntologyId) {
        this.headClassId = headClassId;
        this.headClassName = headClassName;
        this.relationName = relationName;
        this.tailClassId = tailClassId;
        this.tailClassName = tailClassName;
        this.belongCandidateOntologyId = belongCandidateOntologyId;
    }

    public CandidateOntologyTriple(String headClassName, String relationName, String tailClassName, Integer belongCandidateOntologyId) {
        this.headClassName = headClassName;
        this.relationName = relationName;
        this.tailClassName = tailClassName;
        this.belongCandidateOntologyId = belongCandidateOntologyId;
    }
}
