package com.group.KGMS.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 与黄老师系统的请求信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestInfo {
    private String id;
    @JsonFormat(pattern="yyyy-MM-dd HH:MM:SS")
    private Date time;
    private Integer num;
    private String result;
}
