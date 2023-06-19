package com.group.KGMS.service;

import cn.hutool.core.bean.BeanUtil;
import com.group.KGMS.dto.AttributeDto;
import com.group.KGMS.dto.ClassDto;
import com.group.KGMS.dto.OntologyDto;
import com.group.KGMS.dto.RelationDto;
import com.group.KGMS.mapper.AttributeDtoMapper;
import com.group.KGMS.mapper.ClassDtoMapper;
import com.group.KGMS.mapper.RelationDtoMapper;
import com.group.KGMS.utils.JsonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-03-21  17:26
 * @Description:
 */

@Service
public class BigDataServiceImpl implements BigDataService{

    @Resource
    private ClassDtoMapper classDtoMapper;

    @Resource
    private RelationDtoMapper relationDtoMapper;

    @Resource
    private AttributeDtoMapper attributeDtoMapper;

    @Override
    public JsonResult saveOnto(OntologyDto ontologyDto) {
        //先清空原有的数据
        classDtoMapper.delete(null);
        relationDtoMapper.delete(null);
        attributeDtoMapper.delete(null);
        //保存新的数据
        List<ClassDto> classDtos = ontologyDto.getClassDtos();
        for(ClassDto classDto: classDtos){
            classDtoMapper.insert(classDto);
        }
        List<RelationDto> relationDtos = ontologyDto.getRelationDtos();
        for (RelationDto relationDto : relationDtos){
            relationDtoMapper.insert(relationDto);
        }
        List<AttributeDto> attributeDtos = ontologyDto.getAttributeDtos();
        for (AttributeDto attributeDto : attributeDtos){
            attributeDtoMapper.insert(attributeDto);
        }
        return JsonResult.success();
    }

    @Override
    public JsonResult getClassList() {
        List<ClassDto> classDtoList = classDtoMapper.selectList(null);
        return JsonResult.success(classDtoList);
    }

    @Override
    public JsonResult getRelationList() {
        List<RelationDto> relationDtoList = relationDtoMapper.selectList(null);
        return JsonResult.success(relationDtoList);
    }

    @Override
    public JsonResult getAttributeList() {
        List<AttributeDto> attributeDtoList = attributeDtoMapper.selectList(null);
        return JsonResult.success(attributeDtoList);
    }
}
