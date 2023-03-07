package com.group.KGMS.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @作者 杨佳豪
 * @用途 爬虫运行记录类
 * @修改时间 2023/03/04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class T_record {
    private Integer rid;
    private Integer crawler_id;
    private Date start_time;
    private Date end_time;
    private Integer status;
}