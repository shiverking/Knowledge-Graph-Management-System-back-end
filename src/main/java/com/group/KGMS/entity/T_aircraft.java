package com.group.KGMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @作者 冯鑫
 * @用途 飞机类
 * @修改时间 2022/11/11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class T_aircraft {
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
