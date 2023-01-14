package com.group.KGMS.entity;

import lombok.*;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.entity
 * @Author: zt
 * @CreateTime: 2022-12-08  19:14
 * @Description: 候选本体中对象属性形成的三元组的pojo，对应candidate_onto_object_property这个表
 */

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateOntologyTriple {
    private Integer id;
    private Integer headClassId;
    private String headClass;
    private Integer relationId;
    private String relation;
    private Integer tailClassId;
    private String tailClass;
    private Integer belongCandidateOntologyId;
}
