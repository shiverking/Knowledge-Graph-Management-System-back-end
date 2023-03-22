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
 * @CreateTime: 2023-03-21  16:05
 * @Description:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("dto_relation")
public class RelationDto {

    @TableId
    private String id;

    @TableField("name")
    private String name;

    @TableField("start_id")
    private String startId;

    @TableField("end_id")
    private String endId;

    @TableField("create_at")
    private Date createAt;

    @TableField("detail")
    private String detail;

}
