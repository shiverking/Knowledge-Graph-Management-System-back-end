package com.group.KGMS.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.entity
 * @Author: zt
 * @CreateTime: 2022-12-02  16:26
 * @Description: 候选本体的pojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("candidate_onto")
public class CandidateOntology {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("creator_name")
    private String creatorName;

    @TableField("create_time")
    private Date createTime;

    @TableField("change_time")
    private Date changeTime;

    @TableField("comment")
    private String comment;
}
