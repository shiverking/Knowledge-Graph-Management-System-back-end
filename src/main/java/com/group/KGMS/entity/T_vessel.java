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
 * @用途 舰船类
 * @修改时间 2022/11/11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class T_vessel {

    private Integer id;
    private String name;
    private String picture;
    private String description;
    private String manufacturer;
    private String product_date;
    private String launch_time;
    private String service_time;
    private String status;
    private String subtype;
    private String length;
    private String width;
    private String crew_num;
    private String full_load_displacement;
    private String speed;
    private String type;
    private Integer task_id;
}
