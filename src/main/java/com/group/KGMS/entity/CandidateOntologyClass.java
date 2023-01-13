package com.group.KGMS.entity;

import com.group.KGMS.utils.treejson.TreeItem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.entity
 * @Author: zt
 * @CreateTime: 2022-12-22  18:52
 * @Description: 候选本体中每个类的pojo，是为了对类之间的父子关系进行维护，对应candidate_onto_class这个表
 */

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateOntologyClass {
    private Integer id;
    private String name;
    //父id
    private Integer parentId;
    //用一个list来存放每一个类对应的子类
    private List<CandidateOntologyClass> children = new ArrayList<>();

    public CandidateOntologyClass(Integer id, String name, Integer parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }
}
