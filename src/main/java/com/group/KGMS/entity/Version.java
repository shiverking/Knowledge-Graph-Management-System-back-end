package com.group.KGMS.entity;

import lombok.*;

import java.util.Date;

/**
 * @author ：闫崇傲
 * @description：版本定义
 * @date 2023/2/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Version {
    private String version_number;
    private String name;
    private Date submit_time;
    private String submitted_by;
    private int synchronization;
}
