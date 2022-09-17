package com.group.KGMS.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class T_plan {
    @Id
    private Integer id;
    private String plan_name;
    private String start_time;
    private String end_time;
    private String plan_status;
}
