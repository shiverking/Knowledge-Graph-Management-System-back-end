package com.group.KGMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @作者 杨佳豪
 * @用途 爬虫类
 * @修改时间 2023/03/04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class T_crawler {
    private Integer cid;
    private String name;
    private String spider_name;
    private String path;
    private String remark;
    private Integer pid;
    private Integer status;
    private String website;
}
