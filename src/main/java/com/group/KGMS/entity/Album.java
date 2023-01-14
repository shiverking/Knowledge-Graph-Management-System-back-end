package com.group.KGMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @作者 冯鑫
 * @用途 相册类
 * @修改时间 2022/1/14
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Album {
    private Integer albumId;
    private String albumName;
    private String albumImg;
    private String imageNumber;
}
