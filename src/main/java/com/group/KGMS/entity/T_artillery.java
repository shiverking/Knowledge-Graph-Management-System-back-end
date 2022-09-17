package com.group.KGMS.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class T_artillery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String country;
    private String picture;
    private String description;
    private String rd_company;
    private String product_date;
    private String length;
    private String weight;
    private String diameter;
    private String barrel_length;
    private String muzzle_velocity;
    private String scope;
    private String type;
}
