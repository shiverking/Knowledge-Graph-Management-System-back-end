package com.group.KGMS.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.entity
 * @Author: zt
 * @CreateTime: 2023-03-23  12:15
 * @Description:
 */

@Data
@NoArgsConstructor
@TableName("core_onto_data_property")
public class CoreOntologyAttribute {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("class_id")
    private Integer classId;

    @TableField("class_name")
    private String className;

    @TableField("attribute_name")
    private String attributeName;

    @TableField("comment")
    private String comment;

}
