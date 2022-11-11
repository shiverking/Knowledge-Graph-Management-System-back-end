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
 * @用途 人员教育信息类
 * @修改时间 2022/11/11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class T_education {

    private Integer id;
    private String start_time;
    private String finish_time;
    private String major;
    private String degree;
    private Integer person_id;
}
