package com.group.KGMS.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.entity
 * @Author: zt
 * @CreateTime: 2023-03-08  15:36
 * @Description:
 */

@Data
@TableName("core_onto_class")
@NoArgsConstructor
public class CoreOntologyClass {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("class_name")
    private String className;

    @TableField("parent_id")
    private Integer parentId;

    //用一个list来存放每一个类对应的子类,这一列在数据表中不存在，用@TableField(exist = false)进行标识
    @TableField(exist = false)
    private List<CoreOntologyClass> children = new ArrayList<>();

    public CoreOntologyClass(String className, Integer parentId) {
        this.className = className;
        this.parentId = parentId;
    }
}
