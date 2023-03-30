package com.group.KGMS.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.KGMS.dto.RelationDto;
import com.group.KGMS.mapper.RelationDtoMapper;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-03-28  13:20
 * @Description:
 */
@Service
public class RelationDtoServiceImpl extends ServiceImpl<RelationDtoMapper, RelationDto> implements RelationDtoService {
}
