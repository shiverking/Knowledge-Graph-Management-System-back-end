package com.group.KGMS.entity;

import lombok.*;

/**
 * @author ：闫崇傲
 * @description：三元组中关系在库中位置
 * @date 2022/9/19
 */
@Data
@AllArgsConstructor
@Getter
@Setter
public class TripleIds {
    private Long id;
    private Long tripleId;
    private Long headId;
    private Long relationId;
    private Long tailId;
    private Long headCategoryId;
    private Long tailCategoryId;
}
