package com.group.KGMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "semistructured_data_original") //指定要对应的文档名（表名）
public class SemistructuredDataOriginal {
    @Id
    private String _id;
    private String name;
    private String status;
    private String type;
    private String max_speed;
    private String weight;
    private String create_time;
    private Integer cid;
    private String length;
    private String width;
    private String height;
    private String crew;
    private String range;
    private String draught;
    private String displacement;
    private String service_year;
    private String origin;
    private String manufacturer;
    private String ceiling;
    private String horsepower;
}
