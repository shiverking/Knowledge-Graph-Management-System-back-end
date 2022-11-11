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
 * @用途 导弹类
 * @修改时间 2022/11/11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class T_misile {

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
