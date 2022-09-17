package com.group.KGMS.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class T_bomb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String picture;
    private String description;
    private String rd_company;
    private String charge_type;
    private String bomb_length;
    private String weight;
    private String type;
}
