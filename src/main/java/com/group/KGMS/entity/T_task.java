package com.group.KGMS.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class T_task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String task_id;
    private String task_name;
    private String start_time;
    private String end_time;
    private String task_status;
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private T_plan plan;
}
