package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_artillery;

import java.util.List;

public interface ArtilleryService {
    T_artillery findById(int id);

    PageInfo<T_artillery> findAllArtillery(Integer page, Integer size);

    PageInfo<T_artillery> search(RuleForm ruleForm);

    int save(T_artillery artillery);

    int update(T_artillery artillery);

    int delete(int id);
}
