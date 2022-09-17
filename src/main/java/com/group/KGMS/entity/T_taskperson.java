package com.group.KGMS.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class T_taskperson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String task_id;
    private String person_id;
}
