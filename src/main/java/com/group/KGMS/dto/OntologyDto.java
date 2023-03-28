package com.group.KGMS.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.dto
 * @Author: zt
 * @CreateTime: 2023-03-21  16:04
 * @Description:
 */

@Data
@NoArgsConstructor
public class OntologyDto {

    private Integer version;

    private List<ClassDto> classDtos;

    private List<AttributeDto> attributeDtos;

    private List<RelationDto> relationDtos;

}
