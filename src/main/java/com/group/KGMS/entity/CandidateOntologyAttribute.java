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
 * @CreateTime: 2023-03-23  15:36
 * @Description:
 */

@Data
@NoArgsConstructor
@TableName("candidate_onto_data_property")
public class CandidateOntologyAttribute {

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

    @TableField("belong_candidate_id")
    private Integer belongCandidateId;

}
