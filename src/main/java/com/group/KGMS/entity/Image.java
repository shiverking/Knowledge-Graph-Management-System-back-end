package com.group.KGMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @作者 冯鑫
 * @用途 图片类
 * @修改时间 2022/3/14
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection ="image")
public class Image {
    @Id
    private String _id;
    private String imageName;
    private String type;
    private String origin;
    private Integer cid;
    private String create_time;
    private String path;
}
