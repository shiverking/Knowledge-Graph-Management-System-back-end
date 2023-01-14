package com.group.KGMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @作者 冯鑫
 * @用途 图片类
 * @修改时间 2022/1/14
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Image {
    private Integer id;
    private String imageName;
    private String imageUrl;
    private String imageDate;
    private Integer album_id;
}
