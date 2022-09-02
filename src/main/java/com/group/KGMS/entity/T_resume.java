package com.group.KGMS.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class T_resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String start_time;
    private String end_time;
    private String department;
    private String title;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private T_person person;
}
