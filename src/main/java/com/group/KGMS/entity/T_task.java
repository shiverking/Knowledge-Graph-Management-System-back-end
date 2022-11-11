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
 * @用途 任务类
 * @修改时间 2022/11/11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class T_task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String task_name;
    private String start_time;
    private String end_time;
    private String task_status;
    private Integer plan_id;
}
