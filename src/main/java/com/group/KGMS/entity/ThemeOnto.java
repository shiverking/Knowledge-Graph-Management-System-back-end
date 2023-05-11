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
 * @CreateTime: 2023-05-10  21:31
 * @Description:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("theme_onto")
public class ThemeOnto {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("comment")
    private String comment;


}
