package com.group.KGMS.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class T_vessel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String vessel_id;
    private String name;
    private String picture;
    private String description;
    private String manufacturer;
    private String product_date;
    private String launch_time;
    private String service_time;
    private String status;
    private String subtype;
    private String length;
    private String width;
    private String crew_num;
    private String full_load_displacement;
    private String speed;
    private String type;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private T_task task;
}
