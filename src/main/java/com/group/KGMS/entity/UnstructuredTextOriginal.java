package com.group.KGMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ：冯鑫
 * @description：非结构化文本mongodb类（原始数据）
 * @date ：2023/3/14
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "unstructured_text_original") //指定要对应的文档名（表名）
public class UnstructuredTextOriginal {
    @Id
    private String _id;
    //作者
    private String author;
    //标题
    private String title;
    //内容
    private String content;
    //出版时间
    private String publish_time;
    //创建时间
    private String create_time;
    //来源
    private String origin;
    //抽取状态
    private String status;
    //爬虫cid
    @Indexed
    private Integer cid;
}
