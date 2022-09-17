package com.group.KGMS.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class T_person {
    @Id
    private Integer id;
    private String name_cn;
    private String name_en;
    private String gender;
    private String picture;
    private String address;

    private Integer task_id;

    private Integer plan_id;

}
