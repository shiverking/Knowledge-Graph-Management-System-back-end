package com.group.KGMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：闫崇傲
 * @description：角色类
 * @date ：2022/9/9 16:34
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {
    //角色ID
    private Integer id;
    //角色编号
    private String name;
    //角色名称
    private String nameZh;
}
