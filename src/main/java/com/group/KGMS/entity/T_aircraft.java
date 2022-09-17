package com.group.KGMS.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class T_aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String aircraft_name;
    private String picture;
    private String description;
    private String fist_flight;
    private String rd_company;
    private String engines_num;
    private String crew_num;
    private String length;
    private String winspan;
    private String height;
    private String engine;
    private String max_speed;
    private String empty_wgt;
    private String type;

}
