package com.group.KGMS.service;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_misile;

import java.util.List;

public interface MisileService {
    T_misile findById(int id);

    PageInfo<T_misile> findAllMisile(Integer page, Integer size);

    PageInfo<T_misile> search(RuleForm ruleForm);

    int save(T_misile misile);

    int update(T_misile misile);

    int delete(int id);
}
