package com.group.KGMS.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class T_person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String person_id;
    private String name_cn;
    private String name_en;
    private String gender;
    private String picture;
    private String address;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private T_task task;
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private T_plan plan;

}
