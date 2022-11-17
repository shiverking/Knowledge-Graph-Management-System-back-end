package com.group.KGMS.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author 闫崇傲
 * 候选三元组类
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CandidateTriple {
    private Long id;
    private String head;
    private String relation;
    private String tail;
    private Date time;
    private String headCategory;
    private String tailCategory;
    private String status;
}
