package com.group.KGMS.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class T_education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String start_time;
    private String finish_time;
    private String major;
    private String degree;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private T_person person;
}
