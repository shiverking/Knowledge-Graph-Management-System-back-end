package com.group.KGMS.entity;

import lombok.*;

import javax.validation.constraints.NotEmpty;

/**
 * @author ：闫崇傲
 * @description：三元组类定义
 * @date 2022/9/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Triple {
    //头实体
    private String head;
    //关系
    private String relation;
    //尾实体
    private String tail;
}
