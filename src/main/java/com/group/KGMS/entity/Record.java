package com.group.KGMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

/**
 * @作者 冯鑫
 * @用途 爬虫运行记录
 * @修改时间 2023/3/10
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "record") //指定要对应的文档名（表名）
public class Record {
    @Id
    private String _id;
    //爬虫cid
    private Integer cid;
    //运行开始时间
    private String start_time;
    //结束
    private String end_time;
    //运行状态
    private Integer status;
}
