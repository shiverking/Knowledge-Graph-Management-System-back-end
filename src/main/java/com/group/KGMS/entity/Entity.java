package com.group.KGMS.entity;

import lombok.*;

/**
 * @author ：闫崇傲
 * @description：实体定义
 * @date 2022/11/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Entity {
    private Long id;
    private String name;
    private String category;
}
