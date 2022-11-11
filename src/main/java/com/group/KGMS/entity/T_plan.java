package com.group.KGMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * @作者 冯鑫
 * @用途 计划类
 * @修改时间 2022/11/11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class T_plan {

    private Integer id;
    private String plan_name;
    private String start_time;
    private String end_time;
    private String plan_status;
}
