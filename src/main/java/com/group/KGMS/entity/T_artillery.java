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
 * @用途 火炮类
 * @修改时间 2022/11/11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class T_artillery {

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
