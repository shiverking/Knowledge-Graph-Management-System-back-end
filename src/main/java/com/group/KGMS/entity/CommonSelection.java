package com.group.KGMS.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 对接数据治理系统端口号和接口
 */
@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "common")
public class CommonSelection {
    private String ip;
    private Integer port;
    private String kgInterface;
    private String ontologyInterface;
}
