package com.group.KGMS.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.entity
 * @Author: zt
 * @CreateTime: 2023-03-09  00:44
 * @Description:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("core_onto_object_property")
public class CoreOntologyTriple {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("head_class_id")
    private Integer headClassId;

    @TableField("head_class_name")
    private String headClassName;

    @TableField("relation_name")
    private String relationName;

    @TableField("tail_class_id")
    private Integer tailClassId;

    @TableField("tail_class_name")
    private String tailClassName;

    public CoreOntologyTriple(Integer headClassId, String headClassName, String relationName, Integer tailClassId, String tailClassName) {
        this.headClassId = headClassId;
        this.headClassName = headClassName;
        this.relationName = relationName;
        this.tailClassId = tailClassId;
        this.tailClassName = tailClassName;
    }
}
