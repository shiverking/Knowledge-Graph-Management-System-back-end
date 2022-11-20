package com.group.KGMS.entity;

import lombok.*;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;

import java.util.Date;

/**
 * @author ：闫崇傲
 * @description：候选图谱
 * @date 2022/11/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CandidateKG {
    private Long id;
    private String name;
    private String creator;
    //创建时间
    private Date createTime;
    //最后修改时间
    private Date changeTime;
    private String status;
    private String comment;
}
