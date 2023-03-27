package com.group.KGMS.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.dto
 * @Author: zt
 * @CreateTime: 2023-03-21  16:04
 * @Description:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("dto_class")
public class ClassDto {

    @TableId
    private String id;

    @TableField("name")
    private String name;

    @TableField("parent_id")
    private String parentId;

    @TableField("create_at")
    private Date createdAt;

    @TableField("detail")
    private String detail;

}
