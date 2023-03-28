package com.group.KGMS.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.KGMS.dto.ClassDto;
import com.group.KGMS.mapper.ClassDtoMapper;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-03-28  13:19
 * @Description:
 */
@Service
public class ClassDtoServiceImpl extends ServiceImpl<ClassDtoMapper, ClassDto> implements ClassDtoService{
}
