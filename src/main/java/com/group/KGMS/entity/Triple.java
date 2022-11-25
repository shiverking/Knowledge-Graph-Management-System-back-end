package com.group.KGMS.entity;
import lombok.*;
import java.util.Date;

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
    private Long id;
    private String head;
    private String relation;
    private String tail;
    private Date time;
    private Long candidateId;
    private String status;
}
