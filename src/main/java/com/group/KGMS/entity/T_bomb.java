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
 * @用途 爆炸物类
 * @修改时间 2022/11/11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class T_bomb {

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
