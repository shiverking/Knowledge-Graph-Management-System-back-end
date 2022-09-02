package com.group.KGMS.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class T_taskperson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String task_id;
    private String person_id;
}
