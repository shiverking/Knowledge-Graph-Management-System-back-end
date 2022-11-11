package com.group.KGMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * @作者 冯鑫
 * @用途 人员类
 * @修改时间 2022/11/11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class T_person {

    private Integer id;
    private String name_cn;
    private String name_en;
    private String gender;
    private String picture;
    private String address;
    private Integer task_id;
    private Integer plan_id;

}
