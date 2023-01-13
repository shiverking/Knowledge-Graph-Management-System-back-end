package com.group.KGMS.entity;

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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateOntology {
    private Integer id;
    private String name;
    private String creatorName;
    private Date createTime;
    private Date changeTime;
    private String comment;
}
