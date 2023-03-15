package com.group.KGMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateKGInfo {
    private Long id;
    //三元组数量
    private Long tripleCount;
    //实体数量
    private Long entityCount;
    //关系数量
    private Long relationCount;
    //关系数量
    private Long relationTypeCount;
    //候选图谱的id
    private Long candidateId;
}
