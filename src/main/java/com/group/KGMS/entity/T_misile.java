package com.group.KGMS.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class T_misile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String picture;
    private String description;
    private String rd_company;
    private String bomb_length;
    private String bomb_weight;
    private String winspan;
    private String guidance_system;
    private String max_speed;
    private String scope;
    private String type;
}
