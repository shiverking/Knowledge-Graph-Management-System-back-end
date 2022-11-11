package com.group.KGMS.entity;

import lombok.Data;
/**
 * @作者 冯鑫
 * @用途 后台返回分页搜索数据类
 * @修改时间 2022/11/11
 */
@Data
public class RuleForm {
    private String key;
    private String value;
    private Integer page;
    private Integer size;
}
